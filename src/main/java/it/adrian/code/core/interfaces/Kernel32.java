package it.adrian.code.core.interfaces;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;
import it.adrian.code.core.structures.MODULEINFO;

public interface Kernel32 extends StdCallLibrary {

    Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class);

    DWORD PROCESS_QUERY_INFORMATION = new DWORD(0x0400);
    DWORD PROCESS_VM_READ = new DWORD(0x0010);

    boolean ReadProcessMemory(WinNT.HANDLE hProcess, Pointer lpBaseAddress, Pointer lpBuffer, int nSize, Pointer lpNumberOfBytesRead);
    boolean WriteProcessMemory(WinNT.HANDLE hProcess, Pointer lpBaseAddress, Pointer lpBuffer, int nSize, Pointer lpNumberOfBytesRead);


    boolean CloseHandle(Pointer pointer);

    WinNT.HANDLE OpenThread(int dwDesiredAccess, boolean bInheritHandle, WinDef.DWORD dwThreadId);

    boolean SetThreadPriority(WinNT.HANDLE hThread, int nPriority);

    WinNT.HANDLE CreateToolhelp32Snapshot(DWORD dwFlags, DWORD th32ProcessID);

    boolean Thread32First(WinNT.HANDLE hSnapshot, Tlhelp32.THREADENTRY32.ByReference lpte);

    boolean Thread32Next(WinNT.HANDLE hSnapshot, Tlhelp32.THREADENTRY32.ByReference lpte);

    Pointer CreateToolhelp32Snapshot(int flags, int pid);

    boolean Module32NextW(Pointer pointer, Tlhelp32.MODULEENTRY32W entry);

    boolean Module32FirstW(Pointer pointer, Tlhelp32.MODULEENTRY32W entry);

    int _wcsicmp(String s1, String s2);

    WinNT.HANDLE CreateToolhelp32Snapshot(int dwFlags, DWORD th32ProcessID);


    boolean OpenProcess(DWORD dwDesiredAccess, boolean bInheritHandle, DWORD dwProcessId, WinNT.HANDLEByReference phProcess);

    boolean GetModuleInformation(WinNT.HANDLE hProcess, WinDef.HMODULE hModule, MODULEINFO lpmodinfo, int cb);

    int GetModuleFileNameEx(WinNT.HANDLE hProcess, WinDef.HMODULE hModule, char[] lpFilename, int nSize);

    boolean Process32NextW(Pointer pointer, Tlhelp32.PROCESSENTRY32 entry);

    WinNT.HANDLE OpenProcess(int fdwAccess, boolean fInherit, int IDProcess);


}