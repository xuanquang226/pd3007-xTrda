package data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import data.entities.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    
}
