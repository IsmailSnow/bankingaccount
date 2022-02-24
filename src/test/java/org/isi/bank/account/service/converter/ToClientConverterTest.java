package org.isi.bank.account.service.converter;

import org.isi.bank.account.domain.client.Client;
import org.isi.bank.account.service.command.CreateAccountCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToClientConverterTest {

    @Test
    public void should_convert_command_to_client_with_empty_values(){
        CreateAccountCommand command = CreateAccountCommand.builder().build();
        Client client = ToClientConverter.toClient(command);
        assertEquals(client.getEmailOwner(), "");
        assertEquals(client.getFirstNameOwner(), "");
        assertEquals(client.getLastNameOwner(), "");
        assertEquals(client.getPhoneOwner(), "");
    }

    @Test
    public void should_convert_command_to_client_with_same_values(){
        CreateAccountCommand command = CreateAccountCommand.builder()
                .email("email")
                .phone("phone")
                .firstName("firstName")
                .lastName("lastName")
                .build();
        Client client = ToClientConverter.toClient(command);
        assertEquals(client.getEmailOwner(), command.getEmail());
        assertEquals(client.getFirstNameOwner(), command.getFirstName());
        assertEquals(client.getLastNameOwner(), command.getLastName());
        assertEquals(client.getPhoneOwner(), command.getPhone());
    }

}
