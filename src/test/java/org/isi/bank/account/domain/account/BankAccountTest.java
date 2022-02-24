package org.isi.bank.account.domain.account;

import org.isi.bank.account.domain.transaction.Transaction;
import org.isi.bank.account.domain.transaction.TransactionType;
import org.isi.bank.account.domain.transaction.Transactions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccountTest {

    @Test
    public void should_throw_exception_if_negative_amout() {
        BankAccount account = BankAccount.builder().accountNumber(1).balance(BigDecimal.valueOf(10)).build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            account.deposit(BigDecimal.valueOf(-10));
        });
        assertEquals("Amount must be positive", thrown.getMessage());
    }

    @Test
    public void should_throw_exception_if_blocked_account() {
        BankAccount account = BankAccount.builder().accountNumber(1).balance(BigDecimal.valueOf(10)).status(AccountStatus.BLOCKED.getStatus()).build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            account.deposit(BigDecimal.valueOf(10));
        });
        assertEquals("Account is blocked, id : 1", thrown.getMessage());
    }

    @Test
    public void should_deposit() {
        BankAccount account = BankAccount.builder().accountNumber(1)
                .balance(BigDecimal.valueOf(10))
                .status(AccountStatus.ENABLED.getStatus())
                .transactions(new Transactions())
                .build();
        account.deposit(BigDecimal.valueOf(10));
        assertEquals(account.getBalance(), BigDecimal.valueOf(20));
    }

    @Test
    public void should_create_transaction_after_deposit() {
        BankAccount account = BankAccount.builder().accountNumber(1)
                .balance(BigDecimal.valueOf(10))
                .status(AccountStatus.ENABLED.getStatus())
                .transactions(new Transactions())
                .build();
        account.deposit(BigDecimal.valueOf(10));
        Transaction transaction = account.getTransactions().getTransactions().get(0);
        assertEquals(transaction.getTypeTransaction(), TransactionType.DEPOSIT.getType());
        assertEquals(transaction.getAmount(), BigDecimal.valueOf(10));
    }

    @Test
    public void when_withdraw_should_throw_exception_if_negative_amout() {
        BankAccount account = BankAccount.builder().accountNumber(1).balance(BigDecimal.valueOf(10)).build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            account.deposit(BigDecimal.valueOf(-10));
        });
        assertEquals("Amount must be positive", thrown.getMessage());
    }

    @Test
    public void when_withdraw_should_throw_exception_if_blocked_account() {
        BankAccount account = BankAccount.builder().accountNumber(1).balance(BigDecimal.valueOf(10)).status(AccountStatus.BLOCKED.getStatus()).build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            account.withdraw(BigDecimal.valueOf(10));
        });
        assertEquals("Account is blocked, id : 1", thrown.getMessage());
    }

    @Test
    public void when_withdraw_should_throw_exception_if_insufficent_amount() {
        BankAccount account = BankAccount.builder().accountNumber(1).balance(BigDecimal.valueOf(10)).status(AccountStatus.ENABLED.getStatus()).build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            account.withdraw(BigDecimal.valueOf(200));
        });
        assertEquals("Sender has not enough money in account : 1", thrown.getMessage());
    }

    @Test
    public void should_withdraw() {
        BankAccount account = BankAccount.builder().accountNumber(1)
                .balance(BigDecimal.valueOf(10))
                .status(AccountStatus.ENABLED.getStatus())
                .transactions(new Transactions())
                .build();
        account.withdraw(BigDecimal.valueOf(5));
        assertEquals(account.getBalance(), BigDecimal.valueOf(5));
    }

    @Test
    public void should_create_transaction_after_withdraw() {
        BankAccount account = BankAccount.builder().accountNumber(1)
                .balance(BigDecimal.valueOf(10))
                .status(AccountStatus.ENABLED.getStatus())
                .transactions(new Transactions())
                .build();
        account.withdraw(BigDecimal.valueOf(5));
        Transaction transaction = account.getTransactions().getTransactions().get(0);
        assertEquals(transaction.getTypeTransaction(), TransactionType.WITHDRAW.getType());
        assertEquals(transaction.getAmount(), BigDecimal.valueOf(5));
    }

}
