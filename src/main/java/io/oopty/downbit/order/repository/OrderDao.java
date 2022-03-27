package io.oopty.downbit.order.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(schema = "order")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
