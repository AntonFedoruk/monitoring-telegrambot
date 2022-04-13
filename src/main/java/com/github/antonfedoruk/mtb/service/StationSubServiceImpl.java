package com.github.antonfedoruk.mtb.service;

import com.github.antonfedoruk.mtb.quickpowerclient.dto.Station;
import com.github.antonfedoruk.mtb.repository.StationSubRepository;
import com.github.antonfedoruk.mtb.repository.entity.StationSub;
import com.github.antonfedoruk.mtb.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
public class StationSubServiceImpl implements StationSubService {
    private final StationSubRepository stationSubRepository;
    private final TelegramUserService telegramUserService;

    @Autowired
    public StationSubServiceImpl(StationSubRepository stationSubRepository, TelegramUserService telegramUserService) {
        this.stationSubRepository = stationSubRepository;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public StationSub save(String chatId, Station station) {
        TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
        //TODO add exception handling
        StationSub stationSub;
        Optional<StationSub> stationSubFromDB = stationSubRepository.findById(station.getId());
        if (stationSubFromDB.isPresent()) {
            stationSub = stationSubFromDB.get();
            Optional<TelegramUser> first = stationSub.getUsers().stream()
                    .filter(it -> it.getChatId().equalsIgnoreCase(chatId))
                    .findFirst();
            if (first.isEmpty()) {
                stationSub.addUser(telegramUser);
            }
        } else {
            stationSub = new StationSub();
            stationSub.addUser(telegramUser);
            stationSub.setId(station.getId());
            stationSub.setTitle(station.getName());
        }
        return stationSubRepository.save(stationSub);
    }

    @Override
    public StationSub save(StationSub stationSub) {
        return stationSubRepository.save(stationSub);
    }

    @Override
    public Optional<StationSub> findById(Integer id) {
        return stationSubRepository.findById(id);
    }
}