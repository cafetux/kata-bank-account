package fr.bank.account.business;

import java.util.Objects;

public class AccountNumber {

    private final String value;


    public AccountNumber(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountNumber that = (AccountNumber) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "AccountNumber{" +
                "value='" + value + '\'' +
                '}';
    }

    public String value() {
        return value;
    }
}