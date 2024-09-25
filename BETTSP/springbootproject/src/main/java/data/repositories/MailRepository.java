package data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import data.entities.MailEntity;

@Repository
public interface MailRepository extends JpaRepository<MailEntity, Long> {

}
