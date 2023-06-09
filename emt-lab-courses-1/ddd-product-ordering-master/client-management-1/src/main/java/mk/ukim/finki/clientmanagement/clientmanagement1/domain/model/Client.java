package mk.ukim.finki.clientmanagement.clientmanagement1.domain.model;

import lombok.Getter;
import mk.ukim.finki.clientmanagement.clientmanagement1.domain.valueobjects.Email;
import mk.ukim.finki.clientmanagement.clientmanagement1.domain.valueobjects.Password;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name="client")
@Getter
public class Client extends AbstractEntity<ClientId> {

    private String clientName;

    private String clientSurname;

    private Email email;

    private Password password;

    private int phoneNumber;

    private String address;

    private int brSmetka;

    private boolean orderCancelled;

    private boolean orderInProgress;

    private boolean orderDelivered;

    private Client() { super(ClientId.randomId(ClientId.class));
    }

    public  Client (String clientName, String clientSurname, Email email, Password password,
                    int phoneNumber,String address,int brSmetka) {
        super(ClientId.randomId(ClientId.class));
        this.clientName = clientName;
        this.clientSurname = clientSurname;
        this.email=email;
        this.password=password;
        this.phoneNumber=phoneNumber;
        this.address=address;
        this.brSmetka=brSmetka;
    }

    public void orderCancelled(Instant eventTimestamp) {
        // Add your logic here to handle the order cancellation
        // For example, update the client's order status, send a notification, etc.
        // You can set a flag or update a property to indicate that the order is cancelled
        // For instance, assuming you have an 'orderCancelled' flag:

        this.orderCancelled = true;
        // Additionally, you can update any other relevant properties or perform other actions

        // You can also log the event timestamp if needed
        System.out.println("Order cancelled at: " + eventTimestamp);
    }

    public void orderInProgress(boolean orderInProgress){
        this.orderInProgress = true;
        System.out.println("Order is in Progress");
    }

    public void  setOrderDelivered(boolean orderDelivered){
        this.orderDelivered=true;
        System.out.println("Order is Delivered");
    }

}
