package fr.bank.account.business.statement;

import fr.bank.account.business.AccountNumber;
import fr.bank.account.infra.statement.StringRenderer;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class RendererTest {

    private StringRenderer renderer = new StringRenderer();

    @Test
    public void should_print_header() {
        History history = new History(new AccountNumber("345676543234"), Arrays.asList());

        assertThat(renderer.render(history)).isEqualTo("Statement for Account: 345676543234\n"
                                              +"| Date     | Amount    | Balance |\n"
        );
    }

    @Test
    public void should_print_statements() {
        History history = new History(new AccountNumber("6346787643234"), Arrays.asList(
                new Statement(LocalDate.of(2019, 8, 5), Amount.fromCents(2010),Amount.fromCents(2010)),
                new Statement(LocalDate.of(2019, 9, 10), Amount.fromCents(-3000),Amount.fromCents(-1010)),
                new Statement(LocalDate.of(2019, 9, 12), Amount.fromCents(1000),Amount.fromCents(-10))));

        assertThat(renderer.render(history)).isEqualTo("Statement for Account: 6346787643234\n"
                +"| Date     | Amount    | Balance |\n"
                +"| 05/08/19 | 20,10 € | 20,10 € |\n"
                +"| 10/09/19 | -30,00 € | -10,10 € |\n"
                +"| 12/09/19 | 10,00 € | -0,10 € |\n"
        );
    }

}