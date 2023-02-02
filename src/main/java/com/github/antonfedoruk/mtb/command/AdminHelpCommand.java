package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.command.annotation.AdminCommand;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.antonfedoruk.mtb.command.CommandName.STAT;
import static com.github.antonfedoruk.mtb.command.CommandUtils.getChatId;

/**
 * Admin Help {@link Command}.
 */
@AdminCommand
public class AdminHelpCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public static final String ADMIN_HELP_MESSAGE = String.format("✨<b>Доступні команди</b>✨\n\n"
                    + "<b>Показати статистику</b>\n"
                    + "%s - показати кількість активних користувачів\n",
                    STAT.getCommandName());

    public AdminHelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        this.sendBotMessageService.sendMessage(getChatId(update), ADMIN_HELP_MESSAGE);
    }
}