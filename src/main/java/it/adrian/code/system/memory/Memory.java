package it.adrian.code.system.memory;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import it.adrian.code.core.interfaces.Kernel32;
import it.adrian.code.core.interfaces.Kernel32Extra;

public class Memory {

    public static WinDef.HMODULE getModuleBaseAddress(int pid, String moduleName) {
        WinNT.HANDLE snapshotModules = Kernel32.INSTANCE.CreateToolhelp32Snapshot(Kernel32Extra.TH32CS_SNAPMODULE, new WinDef.DWORD(pid));
        WinNT.HANDLE snapshotModules32 = Kernel32.INSTANCE.CreateToolhelp32Snapshot(Kernel32Extra.TH32CS_SNAPMODULE32, new WinDef.DWORD(pid));

        try {
            Tlhelp32.MODULEENTRY32W me32 = new Tlhelp32.MODULEENTRY32W();
            me32.dwSize = new WinDef.DWORD(me32.size());

            if (Kernel32Extra.INSTANCE.Module32FirstW(snapshotModules32, me32)) {
                do {
                    String foundModuleName = Native.toString(me32.szModule).toLowerCase();
                    if (foundModuleName.equals(moduleName.toLowerCase())) {
                        return me32.hModule;
                    }
                } while (Kernel32Extra.INSTANCE.Module32NextW(snapshotModules32, me32));
            }

            if (Kernel32Extra.INSTANCE.Module32FirstW(snapshotModules, me32)) {
                do {
                    String foundModuleName = Native.toString(me32.szModule).toLowerCase();
                    if (foundModuleName.equals(moduleName.toLowerCase())) {
                        return me32.hModule;
                    }
                } while (Kernel32Extra.INSTANCE.Module32NextW(snapshotModules, me32));
            }
        } finally {
            Kernel32Extra.INSTANCE.CloseHandle(snapshotModules);
            Kernel32Extra.INSTANCE.CloseHandle(snapshotModules32);
        }

        return null;
    }

    public static Tlhelp32.MODULEENTRY32W getModule(int pid, String moduleName) {
        WinNT.HANDLE snapshotModules = Kernel32.INSTANCE.CreateToolhelp32Snapshot(Kernel32Extra.TH32CS_SNAPMODULE, new WinDef.DWORD(pid));
        WinNT.HANDLE snapshotModules32 = Kernel32.INSTANCE.CreateToolhelp32Snapshot(Kernel32Extra.TH32CS_SNAPMODULE32, new WinDef.DWORD(pid));

        try {
            Tlhelp32.MODULEENTRY32W me32 = new Tlhelp32.MODULEENTRY32W();
            me32.dwSize = new WinDef.DWORD(me32.size());

            if (Kernel32Extra.INSTANCE.Module32FirstW(snapshotModules32, me32)) {
                do {
                    String foundModuleName = Native.toString(me32.szModule).toLowerCase();
                    if (foundModuleName.equals(moduleName.toLowerCase())) {
                        return me32;
                    }
                } while (Kernel32Extra.INSTANCE.Module32NextW(snapshotModules32, me32));
            }

            if (Kernel32Extra.INSTANCE.Module32FirstW(snapshotModules, me32)) {
                do {
                    String foundModuleName = Native.toString(me32.szModule).toLowerCase();
                    if (foundModuleName.equals(moduleName.toLowerCase())) {
                        return me32;
                    }
                } while (Kernel32Extra.INSTANCE.Module32NextW(snapshotModules, me32));
            }
        } finally {
            Kernel32Extra.INSTANCE.CloseHandle(snapshotModules);
            Kernel32Extra.INSTANCE.CloseHandle(snapshotModules32);
        }

        return null;
    }

    ////////////////MANIPULATIONS/////////////////////

    public static <T> T readMemory(WinNT.HANDLE pHandle, long address, int size) {
        com.sun.jna.Memory memory = new com.sun.jna.Memory(size);
        Pointer ptr = new Pointer(address);
        Kernel32.INSTANCE.ReadProcessMemory(pHandle, ptr, memory, size, null);
        T value = null;
        if (size == 1) {
            value = (T) Byte.valueOf(memory.getByte(0));
        } else if (size == 2) {
            value = (T) Short.valueOf(memory.getShort(0));
        } else if (size == 3) {
            value = (T) Integer.valueOf(memory.getInt(0));
        } else if (size == 4) {
            value = (T) Long.valueOf(memory.getLong(0));
        } else if (size == 5) {
            value = (T) Float.valueOf(memory.getFloat(0));
        } else if (size == 6) {
            value = (T) Double.valueOf(memory.getDouble(0));
        }
        Kernel32.INSTANCE.CloseHandle(pHandle.getPointer());
        return value;
    }
}