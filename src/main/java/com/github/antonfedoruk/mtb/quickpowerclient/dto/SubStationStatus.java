package com.github.antonfedoruk.mtb.quickpowerclient.dto;

import com.github.antonfedoruk.mtb.utils.Emoji;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SubStationStatus {
    AVAILABLE(Emoji.GREEN_CIRCLE.toString()),
    OCCUPIED(Emoji.BATTERY.toString()),
    ERROR(Emoji.WARNING.toString());

    private final String emojiRepresentation;
}
