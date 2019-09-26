package fr.bank.account;

import fr.bank.account.business.AccountNumber;
import fr.bank.account.business.BankAccountService;
import fr.bank.account.business.Days;
import fr.bank.account.business.transaction.Transactions;
import fr.bank.account.business.statement.Amount;
import fr.bank.account.business.statement.History;
import fr.bank.account.business.statement.Statement;
import fr.bank.account.infra.InMemoryTransactionRepository;
import fr.bank.account.infra.SystemClock;
import org.junit.Test;

import java.time.LocalDate;

import static fr.bank.account.business.PositiveAmount.fromCents;
import static org.assertj.core.api.Assertions.assertThat;

public class BankAccountServiceIntegrationTest {

    private Transactions repository = new InMemoryTransactionRepository();

    private Days clock = new SystemClock();
    private BankAccountService service = new BankAccountService(repository, clock);
    private LocalDate today = LocalDate.now();

    @Test
    public void should_save_and_retrieve_transactions() {

        service.withdraw(new AccountNumber("55678754356"), fromCents(1000)); // -10  (-10)
        service.deposit(new AccountNumber("55678754356"), fromCents(1000)); // +10 (0)
        service.deposit(new AccountNumber("55678754356"), fromCents(20000)); // +200 (200)
        service.withdraw(new AccountNumber("55678754356"), fromCents(6000)); // -60  (140)
        service.deposit(new AccountNumber("87543565567"), fromCents(20000)); // autre compte
        service.withdraw(new AccountNumber("55678754356"), fromCents(4000)); // -40  (100)
        service.deposit(new AccountNumber("55678754356"), fromCents(30000)); // +300 (400)

        History history = service.history(new AccountNumber("55678754356"));
        assertThat(history.accountNumber()).isEqualTo(new AccountNumber("55678754356"));
        assertThat(history.statements()).containsExactly(
                new Statement(today, Amount.fromCents(-1000),Amount.fromCents(-1000)),
                new Statement(today, Amount.fromCents(1000),Amount.fromCents(0)),
                new Statement(today, Amount.fromCents(20000),Amount.fromCents(20000)),
                new Statement(today, Amount.fromCents(-6000),Amount.fromCents(14000)),
                new Statement(today, Amount.fromCents(-4000),Amount.fromCents(10000)),
                new Statement(today, Amount.fromCents(30000),Amount.fromCents(40000))
                );

    }

}