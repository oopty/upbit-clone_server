package io.oopty.downbit.order.controller;

import io.oopty.downbit.currency.CurrencyVO;
import io.oopty.downbit.currency.service.CurrencyService;
import io.oopty.downbit.order.OrderService;
import io.oopty.downbit.order.OrderVO;
import io.oopty.downbit.order.constant.OrderSide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController subject;

    @Mock
    private CurrencyService mockCurrencyService;
    @Mock
    private OrderService mockOrderService;

    @DisplayName("get currencyId and then execute order")
    @Test
    void order() {
        CurrencyVO currencyVO = CurrencyVO.builder().id(123).code("KRW-BTC").build();
        when(mockCurrencyService.getCurrencyByCode("KRW-BTC")).thenReturn(currencyVO);
        when(mockOrderService.order(123, OrderSide.ASK.getSide(), "price", 10000, 10, 789)).thenReturn(OrderVO.OrderVOBuilder().build());
        OrderRequest request = OrderRequest
                .OrderRequestBuilder()
                .side(OrderSide.ASK.getSide())
                .marketCode("KRW-BTC")
                .orderType("price")
                .price(10000)
                .volume(10)
                .build();

        OrderResponse result = subject.order(request);
    }
}