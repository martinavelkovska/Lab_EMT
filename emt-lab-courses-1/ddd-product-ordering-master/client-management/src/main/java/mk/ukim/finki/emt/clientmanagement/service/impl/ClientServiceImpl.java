package mk.ukim.finki.emt.clientmanagement.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.clientmanagement.domain.exceptions.ClientIdNotExistException;
import mk.ukim.finki.emt.clientmanagement.domain.model.Client;
import mk.ukim.finki.emt.clientmanagement.domain.model.ClientId;
import mk.ukim.finki.emt.clientmanagement.domain.repository.ClientRepository;
import mk.ukim.finki.emt.clientmanagement.domain.valueobjects.Email;
import mk.ukim.finki.emt.clientmanagement.domain.valueobjects.Password;
import mk.ukim.finki.emt.clientmanagement.service.ClientService;
import mk.ukim.finki.emt.clientmanagement.service.forms.ClientForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@Transactional //vo ramki na edna transakcija kje se izvrsuva
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;



    @Override
    public Client findById(ClientId id) {
        return clientRepository.findById(id).orElseThrow(ClientIdNotExistException::new);
    }

    @Override
    public Client createClient(ClientForm clientForm) {
        Objects.requireNonNull(clientForm,"Client must not be null.");
        Client c = new Client(clientForm.getClientName(), clientForm.getClientSurname(), clientForm.getEmail(), clientForm.getPassword(), clientForm.getPhoneNumber(), clientForm.getAddress(), clientForm.getBrSmetka());
        if(c.getEmail().isValid() && c.getPassword() !=null && c.getEmail()!=null)
             clientRepository.save(c);
        return c;
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public boolean canMakeOrder(ClientId clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(ClientIdNotExistException::new);
        if (client != null) {
            Email email = client.getEmail();
            Password password = client.getPassword();

            // Check if email and password are valid
            boolean isEmailValid = email != null && email.isValid();
            boolean isPasswordValid = password != null;

            return isEmailValid && isPasswordValid;
        }
        return false;
    }

    @Override
    public void deleteClient(ClientId clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(ClientIdNotExistException::new);
        if (client != null) {
            clientRepository.delete(client);
        } else {
            throw new IllegalArgumentException("Client not found.");
        }
    }

    @Override
    public void OrderIsCancelled(ClientId clientId, Instant eventTimestamp) {
        Client client = clientRepository.findById(clientId).orElseThrow(ClientIdNotExistException::new);
        if (client != null) {
            // Perform the necessary actions to handle the order cancellation in the client object
            client.orderCancelled(Instant.now());
            clientRepository.save(client);

        }
    }

    @Override
    public void OrderIsInProgress(ClientId clientId){
        Client client = clientRepository.findById(clientId).orElseThrow(ClientIdNotExistException::new);
        if (client != null) {
            // Perform the necessary actions to handle the order cancellation in the client object
            client.orderInProgress(true);
            clientRepository.save(client);

        }
    }

    @Override
    public void OrderIsDelivered(ClientId clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(ClientIdNotExistException::new);
        if (client != null) {
            // Perform the necessary actions to handle the delivered order in the client object
            // For example, you can update the client's order history, set the order as delivered, etc.

            // Update the order delivered status
            client.setOrderDelivered(true);

            // Perform any other relevant actions or updates

            clientRepository.save(client);
        }
    }

}
