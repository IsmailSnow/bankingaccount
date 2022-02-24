package org.isi.bank.account.domain.transfer;

import org.isi.bank.account.domain.account.AccountErrorResultException;
import org.isi.bank.account.domain.account.AccountStatus;
import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.domain.transaction.Transaction;
import org.isi.bank.account.domain.transaction.TransactionType;
import org.isi.bank.account.domain.transaction.Transactions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferTest {



    @Test
    public void should_throw_exception_if_negative_amount(){
        BankAccount bankAccountSender = BankAccount.builder().accountNumber(1).balance(BigDecimal.valueOf(10)).status(AccountStatus.ENABLED.getStatus()).build();
        BankAccount bankAccountReceiver = BankAccount.builder().accountNumber(1).balance(BigDecimal.valueOf(10)).status(AccountStatus.ENABLED.getStatus()).build();
        Transfer transfer = Transfer.builder()
                .amount(BigDecimal.valueOf(-20))
                .bankAccountSender(bankAccountSender)
                .bankAccountReceiver(bankAccountReceiver)
                .createdAt(LocalDateTime.now())
                .build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            transfer.transferMoney();
        });
        assertEquals("Amount must be positive", thrown.getMessage());
    }
    @Test
    public void should_throw_exception_if_account_sender_is_blocked(){
        BankAccount bankAccountSender = BankAccount.builder().accountNumber(1).balance(BigDecimal.valueOf(10)).status(AccountStatus.BLOCKED.getStatus()).build();
        BankAccount bankAccountReceiver = BankAccount.builder().accountNumber(1).balance(BigDecimal.valueOf(10)).status(AccountStatus.BLOCKED.getStatus()).build();
        Transfer transfer = Transfer.builder()
                .amount(BigDecimal.valueOf(20))
                .bankAccountSender(bankAccountSender)
                .bankAccountReceiver(bankAccountReceiver)
                .createdAt(LocalDateTime.now())
                .build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            transfer.transferMoney();
        });
        assertEquals("Account is blocked, id : 1", thrown.getMessage());
    }
    @Test
    public void should_throw_exception_if_insufficent_money(){
        BankAccount bankAccountSender = BankAccount.builder().accountNumber(1).balance(BigDecimal.valueOf(10)).status(AccountStatus.ENABLED.getStatus()).build();
        BankAccount bankAccountReceiver = BankAccount.builder().accountNumber(1).balance(BigDecimal.valueOf(10)).status(AccountStatus.ENABLED.getStatus()).build();
        Transfer transfer = Transfer.builder()
                .amount(BigDecimal.valueOf(20))
                .bankAccountSender(bankAccountSender)
                .bankAccountReceiver(bankAccountReceiver)
                .createdAt(LocalDateTime.now())
                .build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            transfer.transferMoney();
        });
        assertEquals("Sender has not enough money in account : 1", thrown.getMessage());
    }

    @Test
    public void should_throw_exception_if_account_receiver_is_blocked(){
        BankAccount bankAccountSender = BankAccount.builder().accountNumber(1).balance(BigDecimal.valueOf(10)).transactions(new Transactions()).status(AccountStatus.ENABLED.getStatus()).build();
        BankAccount bankAccountReceiver = BankAccount.builder().accountNumber(3).balance(BigDecimal.valueOf(10)).transactions(new Transactions()).status(AccountStatus.BLOCKED.getStatus()).build();
        Transfer transfer = Transfer.builder()
                .amount(BigDecimal.valueOf(5))
                .bankAccountSender(bankAccountSender)
                .bankAccountReceiver(bankAccountReceiver)
                .createdAt(LocalDateTime.now())
                .build();
        AccountErrorResultException thrown = Assertions.assertThrows(AccountErrorResultException.class, () -> {
            transfer.transferMoney();
        });
        assertEquals("Account is blocked, id : 3", thrown.getMessage());
    }

    @Test
    public void should_transfer_money(){
        BankAccount bankAccountSender = BankAccount.builder().accountNumber(1).balance(BigDecimal.valueOf(10)).transactions(new Transactions()).status(AccountStatus.ENABLED.getStatus()).build();
        BankAccount bankAccountReceiver = BankAccount.builder().accountNumber(3).balance(BigDecimal.valueOf(10)).transactions(new Transactions()).status(AccountStatus.ENABLED.getStatus()).build();
        Transfer transfer = Transfer.builder()
                .amount(BigDecimal.valueOf(5))
                .bankAccountSender(bankAccountSender)
                .bankAccountReceiver(bankAccountReceiver)
                .createdAt(LocalDateTime.now())
                .build();
        transfer.transferMoney();
        assertEquals(BigDecimal.valueOf(15), bankAccountReceiver.getBalance());
        assertEquals(BigDecimal.valueOf(5), bankAccountSender.getBalance());
    }

    @Test
    public void should_create_transaction_when_transfer_money(){
        BankAccount bankAccountSender = BankAccount.builder().accountNumber(1).balance(BigDecimal.valueOf(10)).transactions(new Transactions()).status(AccountStatus.ENABLED.getStatus()).build();
        BankAccount bankAccountReceiver = BankAccount.builder().accountNumber(3).balance(BigDecimal.valueOf(10)).transactions(new Transactions()).status(AccountStatus.ENABLED.getStatus()).build();
        Transfer transfer = Transfer.builder()
                .amount(BigDecimal.valueOf(5))
                .bankAccountSender(bankAccountSender)
                .bankAccountReceiver(bankAccountReceiver)
                .createdAt(LocalDateTime.now())
                .build();
        transfer.transferMoney();
        Transaction transactionWithDraw = bankAccountSender.getTransactions().getTransactions().get(0);
        Transaction transactionDeposit = bankAccountReceiver.getTransactions().getTransactions().get(0);
        assertEquals(BigDecimal.valueOf(5), transactionWithDraw.getAmount());
        assertEquals(transfer.getCreatedAt(), transactionWithDraw.getDateTransaction());
        assertEquals(transactionDeposit.getTypeTransaction(), TransactionType.DEPOSIT.getType());
        assertEquals(BigDecimal.valueOf(5), transactionWithDraw.getAmount());
        assertEquals(transfer.getCreatedAt(), transactionDeposit.getDateTransaction());
        assertEquals(transactionWithDraw.getTypeTransaction(), TransactionType.WITHDRAW.getType());

    }





}
