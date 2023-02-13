package com.github.antonfedoruk.mtb.quickpowerclient.dto;

import com.github.antonfedoruk.mtb.utils.Emoji;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubStationStatus {
    AVAILABLE(Emoji.FREE.toString()),
    OCCUPIED(Emoji.ELECTRIC_PLUG.toString()),
    UNAVAILABLE(Emoji.RED_CIRCLE.toString()),
    ERROR(Emoji.WARNING.toString());

    private final String emojiRepresentation;
}
