package fr.bank.account.business.transaction;

import fr.bank.account.business.AccountNumber;

import java.util.List;

public interface Transactions {


    void save(Transaction transaction);

    List<Transaction> all(AccountNumber account);

}
