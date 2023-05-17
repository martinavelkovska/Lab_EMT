package mk.ukim.finki.clientmanagement.clientmanagement1.domain.repository;

import mk.ukim.finki.clientmanagement.clientmanagement1.domain.model.Client;
import mk.ukim.finki.clientmanagement.clientmanagement1.domain.model.ClientId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepostiory  extends JpaRepository<Client, ClientId> {

}
