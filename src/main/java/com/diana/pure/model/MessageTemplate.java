package com.diana.pure.model;

import java.time.format.DateTimeFormatter;

public class MessageTemplate {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM");
    private static final String TEMPLATE = """
            Следующая встреча кофейного чата:
            
            📅Когда? %s
            📍Где? %s
            ☕️Что нас ждёт? %s
            🙋‍♀️Модератор встречи:
            ❓Дополнительный вопрос к знакомству:
            
            Кто придёт? Ставьте 👌
            """;

    public static String formate(Meeting meeting) {
        return TEMPLATE.formatted(
                FORMATTER.format(meeting.date()),
                meeting.place(),
                meeting.description()
        );
    }
}
