package fr.bank.account;

import fr.bank.account.business.AccountNumber;
import fr.bank.account.business.BankAccountService;
import fr.bank.account.business.date.Days;
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

    public static final LocalDate TODAY = LocalDate.now();
    public static final LocalDate TWO_DAYS_AGO = LocalDate.now().minusDays(2);
    private Transactions repository = Mockito.mock(Transactions.class);
    private Days clock = Mockito.mock(Days.class);

    private BankAccountService sut = new BankAccountService(repository, clock);
    private History history;
    private String ACCOUNT_NUMBER = "12365424";


    @Test
    public void should_deposit() {
        given_a_transaction_today();
        when_deposit_amount(25000);
        then_save_transaction_with_positive_amount();
    }

    private void given_a_transaction_today() {
        when(clock.today()).thenReturn(LocalDate.now());
    }

    @Test
    public void should_withdraw() {
        given_a_transaction_days_ago();
        when_withdraw(5000);
        then_save_transaction_with_negative_amount();
    }

    private void given_a_transaction_days_ago() {
        when(clock.today()).thenReturn(TODAY.minusDays(4));
    }

    @Test
    public void should_retrieve_transaction_for_account() {
        Given_one_transaction_for_this_account();
        when_we_retrieve_history();
        assertThat(history.statements()).hasSize(1);
    }

    @Test
    public void should_retrieve_balance_for_each_statements() {

        Given_transactions_for_this_account(deposit(20000, TWO_DAYS_AGO),withdraw(6000, TODAY));
        when_we_retrieve_history();
        assertThat(history.statements()).hasSize(2);
        assertThat(history.statements()).containsExactly(
                statement(TWO_DAYS_AGO, 20000, 20000),
                statement(TODAY, -6000, 14000)
                );
    }

    private Statement statement(LocalDate localDate, int amount, int balance) {
        return new Statement(localDate, Amount.fromCents(amount), Amount.fromCents(balance));
    }

    private void when_we_retrieve_history() {
        this.history = sut.history(account(ACCOUNT_NUMBER));
    }

    private void Given_transactions_for_this_account(Transaction... ts) {
        when(repository.all(account(ACCOUNT_NUMBER))).thenReturn(Arrays.stream(ts).collect(Collectors.toList()));
    }

    private Transaction deposit(int cents, LocalDate date) {
        return new Transaction(date,account(ACCOUNT_NUMBER), Amount.fromCents(cents));
    }
    private Transaction withdraw(int i, LocalDate date) {
        return new Transaction(date,account(ACCOUNT_NUMBER), Amount.fromCents(-i));
    }

    private void Given_one_transaction_for_this_account() {
        when(repository.all(account(ACCOUNT_NUMBER))).thenReturn(Arrays.asList(deposit(20000, TODAY.minusDays(2))));
    }


    private void when_withdraw(int cents) {
        sut.withdraw(account("6543223456"), absoluteAmount(cents));
    }

    private void when_deposit_amount(int cents) {
        sut.deposit(account("6543223456"), absoluteAmount(cents));
    }

    private void then_save_transaction_with_positive_amount() {
        verify(repository).save(new Transaction(TODAY, account("6543223456"), amount(25000)));
    }

    private void then_save_transaction_with_negative_amount() {
        verify(repository).save(new Transaction(TODAY.minusDays(4), account("6543223456"), amount(-5000)));
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