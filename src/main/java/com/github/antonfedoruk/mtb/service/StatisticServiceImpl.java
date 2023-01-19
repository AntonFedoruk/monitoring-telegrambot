package com.github.antonfedoruk.mtb.service;

import com.github.antonfedoruk.mtb.dto.StationStatDTO;
import com.github.antonfedoruk.mtb.dto.StatisticDTO;
import com.github.antonfedoruk.mtb.repository.entity.TelegramUser;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatisticServiceImpl implements StatisticService {
    final StationSubService stationSubService;
    final TelegramUserService telegramUserService;

    public StatisticServiceImpl(StationSubService stationSubService, TelegramUserService telegramUserService) {
        this.stationSubService = stationSubService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public StatisticDTO countBotStatistic() {
        List<StationStatDTO> stationStatDTOs = stationSubService.findAll().stream()
                .filter(stationSub -> !isEmpty(stationSub.getUsers()))
                .map(stationSub -> new StationStatDTO(stationSub.getId(), stationSub.getTitle(),stationSub.getUsers().size()))
                .collect(Collectors.toList());
        List<TelegramUser> allInActiveUsers = telegramUserService.findAllInActiveUsers();
        List<TelegramUser> allActiveUsers = telegramUserService.retrieveAllActiveUsers();

        double stationsPerUser = getStationPerUser(allActiveUsers);
        return new StatisticDTO(allActiveUsers.size(), allInActiveUsers.size(), stationStatDTOs, stationsPerUser);
    }

    private double getStationPerUser(List<TelegramUser> allActiveUsers) {
        return (double) allActiveUsers.stream().mapToInt(it -> it.getStationSubs().size()).sum() / allActiveUsers.size();
    }
}