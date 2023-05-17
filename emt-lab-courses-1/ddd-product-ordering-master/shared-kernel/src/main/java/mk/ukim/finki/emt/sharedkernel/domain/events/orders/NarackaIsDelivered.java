package mk.ukim.finki.emt.sharedkernel.domain.events.orders;

import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;

import java.time.Instant;

@Getter
public class NarackaIsDelivered extends DomainEvent {

    private String clientId;
    private Instant cancellationTime;

    public NarackaIsDelivered(String topic) {
        super(TopicHolder.TOPIC_ORDER_IS_DELIVERED);
    }

    public NarackaIsDelivered(String clientId, Instant cancellationTime) {
        super(TopicHolder.TOPIC_ORDER_IS_DELIVERED);
        this.clientId = clientId;
        this.cancellationTime = cancellationTime;
    }
}
