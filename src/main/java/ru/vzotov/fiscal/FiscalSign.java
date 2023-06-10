package ru.vzotov.fiscal;

import org.apache.commons.lang3.Validate;
import ru.vzotov.ddd.shared.ValueObject;

import java.util.Objects;

/**
 * <p>
 * Фискальный признак - достоверная информация, сформированная с использованием фискального накопителя
 * и ключа фискального признака или с использованием средств формирования фискального признака и мастер-ключа
 * в результате криптографического преобразования фискальных данных, наличие которой дает возможность выявления
 * корректировки или фальсификации этих фискальных данных при их проверке с использованием фискального накопителя
 * и (или) средства проверки фискального признака;
 * </p>
 *
 * <p>
 * Фискальный признак документа генерируется не кассой, а фискальным накопителем (ФП), на основании введенных данных:
 * <ul>
 *      <li>ИНН пользователя</li>
 *      <li>дата, время регистрации</li>
 *      <li>регистрационный номер ККТ</li>
 *      <li>порядковый номер фискального документа</li>
 *      <li>заводской номер ККТ</li>
 *      <li>ИНН ОФД</li>
 * </ul>
 * </p>
 * В массиве из 6 байт на печать выводятся байты 2-5, которые интерпретируются, как UInt32, big endian
 */
public class FiscalSign implements ValueObject<FiscalSign> {

    private long value;

    public FiscalSign(long value) {
        this.value = value;
    }

    public FiscalSign(String stringValue) {
        Validate.notNull(stringValue);
        this.value = Long.parseUnsignedLong(stringValue);
    }

    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean sameValueAs(FiscalSign that) {
        return that != null && value == that.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final FiscalSign that = (FiscalSign) o;

        return sameValueAs(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    protected FiscalSign() {
        // for hibernate
    }
}
