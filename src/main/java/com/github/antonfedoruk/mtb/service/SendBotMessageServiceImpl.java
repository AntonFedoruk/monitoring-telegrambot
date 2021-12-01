package com.github.antonfedoruk.mtb.service;

import com.github.antonfedoruk.mtb.bot.MonitoringTelegramBot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Implementation of {@link SendBotMessageService} interface.
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendBotMessageServiceImpl implements SendBotMessageService {
    final MonitoringTelegramBot monitoringTelegramBot;

    @Autowired
    public SendBotMessageServiceImpl(MonitoringTelegramBot monitoringTelegramBot) {
        this.monitoringTelegramBot = monitoringTelegramBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            monitoringTelegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            //todo add logging to the project.
            e.printStackTrace();
        }
    }
}