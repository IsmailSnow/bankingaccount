package org.isi.bank.account.port.out;

import org.isi.bank.account.domain.transfer.Transfer;

/*
   Interface For Transfer Crud Operation
*/
public interface TransferPort {
    /*
      @Method createTransfer
      @Param Transfer command
      @Return Transfer
    */
    Transfer createTransfer(Transfer command);
}
