package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.antonfedoruk.mtb.command.CommandUtils.getChatId;

/**
 * Unknown {@link Command}
 */
public class UnknownCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    public static String UNKNOWN_MESSAGE = "Не розумію Вас \uD83D\uDE1F, скористайтесь /help щоб дізнатись можливі команди.";

    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(getChatId(update), UNKNOWN_MESSAGE);
    }
}
