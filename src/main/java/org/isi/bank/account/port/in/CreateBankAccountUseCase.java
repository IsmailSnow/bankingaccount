package org.isi.bank.account.port.in;


import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.service.command.CreateAccountCommand;

/*
   Interface For Create Account Use Case
*/
public interface CreateBankAccountUseCase {
    /*
      @Method Create Bank Account
      @Param CreateAccountCommand command
      @Return BankAccount
    */
    BankAccount createBankAccount(CreateAccountCommand command);
}
