package org.isi.bank.account.domain.account;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

/*
   Class That Represent Account Error Result Exception
*/
@Value
@EqualsAndHashCode(callSuper = true)
@Builder
public class AccountErrorResultException extends RuntimeException {

    private String name;
    private String type;
    private String message;
}
