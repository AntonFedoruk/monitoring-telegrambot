package com.github.antonfedoruk.mtb.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * Enumeration for {@link Command}'s.
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum CommandName {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    NO("");

    final String commandName;
}