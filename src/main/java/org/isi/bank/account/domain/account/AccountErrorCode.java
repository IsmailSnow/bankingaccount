package org.isi.bank.account.domain.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.MessageFormat;

/*
   Class Error for Account Error Code
*/
@AllArgsConstructor
@Getter
public enum AccountErrorCode {
    INVALID_REQUEST("BAD_REQUEST", "Invalid client request : {0}"),
    INTERNAL_ERROR("INTERNAL_SERVER_ERROR", "Invalid server error"),
    ACCOUNT_NOT_FOUND("NOT_FOUND", "Account not found, id : {0}"),
    ACCOUNT_BLOCKED("BAD_REQUEST", "Account is blocked, id : {0}"),
    SENDER_ACCOUNT_NOT_FOUND("NOT_FOUND", "Sender Account is not found, id : {0}"),
    RECEIVER_ACCOUNT_NOT_FOUND("NOT_FOUND", "Receiver Account is not found, id : {0}"),
    NOT_SUFFICIENT_BALANCE("BAD_REQUEST", "Sender has not enough money in account : {0}"),
    NEGATIVE_AMOUNT("BAD_REQUEST", "Amount must be positive");

    private String status;
    private String message;

    public AccountErrorResultException asErrorResult(final Object... params) {
        return AccountErrorResultException.builder()
                .name(name())
                .type(name())
                .message(MessageFormat.format(message, params))
                .build();
    }
}
