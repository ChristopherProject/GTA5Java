package it.adrian.code.core.interfaces;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;

public interface Kernel32 extends StdCallLibrary {

    Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class);

    WinDef.DWORD TH32CS_SNAPMODULE = new WinDef.DWORD(0x00000008);
    WinDef.DWORD TH32CS_SNAPMODULE32 = new WinDef.DWORD(0x00000010);

    boolean ReadProcessMemory(WinNT.HANDLE hProcess, Pointer lpBaseAddress, Pointer lpBuffer, int nSize, Pointer lpNumberOfBytesRead);
    boolean WriteProcessMemory(WinNT.HANDLE hProcess, Pointer lpBaseAddress, Pointer lpBuffer, int nSize, Pointer lpNumberOfBytesRead);

    // Function to create a snapshot of processes or modules
    WinNT.HANDLE CreateToolhelp32Snapshot(WinDef.DWORD dwFlags, WinDef.DWORD th32ProcessID);

    // Function to retrieve information about the next process or module
    boolean Process32NextW(WinNT.HANDLE hSnapshot, Tlhelp32.PROCESSENTRY32.ByReference lppe);

    // Function to retrieve information about the first module
    boolean Module32FirstW(WinNT.HANDLE hSnapshot, Tlhelp32.MODULEENTRY32W lpme);

    // Function to retrieve information about the next module
    boolean Module32NextW(WinNT.HANDLE hSnapshot, Tlhelp32.MODULEENTRY32W lpme);

    // Function to close the handle of a snapshot
    boolean CloseHandle(WinNT.HANDLE hObject);

    WinNT.HANDLE OpenProcess(int fdwAccess, boolean fInherit, int IDProcess);

    int readInt(Pointer address);
}