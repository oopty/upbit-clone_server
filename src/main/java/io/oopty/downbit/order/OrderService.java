package io.oopty.downbit.order;

import io.oopty.downbit.currency.Currency;
import io.oopty.downbit.order.constant.OrderSide;
import io.oopty.downbit.order.constant.OrderStatus;
import io.oopty.downbit.order.constant.OrderType;
import io.oopty.downbit.order.exception.InsufficientMaker;
import io.oopty.downbit.order.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderVO order(int currencyId, String side, String orderType, double price, double volume, int userId) {
        OrderVO result = OrderVO.OrderVOBuilder().build();
        Order orderDao = Order.builder()
                .side(side)
                .type(orderType)
                .price(price)
                .state(OrderStatus.OPENED.getValue())
                .createdAt(LocalDateTime.now())
                .volume(volume)
                .executedVolume(0)
                .remainingVolume(volume)
                .build();
        if (orderType.equals(OrderType.MARKET.getType())) {
            List<Order> orders = orderRepository.findByTypeAndSideAndStateInOrderByPriceAscCreatedAtDesc(
                    OrderType.LIMIT.getType(),
                    side.equals(OrderSide.BID.getSide()) ? OrderSide.ASK.getSide() : OrderSide.BID.getSide(),
                    List.of(OrderStatus.OPENED.getValue(),
                            OrderStatus.PROCESSING.getValue())
            );

            for (Order order : orders) {
                if(orderDao.getRemainingVolume() == 0) break;
                double min = Math.min(order.getRemainingVolume(), orderDao.getRemainingVolume());
                order.setExecutedVolume(order.getExecutedVolume() + min);
                order.setRemainingVolume(order.getRemainingVolume() - min);
                orderDao.setExecutedVolume(orderDao.getExecutedVolume() + min);
                orderDao.setRemainingVolume(orderDao.getRemainingVolume() - min);

                orderRepository.save(order);
            }
            if(orderDao.getRemainingVolume() != 0) {
                throw new InsufficientMaker();
            }
        }

        orderRepository.save(orderDao);
        BeanUtils.copyProperties(orderDao, result);
        return result;
    }
}
