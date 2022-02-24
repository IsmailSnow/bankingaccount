package org.isi.bank.account.domain.transaction;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
   Class That Represent Transaction Domain Model
*/

@Value
@Builder
public class Transaction implements Comparable<Transaction> {

    private BigDecimal amount;
    private String typeTransaction;
    private LocalDateTime dateTransaction;

    public static Transaction deposit(BigDecimal amount) {
        return Transaction.builder().amount(amount)
                .typeTransaction(TransactionType.DEPOSIT.getType())
                .dateTransaction(LocalDateTime.now())
                .build();
    }

    public static Transaction withdraw(BigDecimal amount) {
        return Transaction.builder().amount(amount)
                .typeTransaction(TransactionType.WITHDRAW.getType())
                .dateTransaction(LocalDateTime.now())
                .build();
    }


    @Override
    public int compareTo(Transaction transaction) {
        if (getDateTransaction() == null || transaction.getDateTransaction() == null) return 0;
        return transaction.getDateTransaction().compareTo(getDateTransaction());
    }
}
