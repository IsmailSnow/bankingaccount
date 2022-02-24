package org.isi.bank.account.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.isi.bank.account.domain.account.AccountErrorCode;
import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.port.in.DepositUseCase;
import org.isi.bank.account.port.in.WithDrawUseCase;
import org.isi.bank.account.port.out.AccountPort;
import org.isi.bank.account.service.command.DepositMoneyAccountCommand;
import org.isi.bank.account.service.command.WithdrawMoneyAccountCommand;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/*
 * Service to Execute Account Transaction ==> Deposit/Withdraw
*/

@Slf4j
@Validated
@RequiredArgsConstructor
public class AccountTransactionService implements DepositUseCase, WithDrawUseCase {

    private final AccountPort crudAccountPort;

    @Override
    public BankAccount deposit(@Valid final DepositMoneyAccountCommand command) {
        BankAccount bankAccount = crudAccountPort.loadAccount(command.getAccountNumber())
                .orElseThrow(() -> AccountErrorCode.ACCOUNT_NOT_FOUND.asErrorResult(command.getAccountNumber()));
        bankAccount.deposit(command.getAmount());
        return crudAccountPort.updateAccount(bankAccount);
    }

    @Override
    public BankAccount withdraw(@Valid final WithdrawMoneyAccountCommand command) {
        BankAccount bankAccount = crudAccountPort.loadAccount(command.getAccountNumber())
                .orElseThrow(() -> AccountErrorCode.ACCOUNT_NOT_FOUND.asErrorResult(command.getAccountNumber()));
        bankAccount.withdraw(command.getAmount());
        return crudAccountPort.updateAccount(bankAccount);
    }


}
