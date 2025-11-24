package com.diana.pure.bot;

import com.diana.pure.config.TelegramAuthProperties;
import com.diana.pure.logic.UpdateProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {

    private final TelegramAuthProperties telegramAuthProperties;

    public Bot(TelegramAuthProperties telegramAuthProperties) {
        this.telegramAuthProperties = telegramAuthProperties;
    }

    @Override
    public String getBotUsername() {
        return "Template Message Bot";
    }

    @Override
    public String getBotToken() {
        return telegramAuthProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            execute(UpdateProcessor.onUpdateReceived(update));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
