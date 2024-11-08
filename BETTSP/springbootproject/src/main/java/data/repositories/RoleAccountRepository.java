package data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import data.entities.RoleAccountEntity;

@Repository
public interface RoleAccountRepository extends JpaRepository<RoleAccountEntity, Long> {

    List<RoleAccountEntity> findAllByIdRole(Long idRole);

    List<RoleAccountEntity> findAllByIdAccount(Long idAccount);
}
