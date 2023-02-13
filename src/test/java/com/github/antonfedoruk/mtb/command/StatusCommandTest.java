package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.quickpowerclient.QuickpowerStationClient;
import com.github.antonfedoruk.mtb.quickpowerclient.dto.*;
import com.github.antonfedoruk.mtb.repository.entity.TelegramUser;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.utils.Emoji;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.antonfedoruk.mtb.command.CommandName.STATUS;

@DisplayName("Unit-level testing for StatusCommand")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusCommandTest {
    SendBotMessageService sendBotMessageService;
    QuickpowerStationClient quickpowerStationClient;
    Command statusCommand;

    @BeforeEach
    void init() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        quickpowerStationClient = Mockito.mock(QuickpowerStationClient.class);
        statusCommand = new StatusCommand(sendBotMessageService, quickpowerStationClient);
    }

    @Test
    @DisplayName("Should  properly display status")
    void shouldProperluDisplayStatus() {
        // given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(1L);
        telegramUser.setActive(true);

        List<Station> stations = new ArrayList<>();

        Station station1 = new Station();
        station1.setId(1);
        station1.setName("ST1");
        station1.setAddress("addr1");
        station1.setStationStatus(StationStatus.ONLINE);
        List<SubStation> subStations1 = new ArrayList<>();
        SubStation subStation1 = new SubStation();
        subStation1.setName("sub1");
        subStation1.setModel(StationModel.PT3EVOLVE);
        subStation1.setFirmware(StationFirmware.F_24);
        subStation1.setSubStationStatus(SubStationStatus.AVAILABLE);
        List<Connector> connectors1 = new ArrayList<>();
        Connector connector1 = new Connector(ConnectorsType.TYPE2, ConnectorsStatus.AVAILABLE);
        Connector connector2 = new Connector(ConnectorsType.CHADEMO, ConnectorsStatus.AVAILABLE);
        Connector connector3 = new Connector(ConnectorsType.COMBO, ConnectorsStatus.OCCUPIED);
        connectors1.add(connector1);
        connectors1.add(connector2);
        subStation1.setConnectors(connectors1);
        station1.setSubStations(subStations1);
        Station station2 = new Station();
        station2.setId(2);
        station2.setName("ST2");
        station2.setAddress("addr2");
        station2.setStationStatus(StationStatus.CHARGING);
        List<SubStation> subStations2 = new ArrayList<>();
        SubStation subStation2 = new SubStation();
        subStation2.setName("sub2");
        subStation2.setModel(StationModel.RAPTION50TRIO);
        subStation2.setFirmware(StationFirmware.F_24);
        subStation2.setSubStationStatus(SubStationStatus.OCCUPIED);
        List<Connector> connectors2 = new ArrayList<>();
        connectors1.add(connector3);
        subStation2.setConnectors(connectors2);
        station2.setSubStations(subStations2);

        stations.add(station1);
        stations.add(station2);

        Mockito.when(quickpowerStationClient.getStationList()).thenReturn(stations);

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(telegramUser.getChatId());
        Mockito.when(message.getText()).thenReturn(STATUS.getCommandName());
        update.setMessage(message);

        String STATION_STATUSES = "<b>Поточний статус станцій: </b>\n\n %s";
        String expectedStations = quickpowerStationClient.getStationList().stream()
                .map(station -> "Локація: " + station.getStationStatus().getEmojiRepresentation() + " <b>" + station.getName() + "</b> \n"
                        + station.getSubStations().stream()
                        .map(subStation -> Emoji.ZAP + " cтанція: " + subStation.getSubStationStatus().getEmojiRepresentation() + " " + subStation.getName() + ": " + subStation.getConnectors().stream()
                                .map(connector -> connector.getType().name() + connector.getStatus().getEmojiRepresentation() + " ")
                                .collect(Collectors.joining(", ")))
                        .collect(Collectors.joining("; \n")))
                .collect(Collectors.joining(" \n\n"));
        // when
        statusCommand.execute(update);
        // then
        Mockito.verify(sendBotMessageService).sendMessage(telegramUser.getChatId(), String.format(STATION_STATUSES, expectedStations));
    }
}
