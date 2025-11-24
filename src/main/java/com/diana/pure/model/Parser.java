package com.diana.pure.model;

import com.diana.pure.InputParamType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static com.diana.pure.InputParamType.*;

public class Parser {
    private static final DateTimeFormatter FORMATTER_1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter FORMATTER_2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter FORMATTER_3 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATTER_4 = DateTimeFormatter.ofPattern("dd.MM.yy");
    private static final DateTimeFormatter FORMATTER_5 = DateTimeFormatter.ofPattern("dd-MM-yy");
    private static final DateTimeFormatter FORMATTER_6 = DateTimeFormatter.ofPattern("dd/MM/yy");
    private static final List<DateTimeFormatter> FORMATTERS = List.of(FORMATTER_1, FORMATTER_2, FORMATTER_3, FORMATTER_4, FORMATTER_5, FORMATTER_6);
    private static final String MESSAGE_DELIMITER_COMA = ",";
    private static final String DAY_MONTH_DELIMITER_DASH = "-";
    private static final String DAY_MONTH_DELIMITER_DOT = ".";
    private static final String DAY_MONTH_DELIMITER_MIRROR_DOT = "\\.";
    private static final String DAY_MONTH_DELIMITER_SLASH = "/";
    private static final String DAY_MONTH_DELIMITER_MIRROR_SLASH = "\\/";
    private static final int DAY_MONTH_LENGTH = 2;
    private static final int DAY_POSITION = 0;
    private static final int MONTH_POSITION = 1;

    public static Meeting parse(String messageText) {
        var input = messageText.split(MESSAGE_DELIMITER_COMA);
        if (InputParamType.values().length > input.length) {
            throw new IllegalArgumentException("not enough parameters");
        }
        var date = getDate(input[DATE.getPosition()].trim());
        var place = input[PLACE.getPosition()].trim();
        var description = input[DESCRIPTION.getPosition()].trim();
        return new Meeting(date, place, description);
    }

    private static LocalDate getDate(String value) {
        try {
            return parseDayMonthDate(value);
        } catch (Exception e) {
        }
        try {
            return parseFullDate(value);
        } catch (Exception e) {
        }
        try {
            return parseDayDate(value);
        } catch (Exception e) {
        }
        throw new RuntimeException("invalid date");
    }

    private static LocalDate parseDayMonthDate(String value) {
        LocalDate date = LocalDate.now();
        if (value.contains(DAY_MONTH_DELIMITER_DOT)) {
            return getByDayMonthString(date, value, DAY_MONTH_DELIMITER_MIRROR_DOT);
        } else if (value.contains(DAY_MONTH_DELIMITER_DASH)) {
            return getByDayMonthString(date, value, DAY_MONTH_DELIMITER_DASH);
        } else if (value.contains(DAY_MONTH_DELIMITER_SLASH)) {
            return getByDayMonthString(date, value, DAY_MONTH_DELIMITER_MIRROR_SLASH);
        }
        throw new RuntimeException("Invalid date format: " + value);
    }

    private static LocalDate parseFullDate(String value) {
        LocalDate date;
        for (var formatter : FORMATTERS) {
            try {
                date = LocalDate.parse(value, formatter);
                return date;
            } catch (DateTimeParseException e) {
            }
        }
        throw new RuntimeException("Invalid date format: " + value);
    }

    private static LocalDate parseDayDate(String value) {
        LocalDate date = LocalDate.now();
        try {
            var day = Integer.parseInt(value);
            return LocalDate.of(date.getYear(), date.getMonth(), day);
        } catch (Exception e) {
        }
        throw new RuntimeException("Invalid date format: " + value);
    }

    private static LocalDate getByDayMonthString(LocalDate base, String value, String delimiter) {
        var dayMonth = value.split(delimiter);
        if (DAY_MONTH_LENGTH == dayMonth.length) {
            var day = Integer.parseInt(dayMonth[DAY_POSITION]);
            var month = Integer.parseInt(dayMonth[MONTH_POSITION]);
            return LocalDate.of(base.getYear(), month, day);
        }
        throw new RuntimeException("Invalid date format: " + value);
    }
}
