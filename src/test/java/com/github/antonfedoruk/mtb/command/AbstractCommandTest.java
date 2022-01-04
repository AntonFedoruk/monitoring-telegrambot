package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.bot.MonitoringTelegramBot;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.service.SendBotMessageServiceImpl;
import com.github.antonfedoruk.mtb.service.TelegramUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class AbstractCommandTest {
    protected MonitoringTelegramBot monitoringTelegramBot = Mockito.mock(MonitoringTelegramBot.class);
    protected TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(monitoringTelegramBot);

    abstract String getCommandName();

    abstract String getCommandMessage();

    abstract Command getCommand();

    @Test
    @DisplayName("should properly execute command")
    void shouldProperlyExecuteCommand() throws TelegramApiException {
        // given
        Long chatId = 228L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);
        // when
        getCommand().execute(update);
        // then
        Mockito.verify(monitoringTelegramBot).execute(sendMessage);
    }
}