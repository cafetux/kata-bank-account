package fr.bank.account.business.transaction;

import fr.bank.account.business.AccountNumber;
import fr.bank.account.business.statement.Amount;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction {

    private final LocalDate date;
    private final AccountNumber account;
    private final Amount amount;

    public Transaction(LocalDate date, AccountNumber account, Amount amount) {
        this.date = date;
        this.account = account;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(account, that.account) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, account, amount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", account=" + account +
                ", amount=" + amount +
                '}';
    }

    public LocalDate date() {
        return date;
    }

    public Amount amount() {
        return amount;
    }

    public AccountNumber account() {
        return account;
    }
}
