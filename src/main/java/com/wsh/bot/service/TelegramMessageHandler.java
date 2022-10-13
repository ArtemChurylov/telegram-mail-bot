package com.wsh.bot.service;

import com.wsh.bot.state.User;
import com.wsh.bot.state.UserState;
import com.wsh.bot.state.UserStateStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.wsh.bot.layout.KeyboardMarkup.INITIAL_KEYBOARD_MARKUP;
import static com.wsh.bot.state.UserState.*;

@Service
@RequiredArgsConstructor
public class TelegramMessageHandler {

    private final UserStateStore userStateStore;
    private final MailService mailService;

    public BotApiMethod<?> handleUpdate(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            if ("/start".equals(message.getText())) {
                return handleStartCommand(message);
            }
            return handleMessage(message);
        }
        return null;
    }

    private SendMessage handleStartCommand(Message message) {
        Long chatId = message.getChatId();
        userStateStore.addNewUser(chatId, User.builder().state(INITIAL_STATE).build());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Hi, " + message.getChat().getFirstName());
        sendMessage.setReplyMarkup(INITIAL_KEYBOARD_MARKUP);
        return sendMessage;
    }

    private SendMessage handleMessage(Message message) {
        Long chatId = message.getChatId();
        UserState userState = userStateStore.getUserState(chatId);
        switch (userState) {
            case INITIAL_STATE -> {
                return handleInitialState(message);
            }
            case ASK_FOR_MAIL_ADDRESS -> {
                return handleMailState(message);
            }
            case ASK_FOR_MAIL_CONTENT -> {
                return handleTextState(message);
            }
            default -> {
                return new SendMessage(message.getChatId().toString(), "Command not recognized");
            }
        }
    }

    private SendMessage handleInitialState(Message message) {
        Long chatId = message.getChatId();
        userStateStore.updateUserState(chatId, ASK_FOR_MAIL_ADDRESS);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Provide address where mail should be sent");
        return sendMessage;
    }

    private SendMessage handleMailState(Message message) {
        Long chatId = message.getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        boolean matches = EmailValidator.validateEmailAddress(message.getText());
        if (!matches) {
            sendMessage.setText("Provided email is wrong, try again");
            return sendMessage;
        }
        userStateStore.updateEmail(chatId, message.getText());
        userStateStore.updateUserState(chatId, ASK_FOR_MAIL_CONTENT);
        sendMessage.setText("Provide mail content");
        return sendMessage;
    }

    private SendMessage handleTextState(Message message) {
        Long chatId = message.getChatId();
        userStateStore.updateEmailContent(chatId, message.getText());
        userStateStore.updateUserState(chatId, INITIAL_STATE);
        mailService.sendEmail(chatId, message.getChat());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Mail was successfully sent");
        sendMessage.setReplyMarkup(INITIAL_KEYBOARD_MARKUP);
        return sendMessage;
    }
}
