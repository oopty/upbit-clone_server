package io.oopty.downbit.order;

import lombok.*;

import java.time.LocalDateTime;

@Builder(builderMethodName = "OrderVOBuilder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
public class OrderVO {
    int id;
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
