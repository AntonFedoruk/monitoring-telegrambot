package com.github.antonfedoruk.mtb.quickpowerclient.dto;

import com.github.antonfedoruk.mtb.utils.Emoji;
import lombok.AllArgsConstructor;

/**
 * Statuses  of station.
 */
@AllArgsConstructor
public enum StationStatus {
    ONLINE(Emoji.GREEN_CIRCLE.toString()),
    OFFLINE(Emoji.RED_CIRCLE.toString()),
    CHARGING(Emoji.BATTERY.toString()),
    ERROR(Emoji.WARNING.toString());

    private String emojiRepresentation;
}
