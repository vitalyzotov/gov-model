package ru.vzotov.fiscal;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class FiscalSignTest {

    @Test
    public void testConstructor() {
        Throwable thrown = catchThrowable(() -> {
            new FiscalSign(null);
        });
        assertThat(thrown)
                .as("Should not accept null arguments")
                .isInstanceOf(IllegalArgumentException.class);

        FiscalSign fiscalSign = new FiscalSign("0396771107");
        assertThat(fiscalSign.value()).isEqualTo(396771107L);

        FiscalSign that = new FiscalSign(fiscalSign.value());
        assertThat(fiscalSign).isEqualTo(that);
    }
}
