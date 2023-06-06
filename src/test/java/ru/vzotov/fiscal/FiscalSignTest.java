package ru.vzotov.fiscal;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FiscalSignTest {

    @Test
    public void testConstructor() {
        assertThatThrownBy(() -> new FiscalSign(null))
                .as("Should not accept null arguments")
                .isInstanceOf(NullPointerException.class);

        final FiscalSign fiscalSign = new FiscalSign("0396771107");
        assertThat(fiscalSign.value()).isEqualTo(396771107L);

        final FiscalSign that = new FiscalSign(fiscalSign.value());
        assertThat(fiscalSign).isEqualTo(that);
    }
}
