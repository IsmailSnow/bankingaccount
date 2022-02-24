package org.isi.bank.account.domain.transaction;


/*
   Enum Transaction Type
*/
public enum TransactionType {

    WITHDRAW("WITHDRAW"), DEPOSIT("DEPOSIT");

    private final String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
