package org.isi.bank.account.service.command;


import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class CreateAccountCommand {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private BigDecimal balance;

}
