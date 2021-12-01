package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Start {@link Command}.
 */
public class StartCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    public static final String START_MESSAGE = "Привіт. Я Monitoring Telegram Bot. Я допоможу тобі слідкувати за станом всіляких " +
            "приколдесіків на сайтах.";

    // DO NOT USE @Autowired to get 'sendBotMessageService' bean from Application Context, to avoid circle dependency.
    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}
