package data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import data.entities.CartEntity;
import jakarta.transaction.Transactional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByIdCustomer(Long idCustomer);

    @Modifying
    @Transactional
    @Query("UPDATE CartEntity c set c.totalPrice = :totalPrice WHERE c.idCustomer = :idCustomer")
    void updateTotalPriceByIdCustomerAndTotalPrice(@Param("totalPrice") String totalPrice,
            @Param("idCustomer") Long idCustomer);
}
