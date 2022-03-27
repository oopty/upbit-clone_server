package io.oopty.downbit.order.controller;

import io.oopty.downbit.currency.CurrencyVO;
import io.oopty.downbit.currency.service.CurrencyService;
import io.oopty.downbit.order.OrderService;
import io.oopty.downbit.order.OrderVO;
import io.oopty.downbit.order.constant.OrderSide;
import io.oopty.downbit.order.constant.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController subject;

    @Mock
    private CurrencyService mockCurrencyService;
    @Mock
    private OrderService mockOrderService;

    @Mock
    private HttpServletRequest mockHttpServletRequest;

    @Mock
    private HttpSession mockHttpSession;

    @DisplayName("get currencyId and then execute order")
    @Test
    void order() {
        when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpSession.getAttribute("userId")).thenReturn(456);

        OrderVO orderVO = OrderVO.OrderVOBuilder()
                .id(123)
                .currency(234)
                .user(456)
                .side(OrderSide.ASK.getSide())
                .type("limit")
                .price(123)
                .state(OrderStatus.OPENED.getValue())
                .createdAt(LocalDateTime.of(2022, 3, 20, 14, 19, 0))
                .volume(200)
                .executedVolume(0)
                .remainingVolume(200)
                .build();
        CurrencyVO currencyVO = CurrencyVO.builder().id(123).code("KRW-BTC").build();
        OrderRequest request = OrderRequest
                .OrderRequestBuilder()
                .side(OrderSide.ASK.getSide())
                .marketCode("KRW-BTC")
                .orderType("limit")
                .price(10000)
                .volume(10)
                .build();
        when(mockCurrencyService.getCurrencyByCode("KRW-BTC")).thenReturn(currencyVO);
        when(mockOrderService.order(123, OrderSide.ASK.getSide(), "limit", 10000, 10, 456))
                .thenReturn(orderVO);

        OrderResponse result = subject.order(mockHttpServletRequest, request);

        assertThat(result.getCurrency()).isEqualTo(234);
        assertThat(result.getUser()).isEqualTo(456);
        assertThat(result.getSide()).isEqualTo(OrderSide.ASK.getSide());
        assertThat(result.getType()).isEqualTo("limit");
        assertThat(result.getPrice()).isEqualTo(123);
        assertThat(result.getState()).isEqualTo(OrderStatus.OPENED.getValue());
        assertThat(result.getCreatedAt()).isEqualTo(LocalDateTime.of(2022, 3, 20, 14, 19, 0));
        assertThat(result.getVolume()).isEqualTo(200);
        assertThat(result.getExecutedVolume()).isEqualTo(0);
        assertThat(result.getRemainingVolume()).isEqualTo(200);
    }
}