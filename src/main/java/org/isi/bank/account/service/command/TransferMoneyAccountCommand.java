package org.isi.bank.account.service.command;


import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class TransferMoneyAccountCommand {
    @NonNull
    private Integer senderAccountNumber;
    @NonNull
    private Integer receiverAccountNumber;
    @NonNull
    private BigDecimal amount;
}
