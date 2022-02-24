package org.isi.bank.account.port.in;


import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.service.command.WithdrawMoneyAccountCommand;

/*
   Interface For Withdraw Money Use Case
*/
public interface WithDrawUseCase {
    /*
      @Method withdraw
      @Param WithdrawMoneyAccountCommand command
      @Return BankAccount
      @Throw AccountNotFoundException || NegativeBalanceException || BlockedAccountException || NotSufficientBalanceException
    */
    BankAccount withdraw(WithdrawMoneyAccountCommand command);
}
