package org.isi.bank.account.domain.account;


import lombok.Builder;
import lombok.Data;
import org.isi.bank.account.domain.client.Client;
import org.isi.bank.account.domain.transaction.Transaction;
import org.isi.bank.account.domain.transaction.Transactions;

import java.math.BigDecimal;
import java.util.LinkedList;

/*
   Class That Represent BankAccount Domain Model
*/

@Data
@Builder
public class BankAccount {
    private int accountNumber;
    private BigDecimal balance;
    private String status;
    private Client client;
    private Transactions transactions;

    public static BankAccount buildNewEnabledAccountWithGivenClient(Client client) {
        return BankAccount.builder()
                .client(client)
                .status(AccountStatus.ENABLED.getStatus())
                .balance(BigDecimal.ZERO)
                .transactions(new Transactions(new LinkedList<>()))
                .build();
    }

    public void deposit(final BigDecimal amount) {
        checkDepositRule(amount);
        balance = balance.add(amount);
        transactions.add(Transaction.deposit(amount));
    }

    public void withdraw(final BigDecimal amount) {
        checkWithdrawRule(amount);
        balance = balance.subtract(amount);
        transactions.add(Transaction.withdraw(amount));
    }

    private void checkDepositRule(BigDecimal amount) {
        isPositiveAmount(amount);
        isAccountBlocked();
    }

    private void checkWithdrawRule(BigDecimal amount) {
        isPositiveAmount(amount);
        isAccountBlocked();
        isInsufficientBalance(amount);
    }

    private void isInsufficientBalance(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw AccountErrorCode.NOT_SUFFICIENT_BALANCE.asErrorResult(this.accountNumber);
        }
    }

    private void isAccountBlocked() {
        if (this.status.equals(AccountStatus.BLOCKED.getStatus())) {
            throw AccountErrorCode.ACCOUNT_BLOCKED.asErrorResult(this.accountNumber);
        }
    }

    private void isPositiveAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw AccountErrorCode.NEGATIVE_AMOUNT.asErrorResult();
        }
    }

}
