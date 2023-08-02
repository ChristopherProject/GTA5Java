package it.adrian.code.system.memory.signatures.pointer;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;


public class Ptr {

    private static final Kernel32 kernel32 = Kernel32.INSTANCE;

    private final WinNT.HANDLE handle;
    private Pointer baseAddress;
    private long offset;

    public String processName;
    public String moduleName;

    public Ptr(WinNT.HANDLE handle, Pointer baseAddress) {
        this.handle = handle;
        this.baseAddress = baseAddress;
        this.offset = 0L;
    }

    public static Ptr ofProcess(String processName) {
        int pid = ProcessHandle.allProcesses().filter(p -> p.info().command().orElse("").endsWith(processName)).map(p -> p.pid()).findFirst().orElseThrow(() -> new RuntimeException("No process '" + processName + "' found")).intValue();
        int accessRight = 0x0010 | 0x0020 | 0x0008;
        WinNT.HANDLE handle = kernel32.OpenProcess(accessRight, false, pid);
        Pointer baseAddress = getModuleBaseAddress(pid, processName);
        Ptr ptr = new Ptr(handle, baseAddress);
        ptr.processName = processName;
        ptr.moduleName = processName;
        return ptr;
    }

    public static Pointer getModuleBaseAddress(int pid, String moduleName) {
        Pointer baseAddress = null;
        WinNT.HANDLE snapshot = kernel32.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPMODULE, new WinDef.DWORD(pid));
        try {
            Tlhelp32.MODULEENTRY32W module = new Tlhelp32.MODULEENTRY32W();
            if (kernel32.Module32FirstW(snapshot, module)) {
                do {
                    if (moduleName.equals(module.szModule())) {
                        baseAddress = module.modBaseAddr;
                        break;
                    }
                } while (kernel32.Module32NextW(snapshot, module));
            }
        } finally {
            it.adrian.code.core.interfaces.Kernel32.INSTANCE.CloseHandle(snapshot);
        }

        return baseAddress;
    }

    public Ptr add(int val) {
        offset += val;
        return this;
    }

    public long readLong() {
        Memory memory = getMemory(8);
        return memory.getLong(0);
    }

    public float readFloat() {
        Memory memory = getMemory(8);
        return memory.getFloat(0);
    }

    public int readInt() {
        Memory memory = getMemory(4);
        return memory.getInt(0);
    }

    public Memory getMemory(int size) {
        Memory memory = new Memory(size);
        Pointer src = baseAddress.share(offset);
        kernel32.ReadProcessMemory(handle, src, memory, size, null);
        return memory;
    }

    public boolean writeFloat(float value) {
        Memory memory = new Memory(8);
        memory.setFloat(0, value);
        Pointer src = baseAddress.share(offset);
        return kernel32.WriteProcessMemory(handle, src, memory, 8, null);
    }

    public boolean writeLong(long value) {
        Memory memory = new Memory(8);
        memory.setLong(0, value);
        Pointer src = baseAddress.share(offset);
        IntByReference intRef = new IntByReference();
        boolean res = kernel32.WriteProcessMemory(handle, src, memory, 8, intRef);
        return res;
    }

    public boolean writeInt(int value) {
        Memory memory = new Memory(4);
        memory.setInt(0, value);
        Pointer src = baseAddress.share(offset);
        IntByReference intRef = new IntByReference();
        boolean res = kernel32.WriteProcessMemory(handle, src, memory, 4, intRef);
        return res;
    }

    public Ptr copy() {
        Ptr ptr = new Ptr(handle, baseAddress);
        ptr.offset = offset;
        ptr.moduleName = moduleName;
        ptr.processName = processName;
        return ptr;
    }

    public Ptr indirect64() {
        baseAddress = new Pointer(readLong());
        offset = 0;
        return this;
    }

    @Override
    public String toString() {
        return moduleName + "[" + String.format("%#08x", Pointer.nativeValue(baseAddress)) + "]+0x" + Long.toHexString(offset) + " => 0x" + Long.toHexString(Pointer.nativeValue(baseAddress) + offset);
    }
}

