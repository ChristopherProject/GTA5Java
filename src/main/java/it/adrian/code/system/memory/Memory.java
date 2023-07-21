package it.adrian.code.system.memory;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import it.adrian.code.core.interfaces.Kernel32;

public class Memory {

    public static WinDef.HMODULE getModuleBaseAddress(int pid, String moduleName) {
        WinNT.HANDLE snapshotModules = Kernel32.INSTANCE.CreateToolhelp32Snapshot(Kernel32.TH32CS_SNAPMODULE, new WinDef.DWORD(pid));
        WinNT.HANDLE snapshotModules32 = Kernel32.INSTANCE.CreateToolhelp32Snapshot(Kernel32.TH32CS_SNAPMODULE32, new WinDef.DWORD(pid));

        try {
            Tlhelp32.MODULEENTRY32W me32 = new Tlhelp32.MODULEENTRY32W();
            me32.dwSize = new WinDef.DWORD(me32.size());

            if (Kernel32.INSTANCE.Module32FirstW(snapshotModules32, me32)) {
                do {
                    String foundModuleName = Native.toString(me32.szModule).toLowerCase();
                    if (foundModuleName.equals(moduleName.toLowerCase())) {
                        return me32.hModule;
                    }
                } while (Kernel32.INSTANCE.Module32NextW(snapshotModules32, me32));
            }

            if (Kernel32.INSTANCE.Module32FirstW(snapshotModules, me32)) {
                do {
                    String foundModuleName = Native.toString(me32.szModule).toLowerCase();
                    if (foundModuleName.equals(moduleName.toLowerCase())) {
                        return me32.hModule;
                    }
                } while (Kernel32.INSTANCE.Module32NextW(snapshotModules, me32));
            }
        } finally {
            Kernel32.INSTANCE.CloseHandle(snapshotModules);
            Kernel32.INSTANCE.CloseHandle(snapshotModules32);
        }

        return null;
    }

    public static Tlhelp32.MODULEENTRY32W getModule(int pid, String moduleName) {
        WinNT.HANDLE snapshotModules = Kernel32.INSTANCE.CreateToolhelp32Snapshot(Kernel32.TH32CS_SNAPMODULE, new WinDef.DWORD(pid));
        WinNT.HANDLE snapshotModules32 = Kernel32.INSTANCE.CreateToolhelp32Snapshot(Kernel32.TH32CS_SNAPMODULE32, new WinDef.DWORD(pid));

        try {
            Tlhelp32.MODULEENTRY32W me32 = new Tlhelp32.MODULEENTRY32W();
            me32.dwSize = new WinDef.DWORD(me32.size());

            if (Kernel32.INSTANCE.Module32FirstW(snapshotModules32, me32)) {
                do {
                    String foundModuleName = Native.toString(me32.szModule).toLowerCase();
                    if (foundModuleName.equals(moduleName.toLowerCase())) {
                        return me32;
                    }
                } while (Kernel32.INSTANCE.Module32NextW(snapshotModules32, me32));
            }

            if (Kernel32.INSTANCE.Module32FirstW(snapshotModules, me32)) {
                do {
                    String foundModuleName = Native.toString(me32.szModule).toLowerCase();
                    if (foundModuleName.equals(moduleName.toLowerCase())) {
                        return me32;
                    }
                } while (Kernel32.INSTANCE.Module32NextW(snapshotModules, me32));
            }
        } finally {
            Kernel32.INSTANCE.CloseHandle(snapshotModules);
            Kernel32.INSTANCE.CloseHandle(snapshotModules32);
        }

        return null;
    }

    ////////////////MANIPULATIONS/////////////////////

    //method to write and read

}