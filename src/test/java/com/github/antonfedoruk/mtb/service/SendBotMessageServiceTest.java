package com.github.antonfedoruk.mtb.service;

import com.github.antonfedoruk.mtb.bot.MonitoringTelegramBot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotMessageService")
@FieldDefaults(level = AccessLevel.PRIVATE)
class SendBotMessageServiceTest {
    SendBotMessageService sendBotMessageService;
    MonitoringTelegramBot monitoringTelegramBot;

    @BeforeEach
    public void init() {
        monitoringTelegramBot = Mockito.mock(MonitoringTelegramBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(monitoringTelegramBot);
    }

    @Test
    @DisplayName("Should properly send message")
    void shouldProperlySendMessage() throws TelegramApiException {
        // given
        Long chatId = 1L;
        String message = "test_message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableHtml(true);
        // when
        sendBotMessageService.sendMessage(chatId, message);
        // then
        Mockito.verify(monitoringTelegramBot).execute(sendMessage);
    }
}