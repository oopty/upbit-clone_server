package io.oopty.downbit.order.controller;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "OrderRequestBuilder")
@ToString
@Getter
class OrderRequest {
    private String marketCode;
    private String side;
    private String orderType;
    private double volume;
    private double price;
}
