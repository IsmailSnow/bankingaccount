package org.isi.bank.account.service.command;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;


@Value
@Builder
public class WithdrawMoneyAccountCommand{
    @NonNull
    private BigDecimal amount;
    @NonNull
    private Integer accountNumber;
}
