package org.isi.bank.account.domain.transfer.repository;

import org.isi.bank.account.domain.account.BankAccount;
import org.isi.bank.account.domain.transfer.Transfer;
import org.isi.bank.account.domain.transfer.TransferStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferRepositoryTest {

    private TransferRepository transferRepository;

    @BeforeEach
    public void init() {
        transferRepository = new TransferRepository();
    }

    @Test
    @DisplayName("Should Save Transfer With Status Successful")
    public void should_save_transfer_with_status_successful() {
        //given
        BankAccount bankAccountReceiver = BankAccount.builder().accountNumber(1).build();
        BankAccount bankAccountSender = BankAccount.builder().accountNumber(2).build();
        Transfer transfer = Transfer.builder()
                .amount(BigDecimal.valueOf(50))
                .bankAccountSender(bankAccountSender)
                .bankAccountReceiver(bankAccountReceiver)
                .createdAt(LocalDateTime.now())
                .status(TransferStatus.SUCCESSFUL)
                .build();
        //when
        Transfer savedTransfer = transferRepository.createTransfer(transfer);
        assertEquals(transfer.getAmount(), savedTransfer.getAmount());
        assertEquals(transfer.getCreatedAt(), savedTransfer.getCreatedAt());
        assertEquals(transfer.getStatus(), savedTransfer.getStatus());
        assertEquals(transfer.getBankAccountReceiver(), savedTransfer.getBankAccountReceiver());
        assertEquals(transfer.getBankAccountSender(), savedTransfer.getBankAccountSender());
        assertEquals(transfer.getStatus(), savedTransfer.getStatus());
    }

    @Test
    @DisplayName("Should Save Transfer With Status Failed")
    public void should_save_transfer_with_status_failed() {
        //given
        BankAccount bankAccountReceiver = BankAccount.builder().accountNumber(1).build();
        BankAccount bankAccountSender = BankAccount.builder().accountNumber(2).build();
        Transfer transfer = Transfer.builder()
                .amount(BigDecimal.valueOf(50))
                .bankAccountSender(bankAccountSender)
                .bankAccountReceiver(bankAccountReceiver)
                .createdAt(LocalDateTime.now())
                .status(TransferStatus.FAILED)
                .build();
        //when
        Transfer savedTransfer = transferRepository.createTransfer(transfer);
        assertEquals(transfer.getAmount(), savedTransfer.getAmount());
        assertEquals(transfer.getCreatedAt(), savedTransfer.getCreatedAt());
        assertEquals(transfer.getStatus(), savedTransfer.getStatus());
        assertEquals(transfer.getBankAccountReceiver(), savedTransfer.getBankAccountReceiver());
        assertEquals(transfer.getBankAccountSender(), savedTransfer.getBankAccountSender());
        assertEquals(transfer.getStatus(), savedTransfer.getStatus());
    }

}
