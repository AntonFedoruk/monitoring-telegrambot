package com.github.antonfedoruk.mtb.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

/**
 * Emoji represents in Telegram.
 */
@AllArgsConstructor
public enum Emoji {
    WHITE_CHECK_MARK(EmojiParser.parseToUnicode(":white_check_mark:")),
    FREE(EmojiParser.parseToUnicode(":free:")),
    RED_CIRCLE(EmojiParser.parseToUnicode(":red_circle:")),
//    BLUE_CIRCLE(EmojiParser.parseToUnicode(":blue_circle:")), //not displayed
    ELECTRIC_PLUG(EmojiParser.parseToUnicode(":electric_plug:")),
    BATTERY(EmojiParser.parseToUnicode(":battery:")),
    WARNING(EmojiParser.parseToUnicode(":warning:")),
    ZAP(EmojiParser.parseToUnicode(":zap:")),
    SOS(EmojiParser.parseToUnicode(":sos:"));

    private final String emojiName;

    @Override
    public String toString() {
        return emojiName;
    }
}
