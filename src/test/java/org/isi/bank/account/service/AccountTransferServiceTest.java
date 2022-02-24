package org.isi.bank.account.service;

import org.isi.bank.account.domain.account.AccountErrorResultException;
import org.isi.bank.account.domain.account.AccountStatus;
import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.domain.account.repository.AccountRepository;
import org.isi.bank.account.domain.transaction.Transaction;
import org.isi.bank.account.domain.transaction.Transactions;
import org.isi.bank.account.domain.transfer.Transfer;
import org.isi.bank.account.domain.transfer.TransferStatus;
import org.isi.bank.account.domain.transfer.repository.TransferRepository;
import org.isi.bank.account.service.command.AccountCommand;
import org.isi.bank.account.service.command.TransferMoneyAccountCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTransferServiceTest {


    private AccountRepository repo = new AccountRepository();

    private TransferRepository transferRepository = new TransferRepository();

    private AccountTransferService accountTransferService = new AccountTransferService(repo,transferRepository);

    @BeforeEach
    public void setUp() {
        BankAccount bankAccount = BankAccount.builder().transactions(new Transactions()).build();
        bankAccount.setBalance(BigDecimal.valueOf(10));
        bankAccount.setStatus(AccountStatus.ENABLED.getStatus());
        BankAccount bankAccount2 = BankAccount.builder().transactions(new Transactions()).build();
        bankAccount2.setBalance(BigDecimal.valueOf(20));
        bankAccount2.setStatus(AccountStatus.BLOCKED.getStatus());
        BankAccount bankAccount3 = BankAccount.builder().transactions(new Transactions()).build();
        bankAccount3.setBalance(BigDecimal.valueOf(120));
        bankAccount3.setStatus(AccountStatus.ENABLED.getStatus());
        IntStream.range(1, 20).forEach(value -> {
            LocalDateTime localDateTime = LocalDateTime.of(2015, Month.JULY, 29, 19, value, 40);
            Transaction transaction = Transaction.builder().dateTransaction(localDateTime).build();
            bankAccount.getTransactions().add(transaction);
            bankAccount2.getTransactions().add(transaction);
            bankAccount3.getTransactions().add(transaction);
        });
        repo.createAccount(bankAccount);
        repo.createAccount(bankAccount2);
        repo.createAccount(bankAccount3);
    }

    @Test
    public void should_throw_error_if_account_receiver_is_not_found() {
        TransferMoneyAccountCommand command = TransferMoneyAccountCommand.builder()
                .amount(BigDecimal.valueOf(100))
                .receiverAccountNumber(101)
                .senderAccountNumber(103)
                .build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            accountTransferService.transferMoney(command);
        });
        assertEquals("Receiver Account is not found, id : 101", thrown.getMessage());
    }

    @Test
    public void should_throw_error_if_account_sender_is_not_found() {
        TransferMoneyAccountCommand command = TransferMoneyAccountCommand.builder()
                .amount(BigDecimal.valueOf(100))
                .receiverAccountNumber(1)
                .senderAccountNumber(103)
                .build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            accountTransferService.transferMoney(command);
        });
        assertEquals("Sender Account is not found, id : 103", thrown.getMessage());
    }

    @Test
    public void should_throw_error_if_account_sender_is_blocked() {
        TransferMoneyAccountCommand command = TransferMoneyAccountCommand.builder()
                .amount(BigDecimal.valueOf(100))
                .receiverAccountNumber(1)
                .senderAccountNumber(2)
                .build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            accountTransferService.transferMoney(command);
        });
        assertEquals("Account is blocked, id : 2", thrown.getMessage());
    }

    @Test
    public void should_throw_error_if_account_receiver_is_blocked() {
        TransferMoneyAccountCommand command = TransferMoneyAccountCommand.builder()
                .amount(BigDecimal.valueOf(100))
                .receiverAccountNumber(2)
                .senderAccountNumber(3)
                .build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            accountTransferService.transferMoney(command);
        });
        assertEquals("Account is blocked, id : 2", thrown.getMessage());
    }

    @Test
    public void should_throw_error_if_not_enough_money() {
        TransferMoneyAccountCommand command = TransferMoneyAccountCommand.builder()
                .amount(BigDecimal.valueOf(1000))
                .receiverAccountNumber(1)
                .senderAccountNumber(3)
                .build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            accountTransferService.transferMoney(command);
        });
        assertEquals("Sender has not enough money in account : 3", thrown.getMessage());
    }

    @Test
    public void should_transfer_with_success() {
        TransferMoneyAccountCommand command = TransferMoneyAccountCommand.builder()
                .amount(BigDecimal.valueOf(120))
                .receiverAccountNumber(1)
                .senderAccountNumber(3)
                .build();
        Transfer transfer = accountTransferService.transferMoney(command);
        AccountCommand commandSender =AccountCommand.builder().accountNumber(command.getSenderAccountNumber()).limitTransaction(1).build();
        AccountCommand commandReveicer =AccountCommand.builder().accountNumber(command.getReceiverAccountNumber()).limitTransaction(1).build();
        BankAccount sender = repo.loadAccount(commandSender.getAccountNumber()).orElse(null);
        BankAccount receiver = repo.loadAccount(commandReveicer.getAccountNumber()).orElse(null);
        assertEquals(sender.getBalance(), BigDecimal.ZERO);
        assertEquals(receiver.getBalance(), BigDecimal.valueOf(130));
        assertEquals(transfer.getStatus(), TransferStatus.SUCCESSFUL);
    }


}
