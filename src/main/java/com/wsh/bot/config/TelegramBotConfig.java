package com.wsh.bot.config;

import com.wsh.bot.service.TelegramMessageHandler;
import com.wsh.bot.service.WshMailBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
public class TelegramBotConfig {

    @Bean
    public WshMailBot wshMailBot(TelegramBotProperties properties, TelegramMessageHandler messageHandler) {
        WshMailBot bot = new WshMailBot(SetWebhook.builder().url(properties.getBotPath()).build(), messageHandler);
        bot.setBotToken(properties.getBotToken());
        bot.setBotUsername(properties.getBotUsername());
        bot.setBotPath(properties.getBotPath());
        return bot;
    }
}
