package mk.ukim.finki.emt.sharedkernel.domain.events.orders;

import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;

import java.time.Instant;


@Getter
public class NarackaIsInProgress extends DomainEvent {

    private String clientId;
    private Instant inProgressTime;

    public NarackaIsInProgress(String topic) {
        super(TopicHolder.TOPIC_ORDER_IS_IN_PROGRESS);
    }

    public NarackaIsInProgress(String clientId, Instant inProgressTime) {
        super(TopicHolder.TOPIC_ORDER_IS_IN_PROGRESS);
        this.clientId = clientId;
        this.inProgressTime = inProgressTime;
    }
}
