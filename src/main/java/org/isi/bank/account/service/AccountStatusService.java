package org.isi.bank.account.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.isi.bank.account.domain.account.AccountErrorCode;
import org.isi.bank.account.domain.account.AccountStatus;
import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.port.in.AccountStatusUseCase;
import org.isi.bank.account.port.out.AccountPort;
import org.isi.bank.account.service.command.AccountCommand;

/*
 * Service to Update Account Status ==> Enable/block
*/
@RequiredArgsConstructor
@Slf4j
public class AccountStatusService implements AccountStatusUseCase {

    private final AccountPort accountPort;

    @Override
    public BankAccount blockAccount(final AccountCommand accountCommand) {
        log.info("Block Account money with command : {}", accountCommand);
        BankAccount bankAccount = accountPort.loadAccount(accountCommand.getAccountNumber())
                .orElseThrow(() -> AccountErrorCode.ACCOUNT_NOT_FOUND.asErrorResult(accountCommand.getAccountNumber()));
        bankAccount.setStatus(AccountStatus.BLOCKED.getStatus());
        return accountPort.updateAccount(bankAccount);
    }

    @Override
    public BankAccount enableAccount(final AccountCommand accountCommand) {
        log.info("Enable Account money with command : {}", accountCommand);
        BankAccount bankAccount = accountPort.loadAccount(accountCommand.getAccountNumber())
                .orElseThrow(() -> AccountErrorCode.ACCOUNT_NOT_FOUND.asErrorResult(accountCommand.getAccountNumber()));
        bankAccount.setStatus(AccountStatus.ENABLED.getStatus());
        return accountPort.updateAccount(bankAccount);
    }
}
