package com.wsh.bot.service;

import java.util.regex.Pattern;

public class EmailValidator {

    private static final String GMAIL_REGEX = "^(.+)@gmail.com";

    public static boolean validateEmailAddress(String emailAddress) {
        return Pattern.compile(GMAIL_REGEX).matcher(emailAddress).matches();
    }
}
