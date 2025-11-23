package com.diana.pure.config;

import com.diana.pure.bot.Bot;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@EnableConfigurationProperties
@Configuration
public class TelegramBotConfiguration {
    private final TelegramAuthProperties telegramAuthProperties;

    public TelegramBotConfiguration(TelegramAuthProperties telegramAuthProperties) {
        this.telegramAuthProperties = telegramAuthProperties;
    }

    @Bean
    public Bot telegramBot() {
        return new Bot(telegramAuthProperties);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        var botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(telegramBot());
        return botsApi;
    }
}
