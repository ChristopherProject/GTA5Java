package it.adrian.code.system.memory.signatures.scan;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinNT;
import it.adrian.code.core.interfaces.Kernel32Extra;

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
            long tempWorldPtr = SignatureUtil.findSignature(pHandle, Pointer.nativeValue(baseAddress), mod.modBaseSize.longValue(), signaturePtr, signatureMask);
            if (tempWorldPtr != 0) {
                int value = SignatureUtil.readInt(pHandle, tempWorldPtr + 3);
                long world = tempWorldPtr + value + 7;
                return world - Pointer.nativeValue(baseAddress);
            } else {
                System.out.println("Signature not found.");
            }
        } finally {
            Kernel32Extra.INSTANCE.CloseHandle(pHandle);
        }
        return 0;
    }
}