package mk.ukim.finki.clientmanagement.clientmanagement1.service.forms;

import lombok.Data;
import mk.ukim.finki.clientmanagement.clientmanagement1.domain.valueobjects.Email;
import mk.ukim.finki.clientmanagement.clientmanagement1.domain.valueobjects.Password;

import javax.validation.constraints.NotNull;

@Data
public class ClientForm {

    @NotNull
    private String clientName;

    @NotNull
    private String clientSurname;

    @NotNull
    private Email email;

    @NotNull
    private Password password;

    @NotNull
    private int phoneNumber;

    @NotNull
    private String address;

    @NotNull
    private int brSmetka;
}
