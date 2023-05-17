package mk.ukim.finki.emt.ordermanagement.domain.valueobjects;

import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class ClientId extends DomainObjectId {

    private ClientId() {
        super(ClientId.randomId(ClientId.class).getId());
    }

    public ClientId(String uuid) {
        super(uuid);
    }
}
