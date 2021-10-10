package ru.vzotov.fiscal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(JUnit4.class)
public class InnTest {

    private static final long INN10_L = 2310031475L;
    private static final String INN10_S = "2310031475";

    private static final long INN12_L = 645393065232L;
    private static final String INN12_S = "645393065232";

    @Test
    public void value10digits() {
        Assert.assertEquals(INN10_S, new Inn(INN10_L).value());
        Assert.assertEquals(INN10_S, new Inn(INN10_S).value());
    }

    @Test
    public void value12digits() {
        Assert.assertEquals(INN12_S, new Inn(INN12_L).value());
        Assert.assertEquals(INN12_S, new Inn(INN12_S).value());
    }

    @Test
    public void testToString() {
        assertThat(new Inn(INN10_S).toString()).isEqualTo(INN10_S);
        assertThat(new Inn(INN12_S).toString()).isEqualTo(INN12_S);
    }

    @Test
    public void testConstructor() {
        Throwable thrown;

        thrown = catchThrowable(() -> {
            new Inn(null);
        });
        assertThat(thrown)
                .as("Should not accept null constructor arguments")
                .isInstanceOf(IllegalArgumentException.class);

        thrown = catchThrowable(() -> {
            new Inn("12345678901234");
        });
        assertThat(thrown)
                .as("Should not accept too long arguments")
                .isInstanceOf(IllegalArgumentException.class);

        thrown = catchThrowable(() -> {
            new Inn("12345");
        });
        assertThat(thrown)
                .as("Should not accept too short arguments")
                .isInstanceOf(IllegalArgumentException.class);

        thrown = catchThrowable(() -> {
            new Inn("645393065233");
        });
        assertThat(thrown)
                .as("Should not accept incorrect arguments")
                .isInstanceOf(IllegalArgumentException.class);

        thrown = catchThrowable(() -> {
            new Inn(-64539306523L);
        });
        assertThat(thrown)
                .as("Should not accept negative arguments")
                .isInstanceOf(IllegalArgumentException.class);
    }
}
