package fr.bank.account.business;

import fr.bank.account.business.date.Days;
import fr.bank.account.business.statement.Amount;
import fr.bank.account.business.statement.History;
import fr.bank.account.business.statement.Statement;
import fr.bank.account.business.transaction.Transaction;
import fr.bank.account.business.transaction.Transactions;

import java.util.ArrayList;
import java.util.List;

public class BankAccountService {

    private final Transactions repository;
    private final Days clock;

    public BankAccountService(Transactions repository, Days clock) {
        this.repository = repository;
        this.clock = clock;
    }

    public void deposit(AccountNumber account, PositiveAmount amount) {
        this.repository.save(transaction(account, amount.cents()));
    }

    private Transaction transaction(AccountNumber account, long cents) {
        return new Transaction(clock.today(), account, Amount.fromCents(cents));
    }

    public void withdraw(AccountNumber account, PositiveAmount amount) {
        this.repository.save(transaction(account, -amount.cents()));
    }

    public History history(AccountNumber account) {
        List<Transaction> operations = this.repository.all(account);
        List<Statement> statements = new ArrayList<>();
        for (Transaction operation : operations) {
            statements.add(new Statement(operation.date(), operation.amount(), previousBalance(statements).plus(operation.amount())));
        }
        return new History(account, statements);
    }

    private Amount previousBalance(List<Statement> statements) {
        if(statements.isEmpty()) {
            return Amount.ZERO;
        }
        return statements.get(statements.size()-1).balance();
    }
}
