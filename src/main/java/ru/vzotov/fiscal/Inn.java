package ru.vzotov.fiscal;

import org.apache.commons.lang3.Validate;
import ru.vzotov.ddd.shared.ValueObject;

import java.util.Objects;

/**
 * Идентификационный номер налогоплательщика (ИНН) — цифровой код, упорядочивающий учёт налогоплательщиков в Российской Федерации.
 * <p>
 * ИНН физического лица является последовательностью из 12 цифр, из которых первые две представляют собой код субъекта
 * Российской Федерации согласно ст. 65 Конституции, следующие две — номер местной налоговой инспекции,
 * следующие шесть — номер налоговой записи налогоплательщика и последние две — так называемые «контрольные цифры»
 * для проверки правильности записи.
 * <p>
 * ИНН индивидуального предпринимателя присваивается при регистрации физического лица в качестве индивидуального
 * предпринимателя, если данное лицо ранее его не имело. В ином случае используется имеющийся ИНН.
 * <p>
 * ИНН юридического лица является последовательностью из 10 цифр, из которых первые две представляют собой
 * код субъекта Российской Федерации согласно 65 статье Конституции (или «99» для межрегиональной инспекции ФНС),
 * следующие две — номер местной налоговой инспекции, следующие пять — номер налоговой записи налогоплательщика
 * в территориальном разделе ОГРН (Основной государственный регистрационный номер) и последняя —
 * контрольная цифра. ИНН вместе с КПП — (Код причины постановки на учёт) позволяют определить каждое обособленное
 * подразделение юридического лица, поэтому часто оба этих кода отображаются и используются вместе,
 * например, при указании платежных реквизитов организаций.
 * <p>
 * ИНН иностранного юридического лица с 1 января 2005 года всегда начинается с цифр «9909»,
 * следующие 5 цифр соответствуют Коду иностранной организации, последняя — контрольная цифра.
 */
public class Inn implements ValueObject<Inn> {

    private String value;

    public Inn(long value) {
        initInn(String.valueOf(value));
    }

    public Inn(String value) {
        Validate.notNull(value, "Null argument is not allowed in constructor");
        initInn(value);
    }

    private void initInn(String value) {
        validate(value);
        this.value = value;
    }

    public String value() {
        return value;
    }

    private static void validate(final String value) {
        final int length = value.length(); //(int) Math.log10(value) + 1;
        final long v = Long.parseUnsignedLong(value);

        switch (length) {
            // Для 12-значного ИНН, присваиваемого физическому лицу, контрольными являются последние две цифры
            case 12 -> {
                final long n11 = calculateN11(v);
                final long n12 = calculateN12(v);
                Validate.isTrue((n11 * 10 + n12) == v % 100, "invalid checksum of 12-digit inn");
            }

            // Для 10-значного ИНН, присваиваемого юридическому лицу, контрольной является последняя, десятая цифра
            case 10 -> {
                long n10 = calculateN10(v);
                Validate.isTrue(n10 == v % 10, "invalid checksum of 10-digit inn");
            }
            default -> throw new IllegalArgumentException("invalid inn length");
        }
    }

    private static final int[] n12factor = {8, 6, 4, 9, 5, 3, 10, 4, 2, 7, 3};

    private static long calculateN10(final long value) {
        return checksum(value / 10, 9);
    }

    private static long calculateN11(final long value) {
        return checksum(value / 100, 10);
    }

    private static long calculateN12(final long value) {
        return checksum(value / 10, 11);
    }

    private static long checksum(final long value, final int c) {
        long t = value;
        long checksum = 0;

        for (int i = 0; i < c; i++) {
            final long digit = t % 10;
            t /= 10;
            checksum += n12factor[i] * digit;
        }

        return (checksum % 11) % 10;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean sameValueAs(Inn inn) {
        return inn != null && Objects.equals(value, inn.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Inn inn = (Inn) o;

        return sameValueAs(inn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    protected Inn() {
        // for Hibernate
    }
}
