package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.quickpowerclient.QuickpowerStationClient;
import com.github.antonfedoruk.mtb.quickpowerclient.dto.Station;
import com.github.antonfedoruk.mtb.repository.entity.StationSub;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.service.StationSubService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.antonfedoruk.mtb.command.CommandName.ADD_ALL_STATION_SUB;
import static com.github.antonfedoruk.mtb.command.CommandUtils.getChatId;
import static com.github.antonfedoruk.mtb.command.CommandUtils.getMessage;
import static org.apache.commons.lang3.StringUtils.SPACE;

/**
 * Add all Stations subscription {@link Command}
 */
public class AddAllStationsSubCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final QuickpowerStationClient quickpowerStationClient;
    private final StationSubService stationSubService;

    public AddAllStationsSubCommand(SendBotMessageService sendBotMessageService, QuickpowerStationClient quickpowerStationClient, StationSubService stationSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.quickpowerStationClient = quickpowerStationClient;
        this.stationSubService = stationSubService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = getChatId(update);
        List<String> subscribedStations = new ArrayList<>();
        quickpowerStationClient.getStationList().forEach(station -> {
            StationSub savedStationSub = stationSubService.save(chatId, station);
            subscribedStations.add("'" + savedStationSub.getTitle().toUpperCase() + "'");
        });

        System.out.println("subscribedStations: " + String.join(", ", subscribedStations));
        sendBotMessageService.sendMessage(chatId, "Підписано на моніторинг всіх доступних станції: <b>" + String.join(", ", subscribedStations) + "</b>.");
    }
}