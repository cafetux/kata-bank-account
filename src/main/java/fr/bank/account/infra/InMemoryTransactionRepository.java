package fr.bank.account.infra;

import fr.bank.account.business.AccountNumber;
import fr.bank.account.business.transaction.Transaction;
import fr.bank.account.business.transaction.Transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryTransactionRepository implements Transactions {

    private List<Transaction> database = new ArrayList<Transaction>();

    @Override
    public void save(Transaction transaction) {
        this.database.add(transaction);
    }

    @Override
    public List<Transaction> all(AccountNumber account) {
        return this.database.stream().filter(t->t.account().equals(account)).collect(Collectors.toList());
    }
}
