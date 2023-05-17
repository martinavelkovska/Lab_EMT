package mk.ukim.finki.emt.clientmanagement.domain.valueobjects;

import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Email implements ValueObject {
    private final String email;

    protected Email() {
        this.email = null;
    }

    public Email(String email) {
        validateEmailFormat(email);
        this.email = email;
    }

    public void validateEmailFormat(String email) {
        int atIndex = email.indexOf('@');
        int dotIndex = email.lastIndexOf('.');

        if (!(email.contains("@") && email.contains(".com"))){
            throw new IllegalArgumentException("Invalid email format.");
        }

    }
    public boolean isValid() {
        return email != null;
    }
}
