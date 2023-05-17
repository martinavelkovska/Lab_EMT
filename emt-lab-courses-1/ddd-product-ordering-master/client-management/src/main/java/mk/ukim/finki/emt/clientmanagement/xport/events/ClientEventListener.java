package mk.ukim.finki.emt.clientmanagement.xport.events;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.clientmanagement.domain.model.ClientId;
import mk.ukim.finki.emt.clientmanagement.service.ClientService;
import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.NarackaIsCancelled;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.NarackaIsDelivered;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.NarackaIsInProgress;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

//slusa odreden event
@Service
@AllArgsConstructor
public class ClientEventListener {
    private final ClientService clientService;

    @KafkaListener(topics= TopicHolder.TOPIC_ORDER_IS_CANCELLED, groupId = "clientManagement")
    public void consumeOrderIsCancelled(String jsonMessage) {
        try {
            NarackaIsCancelled event = DomainEvent.fromJson(jsonMessage,NarackaIsCancelled.class);
            clientService.OrderIsCancelled(ClientId.of(event.getClientId()), Instant.now());
        } catch (Exception e){

        }

    }

    @KafkaListener(topics= TopicHolder.TOPIC_ORDER_IS_IN_PROGRESS, groupId = "clientManagement")
    public void consumeOrderIsInProgress(String jsonMessage) {
        try {
            NarackaIsInProgress event = DomainEvent.fromJson(jsonMessage,NarackaIsInProgress.class);
            clientService.OrderIsInProgress(ClientId.of(event.getClientId()));
        } catch (Exception e){

        }

    }

    @KafkaListener(topics= TopicHolder.TOPIC_ORDER_IS_DELIVERED, groupId = "clientManagement")
    public void consumeOrderIsDelivered(String jsonMessage) {
        try {
            NarackaIsDelivered event = DomainEvent.fromJson(jsonMessage,NarackaIsDelivered.class);
            clientService.OrderIsDelivered(ClientId.of(event.getClientId()));
        } catch (Exception e){

        }

    }
}
