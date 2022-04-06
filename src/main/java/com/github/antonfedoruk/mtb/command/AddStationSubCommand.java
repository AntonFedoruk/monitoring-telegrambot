package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.quickpowerclient.QuickpowerStationClient;
import com.github.antonfedoruk.mtb.quickpowerclient.ScraperService;
import com.github.antonfedoruk.mtb.quickpowerclient.dto.Station;
import com.github.antonfedoruk.mtb.repository.entity.StationSub;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.service.StationSubService;
import org.telegram.telegrambots.meta.api.objects.Update;

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
        String chatId = getChatId(update);
        if (isNumeric(stationId)) {
            Station stationById = quickpowerStationClient.getStationById(Integer.parseInt(stationId));
            if (isNull(stationById.getId())) {
                sendStationNotFound(chatId, stationId);
            }
            StationSub savedStationSub = stationSubService.save(chatId, stationById);
            sendBotMessageService.sendMessage(chatId, "Подписал на мониторинг станции " + savedStationSub.getTitle().toUpperCase());
        } else {
            sendStationNotFound(chatId, stationId);
        }
    }

    private void sendStationNotFound(String chatId, String stationId) {
        String stationNotFoundMessage = "Нет станции с ID = \"%s\"";
        sendBotMessageService.sendMessage(chatId, String.format( stationNotFoundMessage, stationId));
    }

    private void sendStationIdList(String chatId) {
        String stationIds = quickpowerStationClient.getStationList().stream()
                .map(station -> String.format("%s - %s \n", station.getName(), station.getId()))
                .collect(Collectors.joining());

        String message = "Чтобы подписаться на мониторинг станции - передай комадну вместе с ID станции. \n" +
                "Например: /addstationsub 123. \n\n" +
                "я подготовил список всех станций - выберай какую хочешь :) \n\n" +
                "имя станции - ID станции \n\n" +
                "%s";

        sendBotMessageService.sendMessage(chatId, String.format( message, stationIds));
    }
}
