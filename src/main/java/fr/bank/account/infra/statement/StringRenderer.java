package fr.bank.account.infra.statement;

import fr.bank.account.business.statement.History;
import fr.bank.account.business.statement.Statement;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;

public class StringRenderer {

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/YY");
    private NumberFormat amountFormatter = new DecimalFormat("#0.00");

    public String render(History history) {
        StringBuilder res = new StringBuilder("Statement for Account: "+history.accountNumber().value()).append("\n");
        res.append("| Date     | Amount    | Balance |").append("\n");
        for (Statement line : history.statements()) {
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