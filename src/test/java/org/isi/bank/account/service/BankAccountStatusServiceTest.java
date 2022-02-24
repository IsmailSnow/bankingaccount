package org.isi.bank.account.service;

import org.isi.bank.account.domain.account.AccountErrorResultException;
import org.isi.bank.account.domain.account.AccountStatus;
import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.domain.account.repository.AccountRepository;
import org.isi.bank.account.domain.transaction.Transaction;
import org.isi.bank.account.domain.transaction.Transactions;
import org.isi.bank.account.service.command.AccountCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccountStatusServiceTest {


    private static AccountRepository repo = new AccountRepository();

    private AccountStatusService accountStatusService = new AccountStatusService(repo);

    @BeforeAll
    public static void setUp() {
        BankAccount bankAccount = BankAccount.builder().transactions(new Transactions()).build();
        IntStream.range(1,20).forEach(value->{
            LocalDateTime localDateTime = LocalDateTime.of(2015, Month.JULY, 29, 19, value, 40);
            Transaction transaction = Transaction.builder().dateTransaction(localDateTime).build();
            bankAccount.getTransactions().add(transaction);
        });
        repo.createAccount(bankAccount);
    }


    @Test
    public void should_throw_error_if_account_toblock_is_not_found() {
        AccountCommand accountCommand = AccountCommand.builder().accountNumber(101).build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            accountStatusService.blockAccount(accountCommand);
        });
        assertEquals("Account not found, id : 101", thrown.getMessage());
    }

    @Test
    public void should_block_account() {
        AccountCommand accountCommand = AccountCommand.builder().accountNumber(1).build();
        BankAccount account = accountStatusService.blockAccount(accountCommand);
        assertEquals(account.getStatus(), AccountStatus.BLOCKED.getStatus());
    }

    @Test
    public void should_throw_error_if_account_toenable_is_not_found() {
        AccountCommand accountCommand = AccountCommand.builder().accountNumber(301).build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            accountStatusService.enableAccount(accountCommand);
        });
        assertEquals("Account not found, id : 301", thrown.getMessage());
    }

    @Test
    public void should_enable_account() {
        AccountCommand accountCommand = AccountCommand.builder().accountNumber(1).build();
        BankAccount account = accountStatusService.enableAccount(accountCommand);
        assertEquals(account.getStatus(), AccountStatus.ENABLED.getStatus());
    }


}
