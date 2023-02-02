package com.github.antonfedoruk.mtb.service;

import com.github.antonfedoruk.mtb.quickpowerclient.dto.Station;
import com.github.antonfedoruk.mtb.repository.StationSubRepository;
import com.github.antonfedoruk.mtb.repository.entity.StationSub;
import com.github.antonfedoruk.mtb.repository.entity.TelegramUser;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

@DisplayName("Unit-level testing for StationSubService")
@FieldDefaults(level = AccessLevel.PRIVATE)
class StationSubServiceTest {
    StationSubService stationSubService;
    StationSubRepository stationSubRepository;
    TelegramUser newUser;

    final static Long CHAT_ID = 1L;

    @BeforeEach
    public void init() {
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        stationSubRepository = Mockito.mock(StationSubRepository.class);
        stationSubService = new StationSubServiceImpl(stationSubRepository, telegramUserService);

        newUser = new TelegramUser();
        newUser.setActive(true);
        newUser.setChatId(CHAT_ID);

        Mockito.when(telegramUserService.findByChatId(CHAT_ID)).thenReturn(Optional.of(newUser));
    }

    @Test
    @DisplayName("Should properly save group")
    void shouldProperlySaveGroup() {
        // given
        Station station = new Station();
        station.setId(1);
        station.setAddress("g1");

        StationSub expectedStationSub = new StationSub();
        expectedStationSub.setId(station.getId());
        expectedStationSub.setTitle(station.getAddress());
        expectedStationSub.addUser(newUser);

        // when
        stationSubService.save(CHAT_ID, station);

        // then
        Mockito.verify(stationSubRepository).save(expectedStationSub);
    }

    @Test
    @DisplayName("Should properly add user to existing group")
    void shouldProperlyAddUserToExistingGroup() {
        // given
        TelegramUser oldTelegramUser = new TelegramUser();
        oldTelegramUser.setChatId(2L);
        oldTelegramUser.setActive(true);

        Station station = new Station();
        station.setId(1);
        station.setAddress("g1");

        StationSub stationFromDB = new StationSub();
        stationFromDB.setId(station.getId());
        stationFromDB.setTitle(station.getAddress());
        stationFromDB.addUser(oldTelegramUser);

        Mockito.when(stationSubRepository.findById(station.getId())).thenReturn(Optional.of(stationFromDB));

        StationSub expectedStationSub = new StationSub();
        expectedStationSub.setId(station.getId());
        expectedStationSub.setTitle(station.getAddress());
        expectedStationSub.addUser(newUser);
        expectedStationSub.addUser(oldTelegramUser);
        // when
        stationSubService.save(CHAT_ID, station);

        // then
        Mockito.verify(stationSubRepository).findById(station.getId());
        Mockito.verify(stationSubRepository).save(expectedStationSub);
    }
}