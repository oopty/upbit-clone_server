package io.oopty.downbit.currency;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "currency")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
@Getter
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
}

