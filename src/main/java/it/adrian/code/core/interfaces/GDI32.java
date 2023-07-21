package it.adrian.code.core.interfaces;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface GDI32 extends StdCallLibrary {
    GDI32 INSTANCE = Native.load("gdi32", GDI32.class, W32APIOptions.DEFAULT_OPTIONS);

    boolean TextOutW(WinDef.HDC hdc, int x, int y, String string, int string_lenght);

    boolean SetBkMode(WinDef.HDC hdc, int mode);
}