package fr.bank.account.business.statement;

import fr.bank.account.business.AccountNumber;

import java.util.Collections;
import java.util.List;

public class History {

    private final AccountNumber account;
    private final List<Statement> lines;


    public History(AccountNumber account, List<Statement> lines) {
        this.account = account;
        this.lines = lines;
    }

    public AccountNumber accountNumber() {
        return account;
    }

    public List<Statement>  statements() {
        return Collections.unmodifiableList(lines);
    }

}
