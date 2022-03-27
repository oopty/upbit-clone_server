package io.oopty.downbit.balance.controller;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
public class CurrencyResponse {
    private String marketCode;
    private double balance;
    private double availableBalance;
    private double lockedBalance;
}
