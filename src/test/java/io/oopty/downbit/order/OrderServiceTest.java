package io.oopty.downbit.order;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    public static final int BTC_CURRENCY_ID = 123;
    public static final String BID = "bid";
    public static final String PRICE = "price";
    public static final int ETH_CURRENCY_ID = 234;
    public static final String ASK = "ask";

    @Spy
    private OrderService orderService;

    @Nested
    class MarketOrderTest {

        @Test
        void verifyThatResultHasCorrectCurrencyCode() {
            OrderVO result = orderService.order("BTC/KRW", BID, PRICE, 0,  100);
            assertThat(result.getCurrency(), is(BTC_CURRENCY_ID));
        }

        @Test
        void verifyThatResultHasCorrectCurrencyCode2() {
            OrderVO result = orderService.order("ETH/KRW", BID, PRICE, 0,  100);
            assertThat(result.getCurrency(), is(ETH_CURRENCY_ID));
        }

        @Test
        void verifyThatResultHasCorrectVolume() {
            OrderVO result = orderService.order("BTC/KRW", BID, PRICE, 0,  200);
            assertThat(result.getVolume(), closeTo(200, 0.00001));
        }

        @Test
        void verifyThatResultHasCorrectExecutedVolume() {
            OrderVO result = orderService.order("BTC/KRW", BID, PRICE, 0,  100);
            assertThat(result.getExecutedVolume(), closeTo(0, 0.00001));
        }

        @Test
        void verifyThatResultHasCorrectRemainingVolume() {
            OrderVO result = orderService.order("BTC/KRW", BID, PRICE, 0,  100);
            assertThat(result.getRemainingVolume(), closeTo(100, 0.00001));
        }

        @Test
        void verifyThatResultHasCorrectRemainingVolume2() {
            OrderVO result = orderService.order("BTC/KRW", BID, PRICE, 0,  200);
            assertThat(result.getRemainingVolume(), closeTo(200, 0.00001));
        }
    }
    
    @Nested 
    class LimitOrderTest {

    }
}