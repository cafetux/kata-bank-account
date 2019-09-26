package fr.bank.account;

import fr.bank.account.business.AccountNumber;
import fr.bank.account.business.BankAccountService;
import fr.bank.account.business.Days;
import fr.bank.account.business.PositiveAmount;
import fr.bank.account.business.statement.Amount;
import fr.bank.account.business.statement.History;
import fr.bank.account.business.statement.Statement;
import fr.bank.account.business.transaction.Transaction;
import fr.bank.account.business.transaction.Transactions;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class BankAccountServiceTest {

    private Transactions repository = Mockito.mock(Transactions.class);
    private Days clock = Mockito.mock(Days.class);

    private BankAccountService sut = new BankAccountService(repository, clock);
    private History history;


    @Test
    public void should_deposit() {
        when(clock.today()).thenReturn(LocalDate.now());
        when_deposit_amount(25000);
        then_save_transaction_with_positive_amount();
    }

    @Test
    public void should_withdraw() {
        when(clock.today()).thenReturn(LocalDate.now().minusDays(4));
        when_withdraw(5000);
        then_save_transaction_with_negative_amount();
    }

    @Test
    public void should_retrieve_transaction_for_account() {
        Given_one_transaction_for_this_account();
        when_we_retrieve_history();
        assertThat(history.statements()).hasSize(1);
        assertThat(history.statements()).containsExactly(new Statement(LocalDate.now().minusDays(2),Amount.fromCents(20000),Amount.fromCents(20000)));
    }

    @Test
    public void should_retrieve_balance_for_each_statements() {

        Given_transactions_for_this_account(deposit(20000),withdraw(6000));
        when_we_retrieve_history();
        assertThat(history.statements()).hasSize(2);
        assertThat(history.statements()).containsExactly(
                new Statement(LocalDate.now().minusDays(2),Amount.fromCents(20000),Amount.fromCents(20000)),
                new Statement(LocalDate.now(),Amount.fromCents(-6000),Amount.fromCents(14000))
                );
    }

    private void when_we_retrieve_history() {
        this.history = sut.history(account("12365424"));
    }

    private void Given_transactions_for_this_account(Transaction... ts) {
        when(repository.all(account("12365424"))).thenReturn(Arrays.stream(ts).collect(Collectors.toList()));
    }

    private Transaction deposit(int cents) {
        return new Transaction(LocalDate.now().minusDays(2),account("12365424"), Amount.fromCents(cents));
    }
    private Transaction withdraw(int i) {
        return new Transaction(LocalDate.now(),account("12365424"), Amount.fromCents(-i));
    }

    private void Given_one_transaction_for_this_account() {
        when(repository.all(account("12365424"))).thenReturn(Arrays.asList(deposit(20000)));
    }


    private void when_withdraw(int cents) {
        sut.withdraw(account("6543223456"), absoluteAmount(cents));
    }

    private void when_deposit_amount(int cents) {
        sut.deposit(account("6543223456"), absoluteAmount(cents));
    }

    private void then_save_transaction_with_positive_amount() {
        verify(repository).save(new Transaction(LocalDate.now(), account("6543223456"), amount(25000)));
    }

    private void then_save_transaction_with_negative_amount() {
        verify(repository).save(new Transaction(LocalDate.now().minusDays(4), account("6543223456"), amount(-5000)));
    }


    private Amount amount(int cents) {
        return Amount.fromCents(cents);
    }

    private PositiveAmount absoluteAmount(int cents) {
        return PositiveAmount.fromCents(cents);
    }

    private AccountNumber account(String id) {
        return new AccountNumber(id);
    }

}