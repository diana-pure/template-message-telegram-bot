package com.diana.pure.logic;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static java.util.Objects.isNull;

public class PrivateChatProcessor {
    public static SendMessage onUpdateReceived(Update update) {
        long id;
        String text = "unsupported action";
        if (isNull(update.getMessage())) {
            System.out.println("Unsupported action received: " + text);
            return null;
        }
        var msg = update.getMessage();
        var user = msg.getFrom();
        id = user.getId();
        text = msg.getText();
        System.out.println(user.getFirstName() + " wrote " + text);
        System.out.println(msg.isCommand());
        return sendText(id, msg.getText());
    }

    public static SendMessage sendText(Long who, String what) {
        return SendMessage
                .builder()
                .chatId(who.toString())
                .text(what)
                .build();
    }
}
