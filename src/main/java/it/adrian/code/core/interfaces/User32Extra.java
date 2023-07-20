package it.adrian.code.core.interfaces;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.W32APIOptions;

public interface User32Extra extends User32 {

    int WM_FONTCHANGE = 0x001D, MB_OK = 0x00000000,  MB_ICONINFORMATION = 0x00000040;

    User32Extra INSTANCE = Native.loadLibrary("user32", User32Extra.class, W32APIOptions.DEFAULT_OPTIONS);

    WinDef.HDC GetWindowDC(WinDef.HWND hWnd);

    boolean GetClientRect(WinDef.HWND hWnd, WinDef.RECT rect);

    int ReleaseDC(HWND hwnd, HDC hdc);

    int MessageBox(WinDef.HWND hWnd, String lpText, String lpCaption, int uType);
}