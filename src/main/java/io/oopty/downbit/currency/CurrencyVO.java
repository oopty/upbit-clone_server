package io.oopty.downbit.currency;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class CurrencyVO {
    private int id;
    private String code;
}
