package io.oopty.downbit.order;

import lombok.*;

import java.time.LocalDateTime;

@Builder(builderMethodName = "OrderVOBuilder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class OrderVO {
    int id;
    String uuid;
    int currency;
    int user;
    String side;
    String type;
    double avgPrice;
    String state;
    LocalDateTime dateTime;
    double volume;
    double executedVolume;
    double remainingVolume;
    int tradeCount;
}
