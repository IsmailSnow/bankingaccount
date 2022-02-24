package org.isi.bank.account.domain.client;


import lombok.Builder;
import lombok.Data;

/*
   Class That Represent Client Domain Model
*/

@Data
@Builder
public class Client {
    private String firstNameOwner;
    private String lastNameOwner;
    private String phoneOwner;
    private String emailOwner;
}
