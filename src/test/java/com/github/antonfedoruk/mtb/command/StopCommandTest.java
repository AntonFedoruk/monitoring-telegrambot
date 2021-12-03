package com.github.antonfedoruk.mtb.command;

import org.junit.jupiter.api.DisplayName;

import static com.github.antonfedoruk.mtb.command.CommandName.STOP;
import static com.github.antonfedoruk.mtb.command.StopCommand.STOP_MESSAGE;

@DisplayName("Unit-level testing for StopCommand")
class StopCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return STOP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return STOP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StopCommand(sendBotMessageService, telegramUserService);
    }
}