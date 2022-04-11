package com.github.antonfedoruk.mtb.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

/**
 * Emoji represents in Telegram.
 */
@AllArgsConstructor
public enum Emoji {
    GREEN_CIRCLE(EmojiParser.parseToUnicode(":green_circle:")),
    RED_CIRCLE(EmojiParser.parseToUnicode(":red_circle:")),
    BATTERY(EmojiParser.parseToUnicode(":battery:")),
    WARNING(EmojiParser.parseToUnicode(":warning:")),
    SOS(EmojiParser.parseToUnicode(":sos:"));

    private String emojiName;

    @Override
    public String toString() {
        return emojiName;
    }
}
