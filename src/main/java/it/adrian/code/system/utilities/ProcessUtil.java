package it.adrian.code.system.utilities;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Tlhelp32;
import it.adrian.code.core.interfaces.Kernel32;

public class ProcessUtil {

    public static int getProcessPidByName(String pName) {
        Tlhelp32.PROCESSENTRY32.ByReference entry = new Tlhelp32.PROCESSENTRY32.ByReference();
        Pointer snapshot = Kernel32.INSTANCE.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPALL.intValue(), 0);
        try {
            while (Kernel32.INSTANCE.Process32NextW(snapshot, entry)) {
                String processName = Native.toString(entry.szExeFile);
                if (pName.equals(processName)) {
                    return entry.th32ProcessID.intValue();
                }
            }
        } finally {
            Kernel32.INSTANCE.CloseHandle(snapshot);
        }
        return 0;
    }
}