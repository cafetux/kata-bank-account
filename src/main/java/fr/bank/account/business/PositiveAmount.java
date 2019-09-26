package fr.bank.account.business;

import java.util.Objects;

public class PositiveAmount {

    private final long cents;

    private PositiveAmount(long cents) {
        this.cents = cents;
    }

    public static PositiveAmount fromCents(long cents) {
        if(cents < 0) {
            throw new IllegalArgumentException(cents +" should be positive");
        }
        return new PositiveAmount(cents);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositiveAmount that = (PositiveAmount) o;
        return cents == that.cents;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cents);
    }

    @Override
    public String toString() {
        return "PositiveAmount{" +
                "cents=" + cents +
                '}';
    }

    public long cents() {
        return cents;
    }
}
