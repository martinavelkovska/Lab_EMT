package mk.ukim.finki.emt.clientmanagement.service;

import mk.ukim.finki.emt.clientmanagement.domain.model.Client;
import mk.ukim.finki.emt.clientmanagement.domain.model.ClientId;
import mk.ukim.finki.emt.clientmanagement.service.forms.ClientForm;

import java.time.Instant;
import java.util.List;

public interface ClientService {

        Client findById(ClientId id);
        Client createClient(ClientForm clientForm);
        List<Client> getAll();
        boolean canMakeOrder(ClientId clientId);
        void deleteClient(ClientId clientId);

        void OrderIsCancelled(ClientId clientId, Instant eventTimestamp);

        void OrderIsInProgress(ClientId clientId);

        void OrderIsDelivered(ClientId clientId);
}
