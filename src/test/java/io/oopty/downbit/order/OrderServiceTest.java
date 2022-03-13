package io.oopty.downbit.order;

import io.oopty.downbit.order.constant.OrderSide;
import io.oopty.downbit.order.repository.OrderDao;
import io.oopty.downbit.order.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    public static final int BTC_CURRENCY_ID = 123;
    public static final String PRICE = "price";
    public static final int ETH_CURRENCY_ID = 234;
    public static final int USER_ID = 789;

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository mockOrderRepository;
    
    @Captor
    private ArgumentCaptor<OrderDao> orderDaoCaptor;

    @DisplayName("result must have currency code matched to requested market code")
    @Test
    void resultHasCorrectCurrencyCode() {
        OrderVO result = orderService.order(BTC_CURRENCY_ID, OrderSide.BID.getSide(), PRICE, 0,  100, USER_ID);
        assertThat(result.getCurrency(), is(BTC_CURRENCY_ID));
    }

    @DisplayName("result must have currency code matched to requested market code")
    @Test
    void resultHasCorrectCurrencyCode2() {
        OrderVO result = orderService.order(ETH_CURRENCY_ID, OrderSide.BID.getSide(), PRICE, 0,  100, USER_ID);
        assertThat(result.getCurrency(), is(ETH_CURRENCY_ID));
    }

    @DisplayName("result must have volume close to requested volume")
    @Test
    void resultHasCorrectVolume() {
        OrderVO result = orderService.order(BTC_CURRENCY_ID, OrderSide.BID.getSide(), PRICE, 0,  200, USER_ID);
        assertThat(result.getVolume(), closeTo(200, 0.00001));
    }

    @DisplayName("result must have executed volume that is 0")
    @Test
    void resultHasCorrectExecutedVolume() {
        OrderVO result = orderService.order(BTC_CURRENCY_ID, OrderSide.BID.getSide(), PRICE, 0,  100, USER_ID);
        assertThat(result.getExecutedVolume(), closeTo(0, 0.00001));
    }

    @DisplayName("result must have remaining volume equals to requested volume")
    @Test
    void resultHasCorrectRemainingVolume() {
        OrderVO result = orderService.order(BTC_CURRENCY_ID, OrderSide.BID.getSide(), PRICE, 0,  100, USER_ID);
        assertThat(result.getRemainingVolume(), closeTo(100, 0.00001));
    }

    @DisplayName("result must have remaining volume equals to requested volume")
    @Test
    void resultHasCorrectRemainingVolume2() {
        OrderVO result = orderService.order(BTC_CURRENCY_ID, OrderSide.BID.getSide(), PRICE, 0,  200, USER_ID);
        assertThat(result.getRemainingVolume(), closeTo(200, 0.00001));
    }

    @DisplayName("result must have order side equals to requested that")
    @Test
    void resultHasCorrectSide() {
        OrderVO result = orderService.order(BTC_CURRENCY_ID, OrderSide.ASK.getSide(), PRICE, 0, 200, USER_ID);
        assertThat(result.getSide(), is(OrderSide.ASK.getSide()));
    }

    @DisplayName("result must have order order equals to requested that")
    @Test
    void resultHasCorrectOrder() {
        OrderVO result = orderService.order(BTC_CURRENCY_ID, OrderSide.ASK.getSide(), PRICE, 0, 200, USER_ID);
        assertThat(result.getUser(), is(USER_ID));
    }

    @DisplayName("should save a market order on database")
    @Test
    void shouldSaveMarketOrder() {
        orderService.order(BTC_CURRENCY_ID, OrderSide.ASK.getSide(), PRICE, 0, 200, USER_ID);

        verify(mockOrderRepository).save(orderDaoCaptor.capture());

        OrderDao value = orderDaoCaptor.getValue();

        assertThat(value.getExecuted_volume(), closeTo(0, 0.00001));
    }

    @DisplayName("when submitted order is market order")
    @Nested
    class MarketOrderTest {
    }

    @DisplayName("when submitted order is limit order,")
    @Nested 
    class LimitOrderTest {

    }
}