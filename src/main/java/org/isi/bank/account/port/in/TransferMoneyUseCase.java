package org.isi.bank.account.port.in;


import org.isi.bank.account.domain.transfer.Transfer;
import org.isi.bank.account.service.command.TransferMoneyAccountCommand;

/*
   Interface For Transfer Money Use Case
*/
public interface TransferMoneyUseCase {
    /*
      @Method Transfer Money
      @Param TransferMoneyAccountCommand command
      @Return Transfer
      @Throw AccountNotFoundException || NegativeBalanceException || BlockedAccountException || NotSufficientBalanceException
    */
    Transfer transferMoney(TransferMoneyAccountCommand command);
}
