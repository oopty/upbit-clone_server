package io.oopty.downbit.order.repository;

import io.oopty.downbit.order.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByTypeAndSideInStateOrderByPriceAscAndCreatedAtDesc(String type, String side, List<String> states);
}
