package org.isi.bank.account.service;

import org.isi.bank.account.domain.account.AccountErrorResultException;
import org.isi.bank.account.domain.account.AccountStatus;
import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.domain.account.repository.AccountRepository;
import org.isi.bank.account.domain.transaction.Transaction;
import org.isi.bank.account.domain.transaction.Transactions;
import org.isi.bank.account.service.command.DepositMoneyAccountCommand;
import org.isi.bank.account.service.command.WithdrawMoneyAccountCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTransactionServiceTest {

    private AccountRepository repo = new AccountRepository();

    private AccountTransactionService accountTransactionService = new AccountTransactionService(repo);

    @BeforeEach
    public void setUp() {
        BankAccount bankAccount = BankAccount.builder().transactions(new Transactions()).build();
        bankAccount.setBalance(BigDecimal.valueOf(10));
        bankAccount.setStatus(AccountStatus.ENABLED.getStatus());
        BankAccount bankAccount2 = BankAccount.builder().transactions(new Transactions()).build();
        bankAccount2.setBalance(BigDecimal.valueOf(20));
        bankAccount2.setStatus(AccountStatus.BLOCKED.getStatus());
        IntStream.range(1, 20).forEach(value -> {
            LocalDateTime localDateTime = LocalDateTime.of(2015, Month.JULY, 29, 19, value, 40);
            Transaction transaction = Transaction.builder().dateTransaction(localDateTime).build();
            bankAccount.getTransactions().add(transaction);
            bankAccount2.getTransactions().add(transaction);
        });
        repo.createAccount(bankAccount);
        repo.createAccount(bankAccount2);
    }


    @Test
    public void should_throw_error_if_account_to_deposit_is_not_found() {
        DepositMoneyAccountCommand command = DepositMoneyAccountCommand.builder()
                .amount(BigDecimal.valueOf(100))
                .accountNumber(101).build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            accountTransactionService.deposit(command);
        });
        assertEquals("Account not found, id : 101", thrown.getMessage());
    }

    @Test
    public void should_throw_error_if_account_to_deposit_is_blocked() {
        DepositMoneyAccountCommand command = DepositMoneyAccountCommand.builder()
                .amount(BigDecimal.valueOf(100))
                .accountNumber(2).build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            accountTransactionService.deposit(command);
        });
        assertEquals("Account is blocked, id : 2", thrown.getMessage());
    }

    @Test
    public void should_deposit() {
        DepositMoneyAccountCommand command = DepositMoneyAccountCommand.builder()
                .amount(BigDecimal.valueOf(100))
                .accountNumber(1).build();
        BankAccount account = accountTransactionService.deposit(command);
        assertEquals(account.getBalance(), BigDecimal.valueOf(110));
    }

    @Test
    public void should_deposit_for_multiple_operation() {
        DepositMoneyAccountCommand command = DepositMoneyAccountCommand.builder()
                .amount(BigDecimal.valueOf(100))
                .accountNumber(1).build();
        BankAccount firstOne = accountTransactionService.deposit(command);
        assertEquals(firstOne.getBalance(), BigDecimal.valueOf(110));
        BankAccount secondOne = accountTransactionService.deposit(command);
        assertEquals(secondOne.getBalance(), BigDecimal.valueOf(210));
        BankAccount ThirdOne = accountTransactionService.deposit(command);
        assertEquals(ThirdOne.getBalance(), BigDecimal.valueOf(310));
    }

    @Test
    public void should_throw_error_if_account_to_withdraw_not_found() {
        WithdrawMoneyAccountCommand command = WithdrawMoneyAccountCommand.builder()
                .amount(BigDecimal.valueOf(100))
                .accountNumber(101).build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            accountTransactionService.withdraw(command);
        });
        assertEquals("Account not found, id : 101", thrown.getMessage());
    }

    @Test
    public void should_throw_error_if_account_blocked() {
        WithdrawMoneyAccountCommand command = WithdrawMoneyAccountCommand.builder()
                .amount(BigDecimal.valueOf(100))
                .accountNumber(2).build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            accountTransactionService.withdraw(command);
        });
        assertEquals("Account is blocked, id : 2", thrown.getMessage());
    }

    @Test
    public void should_throw_error_if_account_insufficent_money() {
        WithdrawMoneyAccountCommand command = WithdrawMoneyAccountCommand.builder()
                .amount(BigDecimal.valueOf(100))
                .accountNumber(1).build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            accountTransactionService.withdraw(command);
        });
        assertEquals("Sender has not enough money in account : 1", thrown.getMessage());
    }

    @Test
    public void should_withdraw() {
        WithdrawMoneyAccountCommand command = WithdrawMoneyAccountCommand.builder()
                .amount(BigDecimal.valueOf(5))
                .accountNumber(1).build();
        BankAccount account = accountTransactionService.withdraw(command);
        assertEquals(BigDecimal.valueOf(5), account.getBalance());
    }

    @Test
    public void should_withdraw_for_multiple_operation() {
        WithdrawMoneyAccountCommand command = WithdrawMoneyAccountCommand.builder()
                .amount(BigDecimal.valueOf(2))
                .accountNumber(1).build();
        BankAccount account = accountTransactionService.withdraw(command);
        assertEquals(BigDecimal.valueOf(8), account.getBalance());
        BankAccount account1 = accountTransactionService.withdraw(command);
        assertEquals(BigDecimal.valueOf(6), account1.getBalance());
        BankAccount account2 = accountTransactionService.withdraw(command);
        assertEquals(BigDecimal.valueOf(4), account2.getBalance());
    }


}
