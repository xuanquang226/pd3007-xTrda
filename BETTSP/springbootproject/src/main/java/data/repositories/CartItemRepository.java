package data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import data.entities.CartItemEntity;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

}
