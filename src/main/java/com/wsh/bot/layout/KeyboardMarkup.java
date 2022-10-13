package com.wsh.bot.layout;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardMarkup {

    public static final ReplyKeyboardMarkup INITIAL_KEYBOARD_MARKUP;

    static {
        INITIAL_KEYBOARD_MARKUP = new ReplyKeyboardMarkup();
        INITIAL_KEYBOARD_MARKUP.setSelective(true);
        INITIAL_KEYBOARD_MARKUP.setResizeKeyboard(true);
        INITIAL_KEYBOARD_MARKUP.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Send mail"));
        keyboard.add(row);
        INITIAL_KEYBOARD_MARKUP.setKeyboard(keyboard);
    }
}
