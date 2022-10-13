package com.wsh.bot.controller;

import com.wsh.bot.service.WshMailBot;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.wsh.bot.controller.RestMapping.BOT_API;

@RequestMapping(BOT_API)
@RestController
@RequiredArgsConstructor
public class BotController {

    private final WshMailBot wshMailBot;

    @PostMapping
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return wshMailBot.onWebhookUpdateReceived(update);
    }
}
