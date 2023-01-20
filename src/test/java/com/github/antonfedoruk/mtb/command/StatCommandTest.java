package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.dto.StationStatDTO;
import com.github.antonfedoruk.mtb.dto.StatisticDTO;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.service.StatisticService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static com.github.antonfedoruk.mtb.command.AbstractCommandTest.prepareUpdate;
import static com.github.antonfedoruk.mtb.command.StatCommand.STAT_MESSAGE;

@DisplayName("Unit-level testing for StatCommand")
@FieldDefaults(level = AccessLevel.PRIVATE)
class StatCommandTest {
    SendBotMessageService sendBotMessageService;
    StatisticService statisticService;
    Command statCommand;

    @BeforeEach
    void init() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        statisticService = Mockito.mock(StatisticService.class);
        statCommand = new StatCommand(sendBotMessageService, statisticService);
    }


    @Test
    @DisplayName("Should  properly send message")
    void shouldProperluSendMessage() {
        //given
        Long chatId=4444L;
        StationStatDTO statDTO = new StationStatDTO(1, "station", 1);
        StatisticDTO statisticDTO = new StatisticDTO(1, 1, Collections.singletonList(statDTO), 2.5);
        Mockito.when(statisticService.countBotStatistic()).thenReturn(statisticDTO);

        //when
        statCommand.execute(prepareUpdate(chatId, CommandName.STAT.getCommandName()));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, String.format(STAT_MESSAGE,
                statisticDTO.getActiveUserCount(),
                statisticDTO.getInactiveUserCount(),
                statisticDTO.getAverageGroupCountByUser(),
                String.format("%s (id = %s) - %s відслідковувань", statDTO.getTitle(), statDTO.getId(), statDTO.getActiveUserCount())));
    }
}