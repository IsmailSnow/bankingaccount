package org.isi.bank.account.service.converter;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.isi.bank.account.domain.client.Client;
import org.isi.bank.account.service.command.CreateAccountCommand;

import java.util.Optional;

@UtilityClass
@Slf4j
public class ToClientConverter {

    public Client toClient(CreateAccountCommand command) {
        return Client.builder()
                .firstNameOwner(Optional.ofNullable(command.getFirstName()).orElse(""))
                .lastNameOwner(Optional.ofNullable(command.getLastName()).orElse(""))
                .emailOwner(Optional.ofNullable(command.getEmail()).orElse(""))
                .phoneOwner(Optional.ofNullable(command.getPhone()).orElse(""))
                .build();
    }
}
