package mk.ukim.finki.emt.sharedkernel.domain.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.time.Instant;

@Getter
public class DomainEvent {

    private String topic;
    private Instant occurredOn;  //datum koga se slucil eventot

    public DomainEvent(String topic) {
        this.occurredOn = Instant.now();
        this.topic = topic;
    }

    public String toJson() {//kje pravi seriajlizacija na cel event vo json objekt za da se napravi transmisija preku kavka,
        // serijalizacija na sekoj event vo json string
        ObjectMapper objectMapper = new ObjectMapper();
        String output = null;
        try {
            output = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {

        }
        return output;
    }

    public String topic() {
        return topic;
    }

    public static <E extends DomainEvent> E fromJson(String json, Class<E> eventClass) throws JsonProcessingException {//praveme deserializacija koga imame json objekt
        // da go praveme originalnata klasa
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json,eventClass);
    }
}
