package org.isi.bank.account.domain.account.repository;

import lombok.extern.slf4j.Slf4j;
import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.domain.transaction.Transaction;
import org.isi.bank.account.domain.transaction.Transactions;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


/*
   Class Converter That Limit Number Of Transactions of a bank account to a given limit
   @Param BankAccount account / int limit
   @Return BankAccount
*/

@Slf4j
public class AccountLimitTransactionConverter {

    public static BankAccount convert(BankAccount account, int limit) {
        log.info("Limit Bank Account Transaction to {}", limit);
        List<Transaction> transactions = account.getTransactions().getTransactions()
                .stream()
                .sorted()
                .limit(limit)
                .collect(Collectors.toCollection(LinkedList::new));
        account.setTransactions(new Transactions(transactions));
        return account;
    }

}
