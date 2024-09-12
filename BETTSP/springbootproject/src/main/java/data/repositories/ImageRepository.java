package data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import data.entities.ImageEntity;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    List<ImageEntity> findImagesByIdProduct(Long id);

    @Modifying
    @Transactional
    // @Query("Delete from ImageEntity img where img.idProduct = :id")
    void deleteImagesByIdProduct(@Param("id") Long id);
}
