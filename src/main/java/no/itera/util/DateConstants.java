package no.itera.util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;

public final class DateConstants {
    private DateConstants(){}

    public static final DateTimeFormatter dateFormatterDeserialization = new DateTimeFormatterBuilder()
            .appendPattern("MM.yyyy")
            .parseDefaulting(DAY_OF_MONTH, 1)
            .toFormatter();

    public static final DateTimeFormatter dateFormatterSerizalisztion = DateTimeFormatter.ofPattern("MM.yyyy");
}
