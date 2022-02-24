package org.isi.bank.account.service;


import org.isi.bank.account.domain.account.AccountErrorResultException;
import org.isi.bank.account.domain.account.AccountStatus;
import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.domain.account.repository.AccountRepository;
import org.isi.bank.account.domain.transaction.Transaction;
import org.isi.bank.account.domain.transaction.Transactions;
import org.isi.bank.account.service.command.AccountCommand;
import org.isi.bank.account.service.command.CreateAccountCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccountCrudServiceTest {

    private AccountRepository repo = new AccountRepository();

    private AccountCrudService accountCrudService = new AccountCrudService(repo);

    @BeforeEach
    public void setUp() {
        BankAccount bankAccount = BankAccount.builder().transactions(new Transactions()).build();
        IntStream.range(1, 20).forEach(value -> {
            LocalDateTime localDateTime = LocalDateTime.of(2015, Month.JULY, 29, 19, value, 40);
            Transaction transaction = Transaction.builder().dateTransaction(localDateTime).build();
            bankAccount.getTransactions().add(transaction);
        });
        repo.createAccount(bankAccount);
    }

    @Test
    public void should_create_account_succesfully() {
        CreateAccountCommand command = CreateAccountCommand.builder()
                .email("email")
                .phone("phone")
                .firstName("firstName")
                .lastName("lastName")
                .build();
        BankAccount account = accountCrudService.createBankAccount(command);
        assertEquals(account.getClient().getEmailOwner(), command.getEmail());
        assertEquals(account.getClient().getPhoneOwner(), command.getPhone());
        assertEquals(account.getClient().getFirstNameOwner(), command.getFirstName());
        assertEquals(account.getClient().getLastNameOwner(), command.getLastName());
        assertEquals(account.getStatus(), AccountStatus.ENABLED.getStatus());
        assertEquals(account.getBalance(), BigDecimal.ZERO);
        assertEquals(account.getTransactions().getTransactions().size(), 0);
        assertEquals(account.getAccountNumber(), 2);
    }


    @Test
    public void should_create_account_succesfully_with_empty_values() {
        CreateAccountCommand command = CreateAccountCommand.builder().build();
        BankAccount account = accountCrudService.createBankAccount(command);
        assertEquals(account.getClient().getEmailOwner(), "");
        assertEquals(account.getClient().getPhoneOwner(), "");
        assertEquals(account.getClient().getFirstNameOwner(), "");
        assertEquals(account.getClient().getLastNameOwner(), "");
        assertEquals(account.getStatus(), AccountStatus.ENABLED.getStatus());
        assertEquals(account.getBalance(), BigDecimal.ZERO);
        assertEquals(account.getTransactions().getTransactions().size(), 0);
        assertEquals(account.getAccountNumber(), 2);
    }

    @Test
    public void should_throw_exception_if_account_not_found() {
        AccountCommand command = AccountCommand.builder().accountNumber(55).limitTransaction(10).build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            accountCrudService.laodBankAccountLimitNTransactions(command);
        });
        assertEquals("Account not found, id : 55", thrown.getMessage());
    }

}
