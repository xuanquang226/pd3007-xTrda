package data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import data.entities.CartEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    @Transactional
    Optional<CartEntity> findByIdCustomer(Long idCustomer);

    @Modifying
    @Transactional
    @Query("UPDATE CartEntity c set c.totalPrice = :totalPrice WHERE c.idCustomer = :idCustomer")
    void updateTotalPriceByIdCustomerAndTotalPrice(@Param("totalPrice") String totalPrice,
            @Param("idCustomer") Long idCustomer);
}
