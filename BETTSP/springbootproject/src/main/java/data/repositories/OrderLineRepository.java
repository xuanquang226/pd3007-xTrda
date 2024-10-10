package data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import data.entities.OrderLineEntity;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLineEntity, Long> {

}
