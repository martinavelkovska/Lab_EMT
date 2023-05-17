package mk.ukim.finki.emt.ordermanagement.domain.model;

import lombok.Getter;
import lombok.NonNull;
import mk.ukim.finki.emt.ordermanagement.domain.valueobjects.ClientId;
import mk.ukim.finki.emt.ordermanagement.domain.valueobjects.Product;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="orders")
@Getter
public class Order extends AbstractEntity<OrderId> {

    @AttributeOverride(name = "id", column = @Column(name = "client_id", nullable = false))
    private ClientId clientId;

    private Instant vremeNaracka;

    @Enumerated(EnumType.STRING)
    private StatusNaracka statusNaracka;

    @Column(name="order_currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;


    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItemList = new HashSet<>();

    private Order() {
        super(OrderId.randomId(OrderId.class));
    }
    public Order(@NotNull ClientId clientId, Instant now, mk.ukim.finki.emt.sharedkernel.domain.financial.Currency currency) {
        super(OrderId.randomId(OrderId.class));
        this.clientId=clientId;
        this.vremeNaracka = now;
        this.currency = currency;
    }

    public Money total() {
        return orderItemList.stream().map(OrderItem::subtotal).reduce(new Money(currency, 0), Money::add);
    }

    public OrderItem addItem(@NonNull Product product, int qty) { //dodavanje na naracan proizvod vo listata za naracka
        Objects.requireNonNull(product,"product must not be null");
        var item  = new OrderItem(product.getId(),product.getPrice(),qty);
        orderItemList.add(item);
        return item;
    }

    public void removeItem(@NonNull OrderItemId orderItemId) { //brisenje od listata naracki prozivod
        Objects.requireNonNull(orderItemId,"Order Item must not be null");
        orderItemList.removeIf(v->v.getId().equals(orderItemId));
    }
    public void cancelOrder() {
        if (statusNaracka != StatusNaracka.CANCELLED) {
            statusNaracka = StatusNaracka.CANCELLED;
        }
    }

    public void inProgress() {
        if (statusNaracka != StatusNaracka.PROCESSING) {
            statusNaracka = StatusNaracka.PROCESSING;
        }
    }

    public void receiveOrder(){
        if (statusNaracka != StatusNaracka.RECEIVED) {
            statusNaracka = StatusNaracka.RECEIVED;
        }
    }



}
