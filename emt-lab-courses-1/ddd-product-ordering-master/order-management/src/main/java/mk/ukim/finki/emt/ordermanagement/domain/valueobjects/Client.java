package mk.ukim.finki.emt.ordermanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.financial.Money;

@Getter
public class Client  implements ValueObject {

    private final ClientId clientId;

    private String clientName;

    private String clientSurname;


    private int phoneNumber;

    private String address;

    private int brSmetka;

    private Client() {
        this.clientId=ClientId.randomId(ClientId.class);
        this.clientName= "";
        this.clientSurname= "";
        this.phoneNumber=0;
        this.address="";
        this.brSmetka=0;
    }

    @JsonCreator
    //mora da imam komunikacija pomegju dvata ograniceni konteksti preku rest servisi
    // i zatoa e potrebno ovo ke pravi povik do client menagment i koi klienti bi se zemale
    public  Client (@JsonProperty("id")  ClientId clientId,@JsonProperty("clientName")   String clientName, @JsonProperty("clientSurname")   String clientSurname,
                    @JsonProperty("phoneNumber")   int phoneNumber, @JsonProperty("address")  String address,
                    @JsonProperty("clientBrSmetka") int brSmetka) {
        this.clientId=clientId;
        this.clientName = clientName;
        this.clientSurname = clientSurname;
        this.phoneNumber=phoneNumber;
        this.address=address;
        this.brSmetka=brSmetka;
    }
}
