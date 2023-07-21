package it.adrian.code;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT;
import it.adrian.code.core.interfaces.Kernel32;
import it.adrian.code.core.interfaces.User32;
import it.adrian.code.system.memory.Memory;
import it.adrian.code.system.memory.signatures.SIGNATURES;
import it.adrian.code.system.memory.signatures.scan.SignatureManager;
import it.adrian.code.system.overlay.GuiInGame;
import it.adrian.code.system.utilities.ProcessUtil;

import java.util.Objects;

/***
 * @author AdrianCode
 * @date 18.07.2023
 */
public class Main {

    private static final String PROCESS_NAME = "GTA5.exe";
    private static final GuiInGame guiInGame = new GuiInGame("Grand Theft Auto V");

    public static void main(String[] args) {
        User32.INSTANCE.MessageBox(null, "Injected!", "NobusWare", User32.MB_OK | User32.MB_ICONINFORMATION);
        int processId = ProcessUtil.getProcessPidByName(PROCESS_NAME);
        if (processId != 0) {
            WinNT.HANDLE pHandle = Kernel32.INSTANCE.OpenProcess(WinNT.PROCESS_ALL_ACCESS, false, processId);
            Pointer baseAddr = Objects.requireNonNull(Memory.getModuleBaseAddress(processId, PROCESS_NAME)).getPointer();
            System.out.println("PID -> " + processId);
            System.out.println("pHandle -> " + "0x" + Long.toHexString(Pointer.nativeValue(pHandle.getPointer())));
            System.out.println("BaseAddress -> " + "0x" + Long.toHexString(Pointer.nativeValue(baseAddr)));
            SignatureManager signatureManager = new SignatureManager(pHandle, "GTA5.exe", processId);

            //this work fine
            long worldPtr = signatureManager.getPtrFromSignature(baseAddr, SIGNATURES.worldPtrSig, SIGNATURES.worldPtrMask);
            System.out.println("worldPtr: 0x" + Long.toHexString(worldPtr));

            //TODO:fix this and implement method to read and write
            //long globalPtr = signatureManager.getPtrFromSignature(null, SIGNATURES.SigGlobalPTR, SIGNATURES.MaskGlobalPTR);
            //System.out.println("globalPtr: 0x" + Long.toHexString(globalPtr));

            guiInGame.init();
            while (processId != 0) {
                guiInGame.renderer();
            }
        }
    }
}