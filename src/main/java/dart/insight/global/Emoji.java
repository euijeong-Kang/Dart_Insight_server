package dart.insight.global;

import java.util.Random;

public class Emoji {

    private static final String[] emojiArray =  {"\uD83E\uDD20", "\uD83D\uDC9D", "\uD83E\uDDD0", "\uD83E\uDD16", "\uD83D\uDC2F", "\uD83E\uDD84", "\uD83D\uDC33", "\uD83E\uDD8A", "\uD83C\uDF83", "\uD83C\uDF97️",
            "\uD83C\uDFB1", "\uD83C\uDFC6", "\uD83D\uDC51", "\uD83E\uDDED", "\uD83E\uDDC1", "\uD83C\uDF69", "\uD83C\uDF41", "☕", "⚡", "⏰", "✈️", "\uD83C\uDF1F", "\uD83D\uDC8E",
            "\uD83C\uDFB5", "\uD83E\uDE99", "\uD83D\uDD2D"};

    public static String getEmoji() {
        int emojiCode;

        Random random = new Random();

        emojiCode = (random.nextInt(emojiArray.length) +1) -1;

        return emojiArray[emojiCode];
    }
}
