package it.adrian.code.core.structures;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class MODULEENTRY32 extends Structure {
    public static class ByReference extends MODULEENTRY32 implements Structure.ByReference {}
    public static class ByValue extends MODULEENTRY32 implements Structure.ByValue {}

    public int dwSize;
    public int th32ModuleID;
    public int th32ProcessID;
    public int GlblcntUsage;
    public int ProccntUsage;
    public Pointer modBaseAddr;
    public int modBaseSize;
    public int hModule;
    public char[] szModule = new char[256];
    public char[] szExePath = new char[260];

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(
                "dwSize", "th32ModuleID", "th32ProcessID", "GlblcntUsage", "ProccntUsage",
                "modBaseAddr", "modBaseSize", "hModule", "szModule", "szExePath"
        );
    }
}
