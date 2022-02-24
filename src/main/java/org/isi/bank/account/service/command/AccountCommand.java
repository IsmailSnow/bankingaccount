package org.isi.bank.account.service.command;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class AccountCommand {

    @NonNull
    private Integer accountNumber;

    private int limitTransaction;

}
