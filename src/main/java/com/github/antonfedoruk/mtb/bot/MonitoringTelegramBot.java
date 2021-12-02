package com.github.antonfedoruk.mtb.bot;

import com.github.antonfedoruk.mtb.command.CommandContainer;
import com.github.antonfedoruk.mtb.service.SendBotMessageServiceImpl;
import com.github.antonfedoruk.mtb.service.TelegramUserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.antonfedoruk.mtb.command.CommandName.NO;

/**
 * Telegram bot for sites monitoring.
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonitoringTelegramBot extends TelegramLongPollingBot {
    public static String COMMAND_PREFIX = "/";

    final CommandContainer commandContainer;

    @Value("${telegrambot.botUserName}")
    String botUserName;

    @Value("${telegrambot.botToken}")
    String botToken;

    @Autowired
    public MonitoringTelegramBot(TelegramUserService telegramUserService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), telegramUserService);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
