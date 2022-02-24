package org.isi.bank.account.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.isi.bank.account.domain.account.AccountErrorCode;
import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.domain.client.Client;
import org.isi.bank.account.port.in.ConsultBankAccountUseCase;
import org.isi.bank.account.port.in.CreateBankAccountUseCase;
import org.isi.bank.account.port.out.AccountPort;
import org.isi.bank.account.service.command.AccountCommand;
import org.isi.bank.account.service.command.CreateAccountCommand;
import org.isi.bank.account.service.converter.ToClientConverter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j
public class AccountCrudService implements CreateBankAccountUseCase, ConsultBankAccountUseCase {

    private final AccountPort accountPort;

    @Override
    public BankAccount createBankAccount(final CreateAccountCommand command) {
        log.info("Create account by command : {}", command);
        Client newClient = ToClientConverter.toClient(command);
        BankAccount bankAccount = BankAccount.buildNewEnabledAccountWithGivenClient(newClient);
        return accountPort.createAccount(bankAccount);
    }

    @Override
    public BankAccount laodBankAccountLimitNTransactions(final AccountCommand command) {
        log.info("Load account by command : {}", command);
        return accountPort.loadAccountLimitNTransactionsByIdAndLimit(command.getAccountNumber(), command.getLimitTransaction())
                .orElseThrow(() -> AccountErrorCode.ACCOUNT_NOT_FOUND.asErrorResult(command.getAccountNumber()));
    }
}
