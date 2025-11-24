package com.diana.pure.logic;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import static java.util.Objects.nonNull;

public class UpdateProcessor {

    public static BotApiMethod onUpdateReceived(Update update) {
        if (nonNull(update.getInlineQuery())) {
            return InlineProcessor.onUpdateReceived(update);
        }
        if (nonNull(update.getMessage())) {
            return PrivateChatProcessor.onUpdateReceived(update);
        }
        return null;
    }
}
