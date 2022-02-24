package org.isi.bank.account.port.in;


import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.service.command.AccountCommand;

/*
   Interface For Account Status Use Case
*/
public interface AccountStatusUseCase {
    /*
      @Method blockAccount
      @Param AccountCommand command
      @Return BankAccount
      @Throw Account Not Found Exception
    */
    BankAccount blockAccount(AccountCommand command);

    /*
      @Method blockAccount
      @Param AccountCommand command
      @Return BankAccount
      @Throw Account Not Found Exception
    */
    BankAccount enableAccount(AccountCommand command);
}
