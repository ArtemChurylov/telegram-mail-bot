package com.wsh.bot.service;

import com.wsh.bot.state.User;
import com.wsh.bot.state.UserStateStore;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;

@Service
@RequiredArgsConstructor
public class MailService {

    private static final String MAIL_FROM = "wsh.mail.bot.noreply@gmail.com";
    private final UserStateStore userStateStore;
    private final JavaMailSender javaMailSender;

    public void sendEmail(Long chatId, Chat chat) {
        User user = userStateStore.getUser(chatId);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(MAIL_FROM);
        message.setTo(user.getTargetEmail());
        message.setText(user.getEmailContent());
        message.setSubject(String.format("Mail sent from %s %s", chat.getFirstName(), chat.getLastName()));
        javaMailSender.send(message);
    }
}
