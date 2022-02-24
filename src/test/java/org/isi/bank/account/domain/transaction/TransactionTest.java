package org.isi.bank.account.domain.transaction;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionTest {


    @Test
    public void should_build_transaction_with_status_deposit() {
        Transaction transaction = Transaction.deposit(BigDecimal.valueOf(20));
        assertEquals(transaction.getTypeTransaction(), TransactionType.DEPOSIT.getType());
    }

    @Test
    public void should_build_transaction_with_status_withdraw() {
        Transaction transaction = Transaction.withdraw(BigDecimal.valueOf(20));
        assertEquals(transaction.getTypeTransaction(), TransactionType.WITHDRAW.getType());
    }

}
