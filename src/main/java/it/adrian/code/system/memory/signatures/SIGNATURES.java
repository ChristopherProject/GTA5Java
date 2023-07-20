package it.adrian.code.system.memory.signatures;

public class SIGNATURES {

    //WORLD
    public static byte[] worldPtrSig = {(byte) 0x48, (byte) 0x8b, (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x45, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x48, (byte) 0x8b, (byte) 0x48, (byte) 0x08, (byte) 0x48, (byte) 0x85, (byte) 0xc9, (byte) 0x74, (byte) 0x07};
    public static String worldPtrMask = "xxx????x????xxxxxxxxx";

    //GLOBAL
    public static byte[] SigGlobalPTR = {(byte) 0x4C, (byte) 0x8D, (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x4D, (byte) 0x8B, (byte) 0x08, (byte) 0x4D, (byte) 0x85, (byte) 0xC9, (byte) 0x74, (byte) 0x11};
    public static String MaskGlobalPTR = "xxx????xxxxxxxx";

}