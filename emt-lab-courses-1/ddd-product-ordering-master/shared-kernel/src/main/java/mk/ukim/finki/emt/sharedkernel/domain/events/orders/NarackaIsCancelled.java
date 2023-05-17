package mk.ukim.finki.emt.sharedkernel.domain.events.orders;

import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;

import java.time.Instant;

@Getter
public class NarackaIsCancelled extends DomainEvent {

    private String clientId;
    private Instant cancellationTime;
    private String cancellationReason;

    public NarackaIsCancelled(String topic) {
        super(TopicHolder.TOPIC_ORDER_IS_CANCELLED);
    }

    public NarackaIsCancelled(String clientId, Instant cancellationTime, String cancellationReason) {
        super(TopicHolder.TOPIC_ORDER_IS_CANCELLED);
        this.clientId = clientId;
        this.cancellationTime = cancellationTime;
    }
}
