package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.repository.entity.StationSub;
import com.github.antonfedoruk.mtb.repository.entity.TelegramUser;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.service.StationSubService;
import com.github.antonfedoruk.mtb.service.TelegramUserService;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.antonfedoruk.mtb.command.CommandName.DELETE_STATION_SUB;
import static com.github.antonfedoruk.mtb.command.CommandUtils.getChatId;
import static com.github.antonfedoruk.mtb.command.CommandUtils.getMessage;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Delete Station subscription {@link Command}
 */
public class DeleteStationSubCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final StationSubService stationSubService;

    public DeleteStationSubCommand(SendBotMessageService sendBotMessageService,
                                   TelegramUserService telegramUserService,
                                   StationSubService stationSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.stationSubService = stationSubService;
    }

    @Override
    public void execute(Update update) {
        if (getMessage(update).equalsIgnoreCase(DELETE_STATION_SUB.getCommandName())) {
            sendStationIdList(getChatId(update));
            return;
        }
        String stationId = getMessage(update).split(SPACE)[1];
        String chatId = getChatId(update);
        if (isNumeric(stationId)) {
            Optional<StationSub> optionalStationSub = stationSubService.findById(Integer.valueOf(stationId));
            if (optionalStationSub.isPresent()) {
                StationSub stationSub = optionalStationSub.get();
                TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
                stationSub.getUsers().remove(telegramUser);
                stationSubService.save(stationSub);
                sendBotMessageService.sendMessage(chatId, String.format("Скасовано підписку на станцію: %s", stationSub.getTitle()));
            } else {
                sendBotMessageService.sendMessage(chatId, "Вказана станція не знайдена =/");
            }
        } else {
            sendBotMessageService.sendMessage(chatId, "Невірний формат ID станції.\n" +
                    "ID має бути цілим додатнім числом");
        }
    }

    private void sendStationIdList(String chatId) {
        String message;
        List<StationSub> stationSubs = telegramUserService.findByChatId(chatId)
                .orElseThrow(NotFoundException::new)
                .getStationSubs();
        if (CollectionUtils.isEmpty(stationSubs)) {
            message = "Поки відсутній моніторинг станцій. Щоб почати відслідковування станцій скористайся /addstationsub";
        } else {
            String userStationSubData = stationSubs.stream()
                    .map(group -> String.format("%s - %s \n", group.getTitle(), group.getId()))
                    .collect(Collectors.joining());

            message = String.format("Для видалення моніторингу станції - передай комадну разом з ID станції.\n" +
                    "Наприклад: /deletestationsub 777 \n\n" +
                    "Ось список всіх доступних станцій:) \n\n" +
                    "'назва станції' - 'ID_станції' \n\n" +
                    "%s", userStationSubData);
        }

        sendBotMessageService.sendMessage(chatId, message);
    }
}