package com.github.antonfedoruk.mtb.command;

import static com.github.antonfedoruk.mtb.command.CommandName.STAT;
import static com.github.antonfedoruk.mtb.command.StatCommand.STAT_MESSAGE;

class StatCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return STAT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return String.format(STAT_MESSAGE, 0);
    }

    @Override
    Command getCommand() {
        return new StatCommand(sendBotMessageService, telegramUserService);
    }
}