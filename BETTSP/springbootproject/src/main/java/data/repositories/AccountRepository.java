package data.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import data.entities.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByIdCustomer(Long idCustomer);

    Optional<AccountEntity> findByUserName(String userName);

    @Transactional
    @Modifying
    @Query("UPDATE AccountEntity a SET a.idCustomer = :idCustomer WHERE a.id = :id")
    void updateIdCustomerById(@Param("idCustomer") Long idCustomer, @Param("id") Long id);
}
