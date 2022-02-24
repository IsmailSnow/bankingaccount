package org.isi.bank.account.domain.account.repository;

import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.domain.transaction.Transaction;
import org.isi.bank.account.domain.transaction.Transactions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class AccountLimitTransactionConverterTest {


    @Test
    public void should_convert_with_specific_length_when_given_limit(){
        BankAccount bankAccount = BankAccount.builder().transactions(new Transactions()).build();
        IntStream.range(1,20).forEach(value->{
            LocalDateTime localDateTime = LocalDateTime.of(2015, Month.JULY, 29, 19, value, 40);
            Transaction transaction = Transaction.builder().dateTransaction(localDateTime).build();
            bankAccount.getTransactions().add(transaction);
        });
        BankAccount bankAccount1 = AccountLimitTransactionConverter.convert(bankAccount,10);
        assertEquals(bankAccount1.getTransactions().getTransactions().size(),10);
    }

    @Test
    public void should_convert_with_specific_order_when_given_limit(){
        BankAccount bankAccount = BankAccount.builder().transactions(new Transactions()).build();
        IntStream.range(1,20).forEach(value->{
            LocalDateTime localDateTime = LocalDateTime.of(2015, Month.JULY, 29, 19, value, 40);
            Transaction transaction = Transaction.builder().dateTransaction(localDateTime).build();
            bankAccount.getTransactions().add(transaction);
        });
        BankAccount bankAccount1 = AccountLimitTransactionConverter.convert(bankAccount,10);
        assertEquals(bankAccount1.getTransactions().getTransactions().stream().sorted().collect(Collectors.toList()), bankAccount1.getTransactions().getTransactions());
    }

}
