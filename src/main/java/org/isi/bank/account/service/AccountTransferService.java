package org.isi.bank.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.isi.bank.account.domain.account.AccountErrorCode;
import org.isi.bank.account.domain.account.AccountErrorResultException;
import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.domain.transfer.Transfer;
import org.isi.bank.account.domain.transfer.TransferStatus;
import org.isi.bank.account.port.in.TransferMoneyUseCase;
import org.isi.bank.account.port.out.AccountPort;
import org.isi.bank.account.port.out.TransferPort;
import org.isi.bank.account.service.command.TransferMoneyAccountCommand;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDateTime;


/*
 * Service to Transfer Money From an Account to Another
 */

@Slf4j
@RequiredArgsConstructor
public class AccountTransferService implements TransferMoneyUseCase {

    private final AccountPort accountPort;
    private final TransferPort transferPort;

    @Override
    public Transfer transferMoney(final TransferMoneyAccountCommand command) {
        log.info("Transfer money with command : {}", command);
        BankAccount bankAccountReceiver = accountPort.loadAccount(command.getReceiverAccountNumber())
                .orElseThrow(() -> AccountErrorCode.RECEIVER_ACCOUNT_NOT_FOUND.asErrorResult(command.getReceiverAccountNumber()));
        BankAccount bankAccountSender = accountPort.loadAccount(command.getSenderAccountNumber())
                .orElseThrow(() -> AccountErrorCode.SENDER_ACCOUNT_NOT_FOUND.asErrorResult(command.getSenderAccountNumber()));
        Transfer transfer = Transfer.builder()
                .amount(command.getAmount())
                .bankAccountSender(bankAccountSender)
                .bankAccountReceiver(bankAccountReceiver)
                .createdAt(LocalDateTime.now())
                .build();
        try {
            transfer.transferMoney();
            accountPort.updateAccount(bankAccountSender);
            accountPort.updateAccount(bankAccountReceiver);
            transfer.setTransferStatus(TransferStatus.SUCCESSFUL);
            log.error("Transaction Success !");
            return transferPort.createTransfer(transfer);
        } catch (final AccountErrorResultException ex) {
            log.error("Transaction failed : {}", ex.getMessage());
            transfer.setTransferStatus(TransferStatus.FAILED);
            transferPort.createTransfer(transfer);
            throw ex;
        }
    }

}
