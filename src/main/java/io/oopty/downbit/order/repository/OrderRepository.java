package io.oopty.downbit.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderDao, Long> {
    List<OrderDao> findByTypeAndSideInStateOrderByPriceAscAndCreatedAtDesc(String type, String side, List<String> states);
}
