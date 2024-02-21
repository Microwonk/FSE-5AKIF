package net.microwonk.studentenverwaltung.repositories;

import net.microwonk.studentenverwaltung.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,String> {
    Optional<Client> findClientByLogin(String login);
}
