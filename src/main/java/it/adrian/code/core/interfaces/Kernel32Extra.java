package it.adrian.code.core.interfaces;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface Kernel32Extra extends StdCallLibrary {

    Kernel32Extra INSTANCE = Native.load("kernel32", Kernel32Extra.class);
    // Constants for snapshot flags
    WinDef.DWORD TH32CS_SNAPPROCESS = new WinDef.DWORD(0x00000002);
    WinDef.DWORD TH32CS_SNAPMODULE = new WinDef.DWORD(0x00000008);
    WinDef.DWORD TH32CS_SNAPMODULE32 = new WinDef.DWORD(0x00000010);
    // Constants for MAX_PATH
    int MAX_PATH = 260;

    // Function to create a snapshot of processes or modules
    WinNT.HANDLE CreateToolhelp32Snapshot(WinDef.DWORD dwFlags, WinDef.DWORD th32ProcessID);

    // Function to retrieve information about the first process or module
    boolean Process32FirstW(WinNT.HANDLE hSnapshot, Tlhelp32.PROCESSENTRY32.ByReference lppe);

    // Function to retrieve information about the next process or module
    boolean Process32NextW(WinNT.HANDLE hSnapshot, Tlhelp32.PROCESSENTRY32.ByReference lppe);

    // Function to retrieve information about the first module
    boolean Module32FirstW(WinNT.HANDLE hSnapshot, Tlhelp32.MODULEENTRY32W lpme);

    // Function to retrieve information about the next module
    boolean Module32NextW(WinNT.HANDLE hSnapshot, Tlhelp32.MODULEENTRY32W lpme);

    // Function to close the handle of a snapshot
    boolean CloseHandle(WinNT.HANDLE hObject);

    //memory utils

   // boolean ReadProcessMemory(WinNT.HANDLE hProcess, Pointer lpBaseAddress, Pointer lpBuffer, BaseTSD.SIZE_T nSize, BaseTSD.SIZE_T lpNumberOfBytesRead);

    // Definition of GetModuleInformation function
    boolean GetModuleInformation(WinNT.HANDLE hProcess, WinDef.HMODULE hModule, Psapi.MODULEINFO lpmodinfo, int cb);

    boolean WriteProcessMemory(WinNT.HANDLE hProcess, Pointer lpBaseAddress, Pointer lpBuffer, BaseTSD.SIZE_T nSize, BaseTSD.SIZE_T lpNumberOfBytesWritten);
    boolean ReadProcessMemory(Pointer hProcess, Pointer lpBaseAddress, byte[] lpBuffer, int nSize, IntByReference lpNumberOfBytesRead);

}