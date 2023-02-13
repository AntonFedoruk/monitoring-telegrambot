package com.github.antonfedoruk.mtb.quickpowerclient.dto;

import com.github.antonfedoruk.mtb.utils.Emoji;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Statuses  of station.
 */
@Getter
@AllArgsConstructor
public enum StationStatus {
    ONLINE(Emoji.WHITE_CHECK_MARK.toString()),
    OFFLINE(Emoji.RED_CIRCLE.toString()),
    CHARGING(Emoji.ELECTRIC_PLUG.toString()),
    ERROR(Emoji.WARNING.toString());

    private final String emojiRepresentation;
}
