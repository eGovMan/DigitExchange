package org.digit.exchange.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, String> {
    @Override
    public String convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        return zonedDateTime.format(formatter);
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(String dbData) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        return ZonedDateTime.parse(dbData, formatter);
    }
}