package org.isi.bank.account.domain.transaction;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/*
   Class That Represent A Groupd Of Transaction Domain Model
*/

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {

    private List<Transaction> transactions = new LinkedList<>();

    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

}
