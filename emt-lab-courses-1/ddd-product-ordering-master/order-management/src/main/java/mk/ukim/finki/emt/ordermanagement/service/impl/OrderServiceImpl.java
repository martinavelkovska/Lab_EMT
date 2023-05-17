package mk.ukim.finki.emt.ordermanagement.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderIdNotExistException;
import mk.ukim.finki.emt.ordermanagement.domain.exceptions.OrderItemIdNotExistException;
import mk.ukim.finki.emt.ordermanagement.domain.model.Order;
import mk.ukim.finki.emt.ordermanagement.domain.model.OrderId;
import mk.ukim.finki.emt.ordermanagement.domain.model.OrderItemId;
import mk.ukim.finki.emt.ordermanagement.domain.model.StatusNaracka;
import mk.ukim.finki.emt.ordermanagement.domain.repository.OrderRepository;
import mk.ukim.finki.emt.ordermanagement.service.OrderService;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderForm;
import mk.ukim.finki.emt.ordermanagement.service.forms.OrderItemForm;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.NaracanProizvodIsCreated;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.NaracanProizvodIsRemoved;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.NarackaIsCancelled;
import mk.ukim.finki.emt.sharedkernel.domain.events.orders.NarackaIsInProgress;
import mk.ukim.finki.emt.sharedkernel.infra.DomainEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.websocket.server.ServerEndpoint;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DomainEventPublisher domainEventPublisher;
    private final Validator validator;

    @Override
    public OrderId placeOrder(OrderForm orderForm) { //kreiraj naracka
        Objects.requireNonNull(orderForm,"order must not be null.");
        var constraintViolations = validator.validate(orderForm);
        if (constraintViolations.size()>0) {
            throw new ConstraintViolationException("The order form is not valid", constraintViolations);
        }
        var newOrder = orderRepository.saveAndFlush(toDomainObject(orderForm));
        newOrder.getOrderItemList().forEach(item->domainEventPublisher.publish(new NaracanProizvodIsCreated(item.getProductId().getId(),item.getQuantity())));
        return newOrder.getId();
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return orderRepository.findById(id);
    }

    @Override
    public void addItem(OrderId orderId, OrderItemForm orderItemForm) throws OrderIdNotExistException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotExistException::new);
        order.addItem(orderItemForm.getProduct(),orderItemForm.getQuantity());
        orderRepository.saveAndFlush(order);
        domainEventPublisher.publish(new NaracanProizvodIsCreated(orderItemForm.getProduct().getId().getId(),orderItemForm.getQuantity()));
    }

    @Override
    public void deleteItem(OrderId orderId, OrderItemId orderItemId, OrderItemForm orderItemForm) throws OrderIdNotExistException, OrderItemIdNotExistException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotExistException::new);
        order.removeItem(orderItemId);
        orderRepository.saveAndFlush(order);
      //  domainEventPublisher.publish(new NaracanProizvodIsRemoved(orderItemForm.getProduct().getId().getId(),orderItemForm.getQuantity()));
    }

    @Override
    public void cancelOrder(OrderId orderId, OrderForm orderForm, String cancellationReason) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotExistException::new);
        if(order!=null) {
            order.cancelOrder();
            orderRepository.saveAndFlush(order);
            domainEventPublisher.publish(new NarackaIsCancelled(orderForm.getClient().getClientId().getId(),  Instant.now(), cancellationReason));
        }
    }


    @Override
    public void inProgress(OrderId orderId, OrderForm orderForm) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotExistException::new);
        Instant orderPlacementTime = order.getVremeNaracka();
        Instant currentTime = Instant.now();
        long hoursSinceOrderPlacement = Duration.between(orderPlacementTime, currentTime).toHours();

        if (hoursSinceOrderPlacement >= 24) {
            order.inProgress();
            orderRepository.saveAndFlush(order);
            domainEventPublisher.publish(new NarackaIsInProgress(orderForm.getClient().getClientId().getId(),  Instant.now()));
        }

    }

    @Override
    public void receiveOrder(OrderId orderId, OrderForm orderForm) {
        Order existingOrder = orderRepository.findById(orderId).orElseThrow(OrderIdNotExistException::new);
        if(existingOrder!=null) {
            existingOrder.receiveOrder();
            orderRepository.saveAndFlush(existingOrder);
            domainEventPublisher.publish(new NarackaIsInProgress(orderForm.getClient().getClientId().getId(),  Instant.now()));
        } else {
            throw new IllegalArgumentException("Order cannot be received. Invalid order status.");
        }
    }


    private Order toDomainObject(OrderForm orderForm) {
        var order = new Order(orderForm.getClient().getClientId(),Instant.now(),orderForm.getCurrency());
        orderForm.getItems().forEach(item->order.addItem(item.getProduct(),item.getQuantity()));
        return order;
    }
}
