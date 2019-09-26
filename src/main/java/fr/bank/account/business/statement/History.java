package fr.bank.account.business.statement;

import fr.bank.account.business.AccountNumber;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class History {

    private final AccountNumber account;
    private final List<Statement> lines;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/YY");
    private NumberFormat amountFormatter = new DecimalFormat("#0.00");


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


    public String print() {
        StringBuilder res = new StringBuilder("Statement for Account: "+account.value()).append("\n");
        res.append("| Date     | Amount    | Balance |").append("\n");
        for (Statement line : lines) {
            res
                    .append("| ").append(line.date().format(dateFormatter)).append(" ")
                    .append("| ").append(print(line.amount().cents())).append(" ")
                    .append("| ").append(print(line.balance().cents()))
                    .append(" |\n");
        }
        return res.toString();
    }
    private String print(long cents) {
        return amountFormatter.format((double)cents/100)+" €";
    }

}
