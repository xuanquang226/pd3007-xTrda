package data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import data.entities.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

}
