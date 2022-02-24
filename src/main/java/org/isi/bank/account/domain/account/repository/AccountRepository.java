package org.isi.bank.account.domain.account.repository;


import lombok.extern.slf4j.Slf4j;
import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.port.out.AccountPort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;

/*
   Class Repository that contain method to access to Account Repository
   Using @Component to Make class as singleton
*/

@Component
@Slf4j
public class AccountRepository implements AccountPort {

    private Map<Integer, BankAccount> bankAccountMap;

    public AccountRepository() {
        log.info("init map");
        this.bankAccountMap = new HashMap<>();
    }

    @Override
    public Optional<BankAccount> loadAccount(int accountNumber) {
        log.info("laod account by accountNumber {}",accountNumber);
        return Optional.ofNullable(bankAccountMap.get(accountNumber));
    }

    @Override
    public Optional<BankAccount> loadAccountLimitNTransactionsByIdAndLimit(int accountNumber, int limitTransaction) {
        log.info("laod account by accountNumber {} and limit {}",accountNumber,limitTransaction);
        Function<BankAccount, BankAccount> converter = account -> AccountLimitTransactionConverter.convert(account, limitTransaction);
        return loadAccount(accountNumber).map(converter);
    }

    @Override
    public BankAccount createAccount(BankAccount bankAccount) {
        log.info("create account for command {}",bankAccount);
        int generatedId = getGeneratedId();
        bankAccount.setAccountNumber(generatedId);
        bankAccountMap.put(bankAccount.getAccountNumber(), bankAccount);
        return bankAccount;
    }

    @Override
    public BankAccount updateAccount(BankAccount bankAccount) {
        log.info("update account for command {}",bankAccount);
        bankAccountMap.put(bankAccount.getAccountNumber(), bankAccount);
        return bankAccount;
    }

    private int getGeneratedId() {
        log.info("generate id");
        OptionalInt generatedIdOpt = this.bankAccountMap.keySet().stream().mapToInt(v -> v).max();
        int generatedId = 1;
        if (generatedIdOpt.isPresent()) {
            generatedId = generatedIdOpt.getAsInt() + 1;
        }
        return generatedId;
    }
}
