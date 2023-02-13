package com.github.antonfedoruk.mtb.quickpowerclient.dto;

import com.github.antonfedoruk.mtb.utils.Emoji;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConnectorsStatus {
    CHARGING(Emoji.ELECTRIC_PLUG.toString()),
    AVAILABLE(Emoji.WHITE_CHECK_MARK.toString()),
    UNAVAILABLE(Emoji.RED_CIRCLE.toString()),
    OCCUPIED(Emoji.ELECTRIC_PLUG.toString()),
    UNKNOWN(Emoji.WARNING.toString());

    private final String emojiRepresentation;
}
