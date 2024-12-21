package data.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import data.entities.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Transactional
    Page<OrderEntity> findFirstByIdCustomerOrderByIdDesc(Long idCustomer, Pageable pageable);

    List<OrderEntity> findAllByIdCustomer(Long idCustomer);

    Page<OrderEntity> findManyByIdCustomerOrderByIdDesc(Long idCustomer, Pageable pageable);
}
