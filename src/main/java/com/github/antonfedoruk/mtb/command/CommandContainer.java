package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.quickpowerclient.QuickpowerStationClient;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.service.StationSubService;
import com.github.antonfedoruk.mtb.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static com.github.antonfedoruk.mtb.command.CommandName.*;

/**
 * Container of the {@link Command}s, which are using for handling telegram commands.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandContainer {
    final ImmutableMap<String, Command> commandMap;
    final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                            QuickpowerStationClient quickpowerStationClient, StationSubService stationSubService) {
        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService))
                .put(ADD_STATION_SUB.getCommandName(), new AddStationSubCommand(sendBotMessageService, quickpowerStationClient, stationSubService))
                .put(LIST_STATION_SUB.getCommandName(), new ListStationSubCommand(sendBotMessageService, telegramUserService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
