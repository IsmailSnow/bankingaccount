package org.isi.bank.account.port.in;


import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.service.command.DepositMoneyAccountCommand;

/*
   Interface For Deposit Account Use Case
*/
public interface DepositUseCase {
    /*
      @Method Deposit Money
      @Param DepositMoneyAccountCommand command
      @Return BankAccount
      @Throw AccountNotFoundException || NegativeBalanceException || BlockedAccountException
    */
    BankAccount deposit(DepositMoneyAccountCommand command);
}
