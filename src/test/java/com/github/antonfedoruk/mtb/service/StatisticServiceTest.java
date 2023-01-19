package com.github.antonfedoruk.mtb.service;

import com.github.antonfedoruk.mtb.dto.StationStatDTO;
import com.github.antonfedoruk.mtb.dto.StatisticDTO;
import com.github.antonfedoruk.mtb.repository.entity.StationSub;
import com.github.antonfedoruk.mtb.repository.entity.TelegramUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static java.util.Collections.singletonList;

@DisplayName("Unit-level testing for StatisticService")
public class StatisticServiceTest {
    private StationSubService stationSubService;
    private TelegramUserService telegramUserService;

    private StatisticService statisticService;

    @BeforeEach
    public void init() {
        stationSubService = Mockito.mock(StationSubService.class);
        telegramUserService = Mockito.mock(TelegramUserService.class);
        statisticService = new StatisticServiceImpl(stationSubService, telegramUserService);
    }

    @Test
    public void shouldProperlySendStatDTO() {
        //given
        Mockito.when(telegramUserService.findAllInActiveUsers()).thenReturn(singletonList(new TelegramUser()));
        TelegramUser activeUser = new TelegramUser();
        activeUser.setStationSubs(singletonList(new StationSub()));
        Mockito.when(telegramUserService.retrieveAllActiveUsers()).thenReturn(singletonList(activeUser));
        StationSub stationSub = new StationSub();
        stationSub.setId(1);
        stationSub.setTitle("station");
        stationSub.setUsers(singletonList(new TelegramUser()));
        Mockito.when(stationSubService.findAll()).thenReturn(singletonList(stationSub));

        //when
        StatisticDTO statisticDTO = statisticService.countBotStatistic();

        //then
        Assertions.assertNotNull(statisticDTO);
        Assertions.assertEquals(1, statisticDTO.getActiveUserCount());
        Assertions.assertEquals(1, statisticDTO.getInactiveUserCount());
        Assertions.assertEquals(1.0, statisticDTO.getAverageGroupCountByUser());
        Assertions.assertEquals(singletonList(new StationStatDTO(stationSub.getId(),stationSub.getTitle(), stationSub.getUsers().size())),
                statisticDTO.getStationStatDTOs());
    }
}
