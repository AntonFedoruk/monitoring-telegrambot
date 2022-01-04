package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.antonfedoruk.mtb.command.CommandName.*;

/**
 * Help {@link Command}.
 */
public class HelpCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public static String HELP_MESSAGE = String.format("✨<b>Дотупні команди</b>✨\n\n"

                    + "<b>Розпочати\\закінчить роботу з ботом</b>\n"
                    + "%s - розпочать роботу зі мнойю\n"
                    + "%s - призупинити роботу зі мнойю\n"
                    + "%s - отримати допомогу в роботі зі мною\n"
                    + "%s - показати кількість активних користувачів\n",
            START.getCommandName(), STOP.getCommandName(), HELP.getCommandName(), STAT.getCommandName());

    // DO NOT USE @Autowired to get 'sendBotMessageService' bean from Application Context, to avoid circle dependency.
    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        this.sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}