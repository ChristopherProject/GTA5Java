package it.adrian.code.system.utilities;

import java.nio.ByteBuffer;
import java.util.Arrays;

class Pattern {
    byte[] bytePattern;
    boolean[] mask;
    int size;

    Pattern(String pattern) {
        bytePattern = new byte[32];
        mask = new boolean[32];

        Arrays.fill(mask, true);

        int length = pattern.length();
        size = 0;
        for (int i = 0; i < length; i++) {
            char c = pattern.charAt(i);
            if (c == ' ') continue;
            if (c == '?') {
                mask[size++] = false;
                continue;
            }
            bytePattern[size++] =
                    (byte) ((Character.digit(pattern.charAt(i++), 16) << 4) + Character.digit(pattern.charAt(i++), 16));
        }
    }

    int match(ByteBuffer buffer, int toIndex) {
        for (int j = 0; j < (toIndex - size); j++) {
            int i = 0;
            for (; i < size && (!mask[i] || buffer.get(i + j) == bytePattern[i]); i++) ;
            if (i == size) return j;
        }
        return -1;
    }
}
