package org.isi.bank.account.domain.transfer;


import lombok.Builder;
import lombok.Getter;
import org.isi.bank.account.domain.account.BankAccount;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
   Class That Represent Transfer Operation Domain Model
*/
@Getter
@Builder
public class Transfer {

    private int idTransfer;
    private BankAccount bankAccountSender;
    private BankAccount bankAccountReceiver;
    private BigDecimal amount;
    private TransferStatus status;
    private LocalDateTime createdAt;

    public void transferMoney() {
        bankAccountSender.withdraw(amount);
        bankAccountReceiver.deposit(amount);
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.status = transferStatus;
    }

    public void setId(int id) {
        this.idTransfer = id;
    }

}
