package com.github.antonfedoruk.mtb.command;

import com.github.antonfedoruk.mtb.service.SendBotMessageService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

@DisplayName("Unit-level testing for CommandContainer")
@FieldDefaults(level = AccessLevel.PRIVATE)
class CommandContainerTest {
    CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        commandContainer = new CommandContainer(sendBotMessageService);
    }

    @Test
    @DisplayName("should return all the existing commands")
    void shouldReturnAllTheExistingCommands() {
        // when-then
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName());
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    @Test
    @DisplayName("should retrieve UnknownCommand")
    void shouldRetrieveUnknownCommand() {
        // given
        String unknownCommand = "/asd";
        // when
        Command command = commandContainer.retrieveCommand(unknownCommand);
        // then
        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}