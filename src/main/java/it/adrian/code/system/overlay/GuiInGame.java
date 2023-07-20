package it.adrian.code.system.overlay;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import it.adrian.code.core.interfaces.GDI32;
import it.adrian.code.core.interfaces.User32Extra;

public class GuiInGame {

    private static String pName;
    private static WinDef.HWND hwnd = null;
    private static WinDef.HDC hdc = null;

    public GuiInGame(String pName) {
        GuiInGame.pName = pName;
    }

    public void init() {
        Pointer foundWindowPointer = new Memory(16);
        Window.findWindow(pName, foundWindowPointer);
        if (foundWindowPointer.getPointer(0) != null) {
            hwnd = new WinDef.HWND(foundWindowPointer.getPointer(0));
            hdc = User32.INSTANCE.GetDC(hwnd);
        } else {
            hwnd = null;
            hdc = null;
        }
    }

    public void renderer() {
        GDI32.INSTANCE.SetBkMode(hdc, 0);//mette il background del testo trasparente
        GDI32.INSTANCE.TextOutW(hdc, 25, 70 - 57, "AdrianWare 1.0.1 (Edition Summer)", "AdrianWare 1.0.1 (Edition Summer)".length());//disegna il testo nella finestra
        GDI32.INSTANCE.TextOutW(hdc, 25, 82 - 57, "Created By AdrianCode", "Created By AdrianCode".length());//disegna il testo nella finestra
        User32Extra.INSTANCE.ReleaseDC(hwnd, User32.INSTANCE.GetDC(hwnd));//rilascia l'hdc della finestra
    }
}
