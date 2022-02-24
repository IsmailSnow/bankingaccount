package org.isi.bank.account.domain.account;

/*
   Enum Account Status
*/
public enum AccountStatus {

    ENABLED("ENABLED"), BLOCKED("BLOCKED");

    private final String status;

    AccountStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
