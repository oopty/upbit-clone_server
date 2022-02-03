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
        }

        @Test
        void whenSubmitMarketOrderForETHCreateAndReceiveOrderInfo() {
            OrderVO result = orderService.order("ETH/KRW", BID, PRICE, 0,  100);
            assertThat(result.getCurrency(), is(ETH_CURRENCY_ID));
        }

        @Test
        void whenSubmitMarketOrderCreateAndReceiveCorrectVolumeOfOrderInfo() {
            OrderVO result = orderService.order("BTC/KRW", BID, PRICE, 0,  200);
            assertThat(result.getVolume(), closeTo(200, 0.00001));
        }
    }
    
    @Nested 
    class LimitOrderTest {

    }
}