package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.quickpowerclient.QuickpowerStationClient;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.service.StationSubService;
import com.github.antonfedoruk.mtb.service.StatisticService;
import com.github.antonfedoruk.mtb.service.TelegramUserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;

@DisplayName("Unit-level testing for CommandContainer")
@FieldDefaults(level = AccessLevel.PRIVATE)
class CommandContainerTest {
    CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        QuickpowerStationClient quickpowerStationClient = Mockito.mock(QuickpowerStationClient.class);
        StationSubService stationSubService = Mockito.mock(StationSubService.class);
        StatisticService statisticService = Mockito.mock(StatisticService.class);
        commandContainer = new CommandContainer(sendBotMessageService,
                telegramUserService,
                quickpowerStationClient,
                stationSubService,
                statisticService,
                Collections.singletonList("admin"));
    }

    @Test
    @DisplayName("should return all the existing commands")
    void shouldReturnAllTheExistingCommands() {
        // when-then
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.findCommand(commandName.getCommandName(), "admin");
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    @Test
    @DisplayName("should retrieve UnknownCommand")
    void shouldRetrieveUnknownCommand() {
        // given
        String unknownCommand = "/asd";
        // when
        Command command = commandContainer.findCommand(unknownCommand, "username");
        // then
        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}