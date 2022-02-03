package io.oopty.downbit.order;

import io.oopty.downbit.order.constant.OrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    public OrderVO order(String marketCode, String side, String orderType, double price, double volume) {
        return OrderVO.OrderVOBuilder()
                .id(123)
                .uuid("uuid1")
                .currency(getCurrencyCode(marketCode))
                .user(456)
                .side(side)
                .type(orderType)
                .price(price)
                .state(OrderStatus.OPENED.getValue())
                .dateTime(LocalDateTime.now())
                .volume(volume)
                .executedVolume(0)
                .remainingVolume(volume)
                .tradeCount(0)
                .build();
    }

    private int getCurrencyCode(String marketCode) {
        switch (marketCode) {
            case "BTC/KRW":
                return 123;
            case "ETH/KRW":
                return 234;
            default:
                return -1;
        }
    }
}
