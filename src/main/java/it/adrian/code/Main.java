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
        int processId = ProcessUtil.getProcessPidByName(PROCESS_NAME);
        if (processId != 0) {
            User32.INSTANCE.MessageBox(null, "Injected!", "NobusWare", User32.MB_OK | User32.MB_ICONINFORMATION);
            WinNT.HANDLE pHandle = Kernel32.INSTANCE.OpenProcess(WinNT.PROCESS_ALL_ACCESS, false, processId);
            Pointer baseAddr = Objects.requireNonNull(Memory.getModuleBaseAddress(processId, PROCESS_NAME)).getPointer();
            System.out.println("PID -> " + processId);
            System.out.println("pHandle -> " + "0x" + Long.toHexString(Pointer.nativeValue(pHandle.getPointer())));
            System.out.println("BaseAddress -> " + "0x" + Long.toHexString(Pointer.nativeValue(baseAddr)));
            SignatureManager signatureManager = new SignatureManager(pHandle, "GTA5.exe", processId);

            long worldPtr = signatureManager.getPtrFromSignature(baseAddr, SIGNATURES.worldPtrSig, SIGNATURES.worldPtrMask);
            System.out.println("worldPtr -> 0x" + Long.toHexString(worldPtr));

            System.out.println("=============");
            System.out.println("STATIC ADRESSES:");
            System.out.println("CURRENT_WEAPON_AMMOS -> 0x" + Long.toHexString((Pointer.nativeValue(baseAddr) + 0x1D59BD0)));
            System.out.println("DEFAULT_MONEY_BANK -> 0x" + Long.toHexString((Pointer.nativeValue(baseAddr) + 0x1E01EB8)));
            System.out.println("DEFAULT_MONEY_ONLINE -> 0x" + Long.toHexString((Pointer.nativeValue(baseAddr) + 0x1E01EB0)));
            System.out.println("=============");

            guiInGame.init();
            while (processId != 0) {
                guiInGame.renderer();
            }
        }else{
            User32.INSTANCE.MessageBox(null, "PROCESS TO ATTACH NOT FOUND", "Warining!?!", User32.MB_OK | User32.MB_ICONWARNING);
            System.exit(-1);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            User32.INSTANCE.MessageBox(null, "Uninfected!", "NobusWare", User32.MB_OK | User32.MB_ICONINFORMATION);
            System.runFinalization();
            System.gc();
        }));
    }
}