package io.oopty.downbit.order.controller;

import io.oopty.downbit.order.OrderService;
import io.oopty.downbit.order.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponse order(@RequestBody OrderRequest orderRequest) {
        OrderVO orderVO = orderService.order(orderRequest.getMarketCode(),
                    orderRequest.getSide(),
                    orderRequest.getOrderType(),
                    orderRequest.getPrice(),
                    orderRequest.getVolume());

        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(orderVO, orderResponse);
        return orderResponse;
    }
}
