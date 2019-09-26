package fr.bank.account.business.statement;

import java.util.Objects;

public class Amount {

    public static final Amount ZERO = fromCents(0);
    private final long cents;

    private Amount(long cents) {
        this.cents = cents;
    }

    public static Amount fromCents(long cents) {
        return new Amount(cents);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount = (Amount) o;
        return cents == amount.cents;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cents);
    }

    @Override
    public String toString() {
        return "Amount{" +
                "cents=" + cents +
                '}';
    }

    public Amount plus(Amount amount) {
        return new Amount(amount.cents+this.cents);
    }

    public long cents() {
        return cents;
    }

}
