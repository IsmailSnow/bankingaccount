package org.isi.bank.account.port.out;

import org.isi.bank.account.domain.account.BankAccount;

import java.util.Optional;

/*
   Interface For Account Crud Operation
*/
public interface AccountPort {
    /*
      @Method loadAccountLimitNTransactionsByIdAndLimit
      @Param int accountNumber | int limintTransaction
      @Return Optional<BankAccount>
    */

    Optional<BankAccount> loadAccountLimitNTransactionsByIdAndLimit(int accountNumber, int limitTransaction);

    /*
      @Method loadAccount
      @Param int accountNumber
      @Return Optional<BankAccount>
    */
    Optional<BankAccount> loadAccount(int accountNumber);

    /*
      @Method createAccount
      @Param BankAccount command
      @Return BankAccount
    */
    BankAccount createAccount(BankAccount command);

    /*
      @Method updateAccount
      @Param BankAccount command
      @Return BankAccount
    */
    BankAccount updateAccount(BankAccount bankAccount);

}
