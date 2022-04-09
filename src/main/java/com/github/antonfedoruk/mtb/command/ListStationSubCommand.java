package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.repository.entity.StationSub;
import com.github.antonfedoruk.mtb.repository.entity.TelegramUser;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;

import java.util.stream.Collectors;

import static com.github.antonfedoruk.mtb.command.CommandUtils.getChatId;
import static com.github.antonfedoruk.mtb.command.CommandUtils.getMessage;

/**
 * {@link Command} for getting list of {@link StationSub}
 */
public class ListStationSubCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public ListStationSubCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }


    @Override
    public void execute(Update update) {
        //todo add exception handling
        TelegramUser telegramUser = telegramUserService.findByChatId(getChatId(update))
                .orElseThrow(NotFoundException::new);

        String message = "Я знайшов всі підписки на моніторинг станції: \n\n";
        String collectedStantions = telegramUser.getStationSubs().stream()
                .map(it -> "Станція: " + it.getTitle() + " , ID = '" + it.getId() + "' \n")
                .collect(Collectors.joining());

        sendBotMessageService.sendMessage(telegramUser.getChatId(), message + collectedStantions);
    }
}
