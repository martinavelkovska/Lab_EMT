package mk.ukim.finki.emt.clientmanagement.domain.valueobjects;

import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class Password implements ValueObject {

    private final String password;

    protected Password() {
        this.password = null;
    }

    public Password(String password) {
        validatePasswordStrength(password);
        this.password = encryptPassword(password);
    }

    public void validatePasswordStrength(String password) {
        // Implement your password strength validation logic here
        // You can check the password length, complexity, or apply any other rules
        // Throw an exception if the password is not strong enough
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }

        if (!password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain both uppercase and lowercase letters.");
        }

        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one digit.");
        }
        if (!password.matches(".*[!@#$%^&*()].*")) {
            throw new IllegalArgumentException("Password must contain at least one special character.");
        }

    }

    public String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public boolean verifyPassword(String passwordToVerify) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(passwordToVerify, this.password);
    }
}
