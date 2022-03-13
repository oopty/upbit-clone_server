package io.oopty.downbit.order.repository;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(schema = "order")
@Data
public class OrderDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    int currency;
    int user;
    String side;
    String type;
    double avg_price;
    String state;
    LocalDateTime created_at;
    double volume;
    double executed_volume;
    double remaining_volume;
}
