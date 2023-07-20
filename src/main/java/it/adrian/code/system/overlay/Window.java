package it.adrian.code.system.overlay;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;

public class Window {

    public static void findWindow(String title, Pointer foundWindowPointer) {
        User32.INSTANCE.EnumWindows((hWnd, foundWindowPointer1) -> {
            if (foundWindowPointer1 != null) {
                char[] windowText = new char[512];
                User32.INSTANCE.GetWindowText(hWnd, windowText, 512);
                String wText = Native.toString(windowText);
                if (wText.contains(title)) {
                    foundWindowPointer1.setPointer(0, hWnd.getPointer());
                }
            }
            return true;

        }, foundWindowPointer);
    }
}