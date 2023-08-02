package it.adrian.code.system.memory.signatures.scan;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinNT;
import it.adrian.code.core.interfaces.Kernel32;

public class SignatureManager {

    public WinNT.HANDLE pHandle;
    public String processName;
    public int pid;

    public SignatureManager(WinNT.HANDLE pHandle, String processName, int pid) {
        this.pHandle = pHandle;
        this.processName = processName;
        this.pid = pid;
    }

    public long getPtrFromSignature(Pointer baseAddress, byte[] signaturePtr, String signatureMask) {
        try {
            Tlhelp32.MODULEENTRY32W mod = it.adrian.code.system.memory.Memory.getModule(pid, processName);
            long tempPtr = SignatureUtil.findSignature(pHandle, Pointer.nativeValue(baseAddress), mod.modBaseSize.longValue(), signaturePtr, signatureMask);
            if (tempPtr != 0) {
                int value = SignatureUtil.readInt(pHandle, tempPtr + 3);
                long ptr = tempPtr + value + 7;
                return ptr - Pointer.nativeValue(baseAddress);
            } else {
                System.out.println("Signature not found.");
            }
        } finally {
            Kernel32.INSTANCE.CloseHandle(pHandle);
        }
        return 0;
    }
    /*
#define world2screen "\x48\x89\x5C\x24\x00\x55\x56\x57\x48\x83\xEC\x70\x65\x4C\x8B\x0C\x25"
#define world2screenMask "xxxx?xxxxxxxxxxxx"
     */

}