package fr.bank.account.business.statement;

import java.time.LocalDate;
import java.util.Objects;

public class Statement {

    private final LocalDate date;
    private final Amount amount;
    private final Amount balance;

    public Statement(LocalDate date, Amount amount, Amount balance) {
        this.date = date;
        this.amount = amount;
        this.balance = balance;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statement statement = (Statement) o;
        return Objects.equals(date, statement.date) &&
                Objects.equals(amount, statement.amount) &&
                Objects.equals(balance, statement.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, amount, balance);
    }

    @Override
    public String toString() {
        return "Statement{" +
                "date=" + date +
                ", amount=" + amount +
                ", balance=" + balance +
                '}';
    }

    public LocalDate date() {
        return date;
    }

    public Amount amount() {
        return amount;
    }

    public Amount balance() {
        return balance;
    }
}