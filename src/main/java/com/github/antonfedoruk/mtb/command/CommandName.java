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
    STAT("/stat"),
    ADD_STATION_SUB("/addstationsub"),
    LIST_STATION_SUB("/liststationsub"),
    DELETE_STATION_SUB("/deletestationsub"),
    ADMIN_HELP("/ahelp"),
    NO("");

    final String commandName;
}