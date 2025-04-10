package data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import data.entities.OrderLineEntity;
import jakarta.transaction.Transactional;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLineEntity, Long> {

    @Transactional
    @Query("Select ol From OrderLineEntity ol where ol.idOrder = :idOrder")
    List<OrderLineEntity> findManyOrderLineByIdOrder(@Param("idOrder") Long idOrder);

    List<OrderLineEntity> findManyOrderLineByIdOrderIn(List<Long> idOrderList);
}
