package data.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import data.entities.CartItemEntity;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    Optional<CartItemEntity> findByIdProductAndIdCart(Long idProduct, Long idCart);

    List<CartItemEntity> findAllByIdCartOrderByIdProductAsc(Long idCart);
}
