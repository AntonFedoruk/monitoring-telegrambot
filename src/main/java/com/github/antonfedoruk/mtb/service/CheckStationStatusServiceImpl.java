package com.github.antonfedoruk.mtb.service;

import com.github.antonfedoruk.mtb.quickpowerclient.QuickpowerStationClient;
import com.github.antonfedoruk.mtb.quickpowerclient.dto.Station;
import com.github.antonfedoruk.mtb.repository.entity.StationSub;
import com.github.antonfedoruk.mtb.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckStationStatusServiceImpl implements CheckStationStatusService {
    private final StationSubService stationSubService;
    private final QuickpowerStationClient quickpowerStationClient;
    private final SendBotMessageService sendBotMessageService;

    @Autowired
    public CheckStationStatusServiceImpl(StationSubService stationSubService,
                                         QuickpowerStationClient quickpowerStationClient,
                                         SendBotMessageService sendBotMessageService) {
        this.stationSubService = stationSubService;
        this.quickpowerStationClient = quickpowerStationClient;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void checkStationStatuses() {
        List<Station> stationList = quickpowerStationClient.getStationList();

        stationSubService.findAll().forEach(stationSub -> {
            Station updatedStation = stationList.stream().filter(station -> station.getName().equalsIgnoreCase(stationSub.getTitle())).findFirst().get();

            notifySubscribersAboutNewStatuses(stationSub, updatedStation);

            updateLastStatus(stationSub, updatedStation);
        });
    }

    private void notifySubscribersAboutNewStatuses(StationSub stationSub, Station updatedStation) {
        if (stationSub.getLastStatus() != (updatedStation.getStationStatus())) {
            String messageWithUpdatedStatus = String.format("Оновився статус <b>%s</b> на станції <b>%s</b>.\n\n",
                    updatedStation.getStationStatus(), updatedStation.getName());

            stationSub.getUsers().stream()
                    .filter(TelegramUser::isActive)
                    .forEach(user -> sendBotMessageService.sendMessage(user.getChatId(),messageWithUpdatedStatus));
        }
    }

    public void updateLastStatus(StationSub stationSub, Station updatedStation) {
        stationSub.setLastStatus(updatedStation.getStationStatus());
        stationSubService.save(stationSub);
    }
}