package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.repository.entity.TelegramUser;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Start {@link Command}.
 */
public class StartCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    public static final String START_MESSAGE = "Привіт. Я Monitoring Telegram Bot. Я допоможу тобі слідкувати за статусом " +
            "зарядних станцій 'QuickPOWER'.";

    // DO NOT USE @Autowired to get 'sendBotMessageService' bean from Application Context, to avoid circle dependency.
    public StartCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        long chatId = CommandUtils.getChatId(update);

        telegramUserService.findByChatId(chatId).ifPresentOrElse(
                user -> {
                    user.setActive(true);
                    telegramUserService.save(user);
                },
                () -> {
                    TelegramUser newUser = new TelegramUser();
                    newUser.setActive(true);
                    newUser.setChatId(chatId);
                    telegramUserService.save(newUser);
                }
        );

        sendBotMessageService.sendMessage(chatId, START_MESSAGE);
    }
}
