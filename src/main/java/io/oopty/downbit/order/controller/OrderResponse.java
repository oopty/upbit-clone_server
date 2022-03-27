package io.oopty.downbit.order.controller;

import lombok.*;

import java.time.LocalDateTime;

@Builder(builderMethodName = "OrderResponseBuilder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
class OrderResponse {
    int currency;
    int user;
    String side;
    String type;
    double price;
    String state;
    LocalDateTime createdAt;
    double volume;
    double executedVolume;
    double remainingVolume;
}
