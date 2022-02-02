package io.oopty.downbit.order;

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
                .avgPrice(100)
                .state("opened")
                .dateTime(LocalDateTime.now())
                .volume(100)
                .executedVolume(0)
                .remainingVolume(100)
                .tradeCount(0)
                .build();
    }

    private int getCurrencyCode(String marketCode) {
        return "BTC/KRW".equals(marketCode) ? 123 : 234;
    }
}
