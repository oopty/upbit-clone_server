package io.oopty.downbit.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "_order")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String side;
    private String type;
    private double price;
    private String state;
    private LocalDateTime createdAt;
    private double volume;
    private double executedVolume;
    private double remainingVolume;
}
