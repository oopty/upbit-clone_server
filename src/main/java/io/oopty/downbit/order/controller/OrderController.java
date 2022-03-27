package io.oopty.downbit.order.controller;

import io.oopty.downbit.currency.CurrencyVO;
import io.oopty.downbit.currency.CurrencyService;
import io.oopty.downbit.order.OrderService;
import io.oopty.downbit.order.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final CurrencyService currencyService;

    public OrderController(OrderService orderService, CurrencyService currencyService) {
        this.orderService = orderService;
        this.currencyService = currencyService;
    }

    @PostMapping
    public OrderResponse order(HttpServletRequest httpServletRequest, @RequestBody OrderRequest orderRequest) {
        CurrencyVO currencyByCode = currencyService.getCurrencyByCode(orderRequest.getMarketCode());

        int userId = (int) httpServletRequest.getSession().getAttribute("userId");
        OrderVO orderVO = orderService.order(currencyByCode.getId(),
                    orderRequest.getSide(),
                    orderRequest.getOrderType(),
                    orderRequest.getPrice(),
                    orderRequest.getVolume(),
                    userId);

        OrderResponse orderResponse = OrderResponse.OrderResponseBuilder().build();
        BeanUtils.copyProperties(orderVO, orderResponse);
        return orderResponse;
    }
}
