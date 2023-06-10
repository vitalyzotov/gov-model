package ru.vzotov.fiscal;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class InnTest {

    private static final long INN10_L = 2310031475L;
    private static final String INN10_S = "2310031475";

    private static final long INN12_L = 645393065232L;
    private static final String INN12_S = "645393065232";

    @Test
    public void value10digits() {
        assertThat(new Inn(INN10_L).value()).isEqualTo(INN10_S);
        assertThat(new Inn(INN10_S).value()).isEqualTo(INN10_S);
    }

    @Test
    public void value12digits() {
        assertThat(new Inn(INN12_L).value()).isEqualTo(INN12_S);
        assertThat(new Inn(INN12_S).value()).isEqualTo(INN12_S);
    }

    @Test
    public void testToString() {
        assertThat(new Inn(INN10_S).toString()).isEqualTo(INN10_S);
        assertThat(new Inn(INN12_S).toString()).isEqualTo(INN12_S);
    }

    @Test
    public void testConstructor() {
        assertThatThrownBy(() -> new Inn(null))
                .as("Should not accept null constructor arguments")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> new Inn("12345678901234"))
                .as("Should not accept too long arguments")
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Inn("12345"))
                .as("Should not accept too short arguments")
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Inn("645393065233"))
                .as("Should not accept incorrect arguments")
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Inn(-64539306523L))
                .as("Should not accept negative arguments")
                .isInstanceOf(IllegalArgumentException.class);
    }
}
