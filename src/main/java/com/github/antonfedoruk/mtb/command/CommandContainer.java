package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.command.annotation.AdminCommand;
import com.github.antonfedoruk.mtb.quickpowerclient.QuickpowerStationClient;
import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import com.github.antonfedoruk.mtb.service.StationSubService;
import com.github.antonfedoruk.mtb.service.StatisticService;
import com.github.antonfedoruk.mtb.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static com.github.antonfedoruk.mtb.command.CommandName.*;
import static java.util.Objects.nonNull;

/**
 * Container of the {@link Command}s, which are using for handling telegram commands.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandContainer {
    final ImmutableMap<String, Command> commandMap;
    final Command unknownCommand;
    final List<String> admins;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                            QuickpowerStationClient quickpowerStationClient, StationSubService stationSubService,
                            StatisticService statisticService,
                            List<String> admins) {
        this.admins = admins;
        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService, statisticService))
                .put(ADD_STATION_SUB.getCommandName(), new AddStationSubCommand(sendBotMessageService, quickpowerStationClient, stationSubService))
                .put(LIST_STATION_SUB.getCommandName(), new ListStationSubCommand(sendBotMessageService, telegramUserService))
                .put(DELETE_STATION_SUB.getCommandName(), new DeleteStationSubCommand(sendBotMessageService, telegramUserService, stationSubService))
                .put(ADMIN_HELP.getCommandName(), new AdminHelpCommand(sendBotMessageService))
                .put(STATUS.getCommandName(), new StatusCommand(sendBotMessageService,quickpowerStationClient))
                .put(ADD_ALL_STATION_SUB.getCommandName(), new AddAllStationsSubCommand(sendBotMessageService,quickpowerStationClient, stationSubService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command findCommand(String commandIdentifier, String username) {
        Command orDefault = commandMap.getOrDefault(commandIdentifier, unknownCommand);
        if (isAdminCommand(orDefault))
            if (admins.contains(username)) {
                return orDefault;
            } else {
                return unknownCommand;
            }
        return orDefault;
    }

    private boolean isAdminCommand(Command command) {
        return nonNull(command.getClass().getAnnotation(AdminCommand.class));
    }
}
