package org.isi.bank.account.service.command;


import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@Builder
public class DepositMoneyAccountCommand {
    @NonNull
    public BigDecimal amount;
    @NonNull
    private Integer accountNumber;
}
