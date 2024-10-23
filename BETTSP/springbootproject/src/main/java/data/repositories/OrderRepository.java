package data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import data.entities.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Transactional
    Page<OrderEntity> findFirstByIdCustomerOrderByIdAsc(Long idCustomer, Pageable pageable);
}
