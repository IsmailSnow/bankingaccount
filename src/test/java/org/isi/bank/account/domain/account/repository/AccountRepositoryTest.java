package org.isi.bank.account.domain.account.repository;

import org.isi.bank.account.domain.account.AccountStatus;
import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.domain.transaction.Transaction;
import org.isi.bank.account.domain.transaction.Transactions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountRepositoryTest {

    private AccountRepository accountRepository;
    private final BankAccount bankAccount = BankAccount.builder().accountNumber(1).build();

    @BeforeEach
    public void init() {
        accountRepository = new AccountRepository();
    }

    @Test
    public void should_find_account_by_accountNumber() {
        accountRepository.createAccount(bankAccount);
        BankAccount retrivedAccount = accountRepository.loadAccount(bankAccount.getAccountNumber()).orElse(null);
        assertEquals(bankAccount, retrivedAccount);
    }

    @Test
    public void should_create_account() {
        BankAccount bankAccount = BankAccount.builder().build();
        accountRepository.createAccount(bankAccount);
        BankAccount retrivedAccount = accountRepository.loadAccount(1).orElse(null);
        assertEquals(retrivedAccount, bankAccount);
    }

    @Test
    public void should_update_account_status() {
        BankAccount bankAccount = BankAccount.builder().status(AccountStatus.BLOCKED.getStatus()).build();
        accountRepository.createAccount(bankAccount);
        bankAccount.setStatus(AccountStatus.ENABLED.getStatus());
        BankAccount updatedAccount = accountRepository.updateAccount(bankAccount);
        assertEquals(updatedAccount.getStatus(), AccountStatus.ENABLED.getStatus());
    }

    @Test
    public void should_return_only_last_10_transactions() {
        BankAccount bankAccount = BankAccount.builder().transactions(new Transactions()).build();
        IntStream.range(1,20).forEach(value->{
            LocalDateTime localDateTime = LocalDateTime.of(2015, Month.JULY, 29, 19, value, 40);
            Transaction transaction = Transaction.builder().dateTransaction(localDateTime).build();
            bankAccount.getTransactions().add(transaction);
        });
        accountRepository.createAccount(bankAccount);
        BankAccount createdAccount = accountRepository.createAccount(bankAccount);
        BankAccount retrievedAccount = accountRepository.loadAccountLimitNTransactionsByIdAndLimit(createdAccount.getAccountNumber(),10).orElse(null);
        assertEquals(retrievedAccount.getTransactions().getTransactions().size(),10);
    }

    @Test
    public void should_return_only_last_10_transactions_in_order() {
        BankAccount bankAccount = BankAccount.builder().transactions(new Transactions()).build();
        IntStream.range(1,20).forEach(value->{
            LocalDateTime localDateTime = LocalDateTime.of(2015, Month.JULY, 29, 19, value, 40);
            Transaction transaction = Transaction.builder().dateTransaction(localDateTime).build();
            bankAccount.getTransactions().add(transaction);
        });
        BankAccount createdAccount = accountRepository.createAccount(bankAccount);
        BankAccount retrievedAccount = accountRepository.loadAccountLimitNTransactionsByIdAndLimit(createdAccount.getAccountNumber(),10).orElse(null);
        assertEquals(retrievedAccount.getTransactions().getTransactions().stream().sorted().collect(Collectors.toList()), retrievedAccount.getTransactions().getTransactions());

    }


}
