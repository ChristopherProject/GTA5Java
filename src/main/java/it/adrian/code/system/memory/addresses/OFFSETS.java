package it.adrian.code.system.memory.addresses;

import com.sun.jna.Pointer;

public class OFFSETS {

    public static final int[] POS_X = new int[]{0x08, 0x30, 0x50};
    public static final int[] POS_Y = new int[]{0x08, 0x30, 0x54};
    public static final int[] POS_Z = new int[]{0x08, 0x30, 0x58};

    public static long calculateAddress(Pointer baseAdress, long PTR, int[] offsets) {
        long address = Pointer.nativeValue(baseAdress) + PTR;
        for (int offset : offsets) {
            address += offset;
        }
        return address;
    }
}