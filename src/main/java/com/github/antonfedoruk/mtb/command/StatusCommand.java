package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.command.annotation.AdminCommand;
import com.github.antonfedoruk.mtb.quickpowerclient.QuickpowerStationClient;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.utils.Emoji;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

/**
 * Status Command {@link Command}.
 * <p>
 * Show statuses of all stations at the moment.
 */
@AdminCommand
public class StatusCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final QuickpowerStationClient quickpowerStationClient;

    public static final String STATION_STATUSES = "<b>Поточний статус станцій: </b>\n\n %s";

    public StatusCommand(SendBotMessageService sendBotMessageService, QuickpowerStationClient quickpowerStationClient) {
        this.sendBotMessageService = sendBotMessageService;
        this.quickpowerStationClient = quickpowerStationClient;
    }

    @Override
    public void execute(Update update) {
        String stations = quickpowerStationClient.getStationList().stream()
                .map(station -> "Локація: " + station.getStationStatus().getEmojiRepresentation() + " <b>" + station.getName() +"</b> \n"
                        + station.getSubStations().stream()
                            .map(subStation -> Emoji.ZAP + " cтанція: " + subStation.getSubStationStatus().getEmojiRepresentation() +" " + subStation.getName() + ": " +subStation.getConnectors().stream()
                                    .map(connector -> connector.getType().name() + connector.getStatus().getEmojiRepresentation() + " ")
                                    .collect(Collectors.joining(", ")))
                        .collect(Collectors.joining("; \n")))
                .collect(Collectors.joining(" \n\n"));
        sendBotMessageService.sendMessage(CommandUtils.getChatId(update), String.format(STATION_STATUSES, stations));
    }
}