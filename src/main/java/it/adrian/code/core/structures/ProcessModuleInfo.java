package it.adrian.code.core.structures;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;

import java.util.Arrays;
import java.util.List;

public class ProcessModuleInfo extends Structure {

    public static class ByReference extends ProcessModuleInfo implements Structure.ByReference {}
    public WinDef.DWORD dwSize;
    public WinDef.DWORD th32ModuleID;
    public WinDef.DWORD th32ProcessID;
    public WinDef.DWORD GlblcntUsage;
    public WinDef.DWORD ProccntUsage;
    public Pointer modBaseAddr;
    public WinDef.DWORD modBaseSize;
    public WinDef.HMODULE hModule;
    public char[] szModule = new char[WinDef.MAX_PATH];
    public char[] szExePath = new char[WinDef.MAX_PATH];

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("dwSize", "th32ModuleID", "th32ProcessID", "GlblcntUsage",
                "ProccntUsage", "modBaseAddr", "modBaseSize", "hModule", "szModule", "szExePath");
    }
}