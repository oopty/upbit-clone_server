package io.oopty.downbit.order;

import io.oopty.downbit.order.constant.OrderStatus;
import io.oopty.downbit.order.repository.OrderDao;
import io.oopty.downbit.order.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {
    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderVO order(int currencyId, String side, String orderType, double price, double volume, int userId) {
        OrderVO order = OrderVO.OrderVOBuilder()
                .id(-1)
                .currency(currencyId)
                .user(userId)
                .side(side)
                .type(orderType)
                .price(price)
                .state(OrderStatus.OPENED.getValue())
                .dateTime(LocalDateTime.now())
                .volume(volume)
                .executedVolume(0)
                .remainingVolume(volume)
                .build();

        OrderDao orderDao = new OrderDao();
        BeanUtils.copyProperties(order, orderDao);
        orderRepository.save(orderDao);

        return order;
    }

    private int getCurrencyCode(String marketCode) {
        switch (marketCode) {
            case "KRW-BTC":
                return 123;
            case "KRW-ETH":
                return 234;
            default:
                return -1;
        }
    }
}
