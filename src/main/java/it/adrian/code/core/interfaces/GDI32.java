package it.adrian.code.core.interfaces;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface GDI32 extends StdCallLibrary {
    GDI32 INSTANCE = Native.load("gdi32", GDI32.class, W32APIOptions.DEFAULT_OPTIONS);

    boolean TextOutW(WinDef.HDC hdc, int x, int y, String string, int string_lenght);

    boolean SetBkMode(WinDef.HDC hdc, int mode);

    int SetPixel(WinDef.HDC var1, int var2, int var3, int var4);

    WinDef.HFONT CreateFontA(int cHeight, int cWidth, int cEscapement, int cOrientation, int cWeight, WinDef.DWORD bItalic, WinDef.DWORD bUnderline, WinDef.DWORD bStrikeOut, WinDef.DWORD iCharSet, WinDef.DWORD iOutPrecision, WinDef.DWORD iClipPrecision, WinDef.DWORD iQuality, WinDef.DWORD iPitchAndFamily, String pszFaceName);

    int SetTextSize(WinDef.HDC hdc, int size);
}