package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.quickpowerclient.QuickpowerStationClient;
import com.github.antonfedoruk.mtb.quickpowerclient.dto.Station;
import com.github.antonfedoruk.mtb.repository.entity.StationSub;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.service.StationSubService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.github.antonfedoruk.mtb.command.CommandName.ADD_STATION_SUB;
import static com.github.antonfedoruk.mtb.command.CommandUtils.getChatId;
import static com.github.antonfedoruk.mtb.command.CommandUtils.getMessage;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Add Station subscription {@link Command}
 */
public class AddStationSubCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final QuickpowerStationClient quickpowerStationClient;
    private final StationSubService stationSubService;

    public AddStationSubCommand(SendBotMessageService sendBotMessageService, QuickpowerStationClient quickpowerStationClient, StationSubService stationSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.quickpowerStationClient = quickpowerStationClient;
        this.stationSubService = stationSubService;
    }


    @Override
    public void execute(Update update) {
        if (getMessage(update).equalsIgnoreCase(ADD_STATION_SUB.getCommandName())) {
            sendStationIdList(getChatId(update));
            return;
        }
        String stationId = getMessage(update).split(SPACE)[1];
        Long chatId = getChatId(update);
        if (isNumeric(stationId)) {
            try {
                Station stationById = quickpowerStationClient.getStationByIdOrThrowNoSuchElementException(Integer.parseInt(stationId));

            System.out.println("Trying to subscribe on " + stationById.getName());
            if (isNull(stationById.getId())) {
                sendStationNotFound(chatId, stationId);
            }
            StationSub savedStationSub = stationSubService.save(chatId, stationById);
            sendBotMessageService.sendMessage(chatId, "Підписано на моніторинг станції " + savedStationSub.getTitle().toUpperCase());
            } catch (NoSuchElementException e) {
                sendBotMessageService.sendMessage(chatId, e.getMessage());
            }
        } else {
            sendStationNotFound(chatId, stationId);
        }
    }

    private void sendStationNotFound(Long chatId, String stationId) {
        String stationNotFoundMessage = "Відсутня станція з ID = \"%s\"";
        sendBotMessageService.sendMessage(chatId, String.format( stationNotFoundMessage, stationId));
    }

    private void sendStationIdList(Long chatId) {
        String stationIds = quickpowerStationClient.getStationList().stream()
                .map(station -> String.format("%s - '%s' \n", station.getName(), station.getId()))
                .collect(Collectors.joining());

        String message = "Щоб підписатись на моніторинг станції - передайте команду разом з ID станції. \n" +
                "Приклад: /addstationsub 123. \n\n" +
                "Ось список всіх доступних станцій:) \n\n" +
                "'назва станції' - 'ID_станції' \n\n" +
                "%s";

        sendBotMessageService.sendMessage(chatId, String.format( message, stationIds));
    }
}
