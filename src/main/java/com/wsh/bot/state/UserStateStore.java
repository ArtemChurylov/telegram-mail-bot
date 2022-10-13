package com.wsh.bot.state;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserStateStore {

    private final Map<Long, User> users = new HashMap<>();

    public void addNewUser(Long chatId, User user) {
        users.put(chatId, user);
    }

    public User getUser(Long chatId) {
        return users.get(chatId);
    }

    public UserState getUserState(Long chatId) {
        User user = users.get(chatId);
        if (user == null) {
            user = User.builder().state(UserState.INITIAL_STATE).build();
            users.put(chatId, user);
        }
        return user.getState();
    }

    public void updateUserState(Long chatId, UserState state) {
        User user = users.get(chatId);
        user.setState(state);
        users.put(chatId, user);
    }

    public void updateEmail(Long chatId, String email) {
        User user = users.get(chatId);
        user.setTargetEmail(email);
        users.put(chatId, user);
    }

    public void updateEmailContent(Long chatId, String emailContent) {
        User user = users.get(chatId);
        user.setEmailContent(emailContent);
        users.put(chatId, user);
    }
}
