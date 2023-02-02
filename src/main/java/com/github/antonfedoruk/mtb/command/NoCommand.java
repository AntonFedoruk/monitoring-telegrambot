package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.antonfedoruk.mtb.command.CommandUtils.getChatId;

/**
 * No {@link Command}.
 */
public class NoCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    public static String NO_MESSAGE = "Я підтримую команди, які починаються зі слешу(/).\n"
            + "Для перегляду списоку доступних команд скористайтесь /help";

    // DO NOT USE @Autowired to get 'sendBotMessageService' bean from Application Context, to avoid circle dependency.
    public NoCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        this.sendBotMessageService.sendMessage(getChatId(update), NO_MESSAGE);
    }
}
