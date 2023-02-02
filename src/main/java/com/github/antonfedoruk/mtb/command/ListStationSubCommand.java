package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.repository.entity.StationSub;
import com.github.antonfedoruk.mtb.repository.entity.TelegramUser;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

/**
 * {@link Command} for getting list of {@link StationSub}
 */
public class ListStationSubCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public static final String NEW_USER_ERROR_MESSAGE = "Ой, не можу знайти вас в базі данних. Скористайтесь командою" +
            " /start для початку роботи з ботом, \\n" +
            "та /addstationsub для добавлення станцій на відслідковування.";

    public ListStationSubCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {

        long chatId = CommandUtils.getChatId(update);
//        TelegramUser telegramUser = telegramUserService.findByChatId(getChatId(update))
//                .orElseThrow(NotFoundException::new);

        telegramUserService.findByChatId(chatId).ifPresentOrElse(
                telegramUser -> {
                    String message = "Я знайшов всі підписки на моніторинг станції: \n\n";
                    String collectedStantions = telegramUser.getStationSubs().stream()
                            .map(it -> "Станція: " + it.getTitle() + " , ID = '" + it.getId() + "' \n")
                            .collect(Collectors.joining());

                    sendBotMessageService.sendMessage(telegramUser.getChatId(), message + collectedStantions);
                },
                () -> {
                    TelegramUser newUser = new TelegramUser();
                    newUser.setActive(true);
                    newUser.setChatId(chatId);
                    telegramUserService.save(newUser);

                    sendBotMessageService.sendMessage(chatId, NEW_USER_ERROR_MESSAGE);
                }

        );

//        String message = "Я знайшов всі підписки на моніторинг станції: \n\n";
//        String collectedStantions = telegramUser.getStationSubs().stream()
//                .map(it -> "Станція: " + it.getTitle() + " , ID = '" + it.getId() + "' \n")
//                .collect(Collectors.joining());
//
//        sendBotMessageService.sendMessage(telegramUser.getChatId(), message + collectedStantions);
    }
}
