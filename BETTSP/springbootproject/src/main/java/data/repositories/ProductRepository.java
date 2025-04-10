package data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import data.entities.ProductEntity;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByName(String name);

    List<ProductEntity> findProductsByIdCategory(Long idCategory);

    List<ProductEntity> findProductsByIdIn(List<Long> productIdList);

    ProductEntity findFirstByNameOrderByIdDesc(String userName);
}
