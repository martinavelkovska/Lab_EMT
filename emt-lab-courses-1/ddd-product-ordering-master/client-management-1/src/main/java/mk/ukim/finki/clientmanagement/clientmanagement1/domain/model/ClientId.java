package mk.ukim.finki.clientmanagement.clientmanagement1.domain.model;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class ClientId extends DomainObjectId {

    private ClientId() {
        super(ClientId.randomId(ClientId.class).getId());
    }

    public ClientId(@NonNull String uuid) {
        super(uuid);
    }


    public static ClientId of(String uuid) {
        ClientId c = new ClientId(uuid);
        return c;
    }

}
