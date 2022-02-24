package org.isi.bank.account.port.in;


import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.service.command.AccountCommand;

/*
   Interface For Consultation Account Use Case
*/
public interface ConsultBankAccountUseCase {
    /*
      @Method Load Account With Last N Transactions
      @Param AccountCommand command
      @Return BankAccount
      @Throw Account Not Found Exception
    */
    BankAccount laodBankAccountLimitNTransactions(AccountCommand command);
}
