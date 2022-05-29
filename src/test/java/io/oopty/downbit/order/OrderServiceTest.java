package io.oopty.downbit.order;

import io.oopty.downbit.order.constant.OrderSide;
import io.oopty.downbit.order.constant.OrderStatus;
import io.oopty.downbit.order.constant.OrderType;
import io.oopty.downbit.order.exception.InsufficientMaker;
import io.oopty.downbit.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    public static final int BTC_CURRENCY_ID = 123;
    public static final int ETH_CURRENCY_ID = 234;
    public static final int USER_ID = 789;

    @InjectMocks
    private OrderService subject;

    @Mock
    private OrderRepository mockOrderRepository;
    
    @Captor
    private ArgumentCaptor<Order> orderDaoCaptor;

    @DisplayName("when submitted order is market order")
    @Nested
    class MarketOrderTest {
        @DisplayName("if trade side is bid")
        @Nested
        class TradeSideIsBidTest {

            private Order order1;
            private Order order2;
            private Order order3;

            @BeforeEach
            void setUp() {
                order1 = Order.builder()
                        .id(123L)
                        .side(OrderSide.ASK.getSide())
                        .type(OrderType.LIMIT.getType())
                        .price(123)
                        .state(OrderStatus.OPENED.getValue())
                        .createdAt(LocalDateTime.of(2022, 3, 20, 14, 19, 0))
                        .volume(200)
                        .executedVolume(0)
                        .remainingVolume(200)
                        .build();

                order2 = Order.builder()
                        .id(234L)
                        .side(OrderSide.ASK.getSide())
                        .type(OrderType.LIMIT.getType())
                        .price(234)
                        .state(OrderStatus.PROCESSING.getValue())
                        .createdAt(LocalDateTime.of(2022, 3, 20, 14, 19, 0))
                        .volume(200)
                        .executedVolume(0)
                        .remainingVolume(200)
                        .build();

                order3 = Order.builder()
                        .id(345L)
                        .side(OrderSide.ASK.getSide())
                        .type(OrderType.LIMIT.getType())
                        .price(456)
                        .state(OrderStatus.OPENED.getValue())
                        .createdAt(LocalDateTime.of(2022, 3, 20, 14, 19, 0))
                        .volume(200)
                        .executedVolume(0)
                        .remainingVolume(200)
                        .build();
            }

            @DisplayName("should trade with limit order")
            @Test
            void shouldTradeWithLimitOrder1() {
                when(mockOrderRepository.findByTypeAndSideAndStateInOrderByPriceAscCreatedAtDesc(
                        OrderType.LIMIT.getType(),
                        OrderSide.ASK.getSide(),
                        List.of(OrderStatus.OPENED.getValue(), OrderStatus.PROCESSING.getValue())))
                        .thenReturn(List.of(order1, order2, order3));

                subject.order(234, OrderSide.BID.getSide(), OrderType.MARKET.getType(), 0, 100, 456);

                verify(mockOrderRepository, times(2)).save(orderDaoCaptor.capture());
                List<Order> allValues = orderDaoCaptor.getAllValues();

                Order orderResult1 = allValues.get(0);
                Order orderResult2 = allValues.get(1);

                assertThat(orderResult1.getId(), is(123L));
                assertThat(orderResult1.getExecutedVolume(), closeTo(100, 0.00001));
                assertThat(orderResult1.getRemainingVolume(), closeTo(100, 0.00001));

                assertThat(orderResult2.getId(), is(nullValue()));
                assertThat(orderResult2.getExecutedVolume(), closeTo(100, 0.00001));
                assertThat(orderResult2.getRemainingVolume(), closeTo(0, 0.00001));
            }

            @DisplayName("should trade with limit order")
            @Test
            void shouldTradeWithLimitOrder2() {
                when(mockOrderRepository.findByTypeAndSideAndStateInOrderByPriceAscCreatedAtDesc(
                        OrderType.LIMIT.getType(),
                        OrderSide.ASK.getSide(),
                        List.of(OrderStatus.OPENED.getValue(), OrderStatus.PROCESSING.getValue())))
                        .thenReturn(List.of(order1, order2, order3));

                subject.order(234, OrderSide.BID.getSide(), OrderType.MARKET.getType(), 0, 300, 456);

                verify(mockOrderRepository, times(3)).save(orderDaoCaptor.capture());
                List<Order> allValues = orderDaoCaptor.getAllValues();

                Order orderResult1 = allValues.get(0);
                Order orderResult2 = allValues.get(1);
                Order orderResult3 = allValues.get(2);

                assertThat(orderResult1.getId(), is(123L));
                assertThat(orderResult1.getExecutedVolume(), closeTo(200, 0.00001));
                assertThat(orderResult1.getRemainingVolume(), closeTo(0, 0.00001));

                assertThat(orderResult2.getId(), is(234L));
                assertThat(orderResult2.getExecutedVolume(), closeTo(100, 0.00001));
                assertThat(orderResult2.getRemainingVolume(), closeTo(100, 0.00001));

                assertThat(orderResult3.getId(), is(nullValue()));
                assertThat(orderResult3.getExecutedVolume(), closeTo(300, 0.00001));
                assertThat(orderResult3.getRemainingVolume(), closeTo(0, 0.00001));
            }

            @DisplayName("when there are not enough maker, should make error")
            @Test
            void whenThereAreNotEnoughMakerShouldMakeError() {
                when(mockOrderRepository.findByTypeAndSideAndStateInOrderByPriceAscCreatedAtDesc(
                        OrderType.LIMIT.getType(),
                        OrderSide.ASK.getSide(),
                        List.of(OrderStatus.OPENED.getValue(), OrderStatus.PROCESSING.getValue())))
                        .thenReturn(List.of(order1, order2, order3));


                assertThrows(InsufficientMaker.class, () -> subject.order(234, OrderSide.BID.getSide(), OrderType.MARKET.getType(), 0, 601, 456));
            }
        }

        @DisplayName("if trade side is ask")
        @Nested
        class TradeSideIsAskTest {


            private Order order1;
            private Order order2;
            private Order order3;

            @BeforeEach
            void setUp() {
                order1 = Order.builder()
                        .id(123L)
                        .side(OrderSide.BID.getSide())
                        .type(OrderType.LIMIT.getType())
                        .price(456)
                        .state(OrderStatus.OPENED.getValue())
                        .createdAt(LocalDateTime.of(2022, 3, 20, 14, 19, 0))
                        .volume(200)
                        .executedVolume(0)
                        .remainingVolume(200)
                        .build();

                order2 = Order.builder()
                        .id(234L)
                        .side(OrderSide.BID.getSide())
                        .type(OrderType.LIMIT.getType())
                        .price(345)
                        .state(OrderStatus.PROCESSING.getValue())
                        .createdAt(LocalDateTime.of(2022, 3, 20, 14, 19, 0))
                        .volume(200)
                        .executedVolume(0)
                        .remainingVolume(200)
                        .build();

                order3 = Order.builder()
                        .id(345L)
                        .side(OrderSide.BID.getSide())
                        .type(OrderType.LIMIT.getType())
                        .price(234)
                        .state(OrderStatus.OPENED.getValue())
                        .createdAt(LocalDateTime.of(2022, 3, 20, 14, 19, 0))
                        .volume(200)
                        .executedVolume(0)
                        .remainingVolume(200)
                        .build();
            }

            @DisplayName("should trade with limit order")
            @Test
            void shouldTradeWithLimitOrder1() {
                when(mockOrderRepository.findByTypeAndSideAndStateInOrderByPriceAscCreatedAtDesc(
                        OrderType.LIMIT.getType(),
                        OrderSide.BID.getSide(),
                        List.of(OrderStatus.OPENED.getValue(), OrderStatus.PROCESSING.getValue())))
                        .thenReturn(List.of(order1, order2, order3));

                subject.order(234, OrderSide.ASK.getSide(), OrderType.MARKET.getType(), 0, 100, 456);

                verify(mockOrderRepository, times(2)).save(orderDaoCaptor.capture());
                List<Order> allValues = orderDaoCaptor.getAllValues();

                Order orderResult1 = allValues.get(0);
                Order orderResult2 = allValues.get(1);

                assertThat(orderResult1.getId(), is(123L));
                assertThat(orderResult1.getExecutedVolume(), closeTo(100, 0.00001));
                assertThat(orderResult1.getRemainingVolume(), closeTo(100, 0.00001));

                assertThat(orderResult2.getId(), is(nullValue()));
                assertThat(orderResult2.getExecutedVolume(), closeTo(100, 0.00001));
                assertThat(orderResult2.getRemainingVolume(), closeTo(0, 0.00001));
            }

            @DisplayName("should trade with limit order")
            @Test
            void shouldTradeWithLimitOrder2() {
                when(mockOrderRepository.findByTypeAndSideAndStateInOrderByPriceAscCreatedAtDesc(
                        OrderType.LIMIT.getType(),
                        OrderSide.BID.getSide(),
                        List.of(OrderStatus.OPENED.getValue(), OrderStatus.PROCESSING.getValue())))
                        .thenReturn(List.of(order1, order2, order3));

                subject.order(234, OrderSide.ASK.getSide(), OrderType.MARKET.getType(), 0, 300, 456);

                verify(mockOrderRepository, times(3)).save(orderDaoCaptor.capture());
                List<Order> allValues = orderDaoCaptor.getAllValues();

                Order orderResult1 = allValues.get(0);
                Order orderResult2 = allValues.get(1);
                Order orderResult3 = allValues.get(2);

                assertThat(orderResult1.getId(), is(123L));
                assertThat(orderResult1.getExecutedVolume(), closeTo(200, 0.00001));
                assertThat(orderResult1.getRemainingVolume(), closeTo(0, 0.00001));

                assertThat(orderResult2.getId(), is(234L));
                assertThat(orderResult2.getExecutedVolume(), closeTo(100, 0.00001));
                assertThat(orderResult2.getRemainingVolume(), closeTo(100, 0.00001));

                assertThat(orderResult3.getId(), is(nullValue()));
                assertThat(orderResult3.getExecutedVolume(), closeTo(300, 0.00001));
                assertThat(orderResult3.getRemainingVolume(), closeTo(0, 0.00001));
            }

            @DisplayName("when there are not enough maker, should make error")
            @Test
            void whenThereAreNotEnoughMakerShouldMakeError() {
                when(mockOrderRepository.findByTypeAndSideAndStateInOrderByPriceAscCreatedAtDesc(
                        OrderType.LIMIT.getType(),
                        OrderSide.BID.getSide(),
                        List.of(OrderStatus.OPENED.getValue(), OrderStatus.PROCESSING.getValue())))
                        .thenReturn(List.of(order1, order2, order3));


                assertThrows(InsufficientMaker.class, () -> subject.order(234, OrderSide.ASK.getSide(), OrderType.MARKET.getType(), 0, 601, 456));
            }
        }

    }

    @DisplayName("when submitted order is limit order,")
    @Nested 
    class LimitOrderTest {

        @DisplayName("result must have currency code matched to requested market code")
        @Test
        void resultHasCorrectCurrencyCode() {
            OrderVO result = subject.order(BTC_CURRENCY_ID, OrderSide.BID.getSide(), OrderType.LIMIT.getType(), 0,  100, USER_ID);
            assertThat(result.getCurrency(), is(BTC_CURRENCY_ID));
        }

        @DisplayName("result must have currency code matched to requested market code")
        @Test
        void resultHasCorrectCurrencyCode2() {
            OrderVO result = subject.order(ETH_CURRENCY_ID, OrderSide.BID.getSide(), OrderType.LIMIT.getType(), 0,  100, USER_ID);
            assertThat(result.getCurrency(), is(ETH_CURRENCY_ID));
        }

        @DisplayName("result must have volume close to requested volume")
        @Test
        void resultHasCorrectVolume() {
            OrderVO result = subject.order(BTC_CURRENCY_ID, OrderSide.BID.getSide(), OrderType.LIMIT.getType(), 0,  200, USER_ID);
            assertThat(result.getVolume(), closeTo(200, 0.00001));
        }

        @DisplayName("result must have executed volume that is 0")
        @Test
        void resultHasCorrectExecutedVolume() {
            OrderVO result = subject.order(BTC_CURRENCY_ID, OrderSide.BID.getSide(), OrderType.LIMIT.getType(), 0,  100, USER_ID);
            assertThat(result.getExecutedVolume(), closeTo(0, 0.00001));
        }

        @DisplayName("result must have remaining volume equals to requested volume")
        @Test
        void resultHasCorrectRemainingVolume() {
            OrderVO result = subject.order(BTC_CURRENCY_ID, OrderSide.BID.getSide(), OrderType.LIMIT.getType(), 0,  100, USER_ID);
            assertThat(result.getRemainingVolume(), closeTo(100, 0.00001));
        }

        @DisplayName("result must have remaining volume equals to requested volume")
        @Test
        void resultHasCorrectRemainingVolume2() {
            OrderVO result = subject.order(BTC_CURRENCY_ID, OrderSide.BID.getSide(), OrderType.LIMIT.getType(), 0,  200, USER_ID);
            assertThat(result.getRemainingVolume(), closeTo(200, 0.00001));
        }

        @DisplayName("result must have order side equals to requested that")
        @Test
        void resultHasCorrectSide() {
            OrderVO result = subject.order(BTC_CURRENCY_ID, OrderSide.ASK.getSide(), OrderType.LIMIT.getType(), 0, 200, USER_ID);
            assertThat(result.getSide(), is(OrderSide.ASK.getSide()));
        }

        @DisplayName("result must have order order equals to requested that")
        @Test
        void resultHasCorrectOrder() {
            OrderVO result = subject.order(BTC_CURRENCY_ID, OrderSide.ASK.getSide(), OrderType.LIMIT.getType(), 0, 200, USER_ID);
            assertThat(result.getUser(), is(USER_ID));
        }

        @DisplayName("should save a market order on database")
        @Test
        void shouldSaveMarketOrder() {
            subject.order(BTC_CURRENCY_ID, OrderSide.ASK.getSide(), OrderType.LIMIT.getType(), 0, 200, USER_ID);

            verify(mockOrderRepository).save(orderDaoCaptor.capture());

            Order value = orderDaoCaptor.getValue();

            assertThat(value.getExecutedVolume(), closeTo(0, 0.00001));
        }
    }
}