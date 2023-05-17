package mk.ukim.finki.emt.productcatalog.xport.events;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.productcatalog.domain.models.ProductId;
import mk.ukim.finki.emt.productcatalog.services.ProductService;
import mk.ukim.finki.emt.sharedkernel.domain.config.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.NaracanProizvodIsCreated;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.NaracanProizvodIsRemoved;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductEventListener {

    private final ProductService productService;

    @KafkaListener(topics= TopicHolder.TOPIC_ORDER_ITEM_CREATED, groupId = "productCatalog")
    public void consumeOrderItemCreatedEvent(String jsonMessage) {
        try {
            NaracanProizvodIsCreated event = DomainEvent.fromJson(jsonMessage,NaracanProizvodIsCreated.class);
            productService.orderItemCreated(ProductId.of(event.getProductId()), event.getQuantity());
        } catch (Exception e){

        }

    }

    @KafkaListener(topics= TopicHolder.TOPIC_ORDER_ITEM_REMOVED, groupId = "productCatalog")
    public void consumeOrderItemRemovedEvent(String jsonMessage) {
        try {
            NaracanProizvodIsRemoved event = DomainEvent.fromJson(jsonMessage,NaracanProizvodIsRemoved.class);
            productService.orderItemRemoved(ProductId.of(event.getProductId()), event.getQuantity());
        } catch (Exception e){

        }

    }
}
