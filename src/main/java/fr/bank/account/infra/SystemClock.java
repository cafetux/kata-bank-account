package fr.bank.account.infra;

import fr.bank.account.business.Days;

import java.time.LocalDate;

public class SystemClock implements Days {

    @Override
    public LocalDate today() {
        return LocalDate.now();
    }
}
