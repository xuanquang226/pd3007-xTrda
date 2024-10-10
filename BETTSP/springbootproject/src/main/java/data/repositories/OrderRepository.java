package data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import data.entities.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
