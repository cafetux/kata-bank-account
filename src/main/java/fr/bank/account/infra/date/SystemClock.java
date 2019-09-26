package fr.bank.account.infra.date;

import fr.bank.account.business.date.Days;

import java.time.LocalDate;

public class SystemClock implements Days {

    @Override
    public LocalDate today() {
        return LocalDate.now();
    }
}
