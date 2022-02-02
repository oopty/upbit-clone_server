package io.oopty.downbit.order;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    public static final int BTC_CURRENCY_ID = 123;
    public static final int USER_ID = 456;
    public static final String BID = "bid";
    public static final String PRICE = "price";
    public static final String OPENED = "opened";
    public static final int ETH_CURRENCY_ID = 234;

    @Spy
    private OrderService orderService;

    @Nested
    class MarketOrderTest {

        @Test
        void whenSubmitMarketOrderForBTCCreateAndReceiveOrderInfo() {
            OrderVO result = orderService.order("BTC/KRW", BID, PRICE, 0,  100);

            assertThat(result.getCurrency(), is(BTC_CURRENCY_ID));
            assertThat(result.getUser(), is(USER_ID));
            assertThat(result.getSide(), is(BID));
            assertThat(result.getType(), is(PRICE));
            assertThat(result.getAvgPrice(), closeTo(100, 0.00001));
            assertThat(result.getState(), is(OPENED));
            assertThat(result.getDateTime(), any(LocalDateTime.class));
            assertThat(result.getVolume(), closeTo(100, 0.00001));
            assertThat(result.getExecutedVolume(), closeTo(0, 0.00001));
            assertThat(result.getRemainingVolume(), closeTo(100, 0.00001));
            assertThat(result.getTradeCount(), is(0));
        }

        @Test
        void whenSubmitMarketOrderForETHCreateAndReceiveOrderInfo() {
            OrderVO result = orderService.order("ETH/KRW", BID, PRICE, 0,  100);

            assertThat(result.getCurrency(), is(ETH_CURRENCY_ID));
            assertThat(result.getUser(), is(USER_ID));
            assertThat(result.getSide(), is(BID));
            assertThat(result.getType(), is(PRICE));
            assertThat(result.getAvgPrice(), closeTo(100, 0.00001));
            assertThat(result.getState(), is(OPENED));
            assertThat(result.getDateTime(), any(LocalDateTime.class));
            assertThat(result.getVolume(), closeTo(100, 0.00001));
            assertThat(result.getExecutedVolume(), closeTo(0, 0.00001));
            assertThat(result.getRemainingVolume(), closeTo(100, 0.00001));
            assertThat(result.getTradeCount(), is(0));
        }
    }
    
    @Nested 
    class LimitOrderTest {

    }
}