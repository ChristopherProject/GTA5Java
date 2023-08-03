package it.adrian.code.system.memory;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import it.adrian.code.core.interfaces.Kernel32;
import it.adrian.code.system.memory.signatures.pointer.Ptr;
import it.adrian.code.system.utilities.JOAAT;

public class Memory {

    public static WinDef.HMODULE getModuleBaseAddress(int pid, String moduleName) {
        WinNT.HANDLE snapshotModules = Kernel32.INSTANCE.CreateToolhelp32Snapshot(Kernel32.TH32CS_SNAPMODULE, new WinDef.DWORD(pid));
        WinNT.HANDLE snapshotModules32 = Kernel32.INSTANCE.CreateToolhelp32Snapshot(Kernel32.TH32CS_SNAPMODULE32, new WinDef.DWORD(pid));

        try {
            Tlhelp32.MODULEENTRY32W me32 = new Tlhelp32.MODULEENTRY32W();
            me32.dwSize = new WinDef.DWORD(me32.size());

            if (Kernel32.INSTANCE.Module32FirstW(snapshotModules32, me32)) {
                do {
                    String foundModuleName = Native.toString(me32.szModule).toLowerCase();
                    if (foundModuleName.equals(moduleName.toLowerCase())) {
                        return me32.hModule;
                    }
                } while (Kernel32.INSTANCE.Module32NextW(snapshotModules32, me32));
            }

            if (Kernel32.INSTANCE.Module32FirstW(snapshotModules, me32)) {
                do {
                    String foundModuleName = Native.toString(me32.szModule).toLowerCase();
                    if (foundModuleName.equals(moduleName.toLowerCase())) {
                        return me32.hModule;
                    }
                } while (Kernel32.INSTANCE.Module32NextW(snapshotModules, me32));
            }
        } finally {
            Kernel32.INSTANCE.CloseHandle(snapshotModules);
            Kernel32.INSTANCE.CloseHandle(snapshotModules32);
        }

        return null;
    }

    public static Tlhelp32.MODULEENTRY32W getModule(int pid, String moduleName) {
        WinNT.HANDLE snapshotModules = Kernel32.INSTANCE.CreateToolhelp32Snapshot(Kernel32.TH32CS_SNAPMODULE, new WinDef.DWORD(pid));
        WinNT.HANDLE snapshotModules32 = Kernel32.INSTANCE.CreateToolhelp32Snapshot(Kernel32.TH32CS_SNAPMODULE32, new WinDef.DWORD(pid));

        try {
            Tlhelp32.MODULEENTRY32W me32 = new Tlhelp32.MODULEENTRY32W();
            me32.dwSize = new WinDef.DWORD(me32.size());

            if (Kernel32.INSTANCE.Module32FirstW(snapshotModules32, me32)) {
                do {
                    String foundModuleName = Native.toString(me32.szModule).toLowerCase();
                    if (foundModuleName.equals(moduleName.toLowerCase())) {
                        return me32;
                    }
                } while (Kernel32.INSTANCE.Module32NextW(snapshotModules32, me32));
            }

            if (Kernel32.INSTANCE.Module32FirstW(snapshotModules, me32)) {
                do {
                    String foundModuleName = Native.toString(me32.szModule).toLowerCase();
                    if (foundModuleName.equals(moduleName.toLowerCase())) {
                        return me32;
                    }
                } while (Kernel32.INSTANCE.Module32NextW(snapshotModules, me32));
            }
        } finally {
            Kernel32.INSTANCE.CloseHandle(snapshotModules);
            Kernel32.INSTANCE.CloseHandle(snapshotModules32);
        }

        return null;
    }

    ////////////////MANIPULATIONS/////////////////////

    /**
     * Calcola l'indirizzo finale sommando gli offset specificati all'indirizzo di base.
     *
     * @param ptr l'indirizzo di base a cui aggiungere gli offset.
     * @param offsets gli offset da sommare all'indirizzo di base.
     * @return l'indirizzo finale ottenuto sommando gli offset all'indirizzo di base.
     */
    public static long findAddress(long ptr, byte[] offsets) {
        long  addr = ptr;
        for (int offset : offsets) {
            addr = addr + offset;
        }
        return addr;
    }

    /**
     * Scrive un valore intero nella memoria del processo remoto all'indirizzo ottenuto sommando l'offset specificato all'indirizzo base.
     *
     * @param baseAddr l'indirizzo base a cui aggiungere l'offset per ottenere l'indirizzo finale.
     * @param offset l'offset da sommare all'indirizzo base per ottenere l'indirizzo finale di scrittura.
     * @param value il valore intero da scrivere nella memoria del processo remoto.
     */
    public static void writeFromOffset(Ptr baseAddr, long offset, int value) {
        int offsetAsInt = (int) offset;
        Ptr finalPtr = baseAddr.copy().add(offsetAsInt);
        finalPtr.writeInt(value);
    }

    /**
     * Scrive un valore intero nella memoria del processo remoto all'indirizzo ottenuto sommando gli offset specificati al puntatore dato.
     *
     * @param pHandle il puntatore del processo remoto.
     * @param ptr l'indirizzo di base a cui aggiungere gli offset per ottenere l'indirizzo finale di scrittura.
     * @param offsets gli offset da sommare all'indirizzo di base per ottenere l'indirizzo finale di scrittura.
     * @param value il valore intero da scrivere nella memoria del processo remoto.
     */
    public static void writeFromOffsetPtr(WinNT.HANDLE pHandle, long ptr,  int[] offsets, int value) {
        Ptr pointer = new Ptr(pHandle, new Pointer(ptr));
        pointer.processName =  pointer.moduleName = "GTA5.exe";
        Ptr address = pointer.copy();
        for (int offset : offsets) {address.add(offset);}

        //System.out.println(address);
        address.writeInt(value);
    }

    /**
     * Legge un valore di tipo specificato dalla memoria del processo remoto all'indirizzo ottenuto sommando gli offset specificati al puntatore dato.
     *
     * @param pHandle il puntatore del processo remoto.
     * @param ptr l'indirizzo di base a cui aggiungere gli offset per ottenere l'indirizzo finale di lettura.
     * @param offsets gli offset da sommare all'indirizzo di base per ottenere l'indirizzo finale di lettura.
     * @param type il tipo di dato da leggere (Integer, Long o Float).
     * @return il valore letto dalla memoria del processo remoto di tipo specificato.
     * @throws IllegalArgumentException se il tipo di dato specificato non è supportato.
     */
    public static <T> T readFromOffsetPtr(WinNT.HANDLE pHandle, long ptr, int[] offsets, Class<T> type){
        Ptr pointer = new Ptr(pHandle, new Pointer(ptr));
        pointer.processName =  pointer.moduleName = "GTA5.exe";
        Ptr address = pointer.copy();
        for (int offset : offsets) {address.add(offset);}
        if (type == Integer.class) {
            return type.cast(address.readInt());
        } else if (type == Long.class) {
            return type.cast(address.readLong());
        } else if (type == Float.class) {
            return type.cast(address.readFloat());
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }

    /**
     * Legge un valore di tipo specificato dalla memoria del processo remoto all'indirizzo ottenuto sommando l'offset specificato all'indirizzo base.
     *
     * @param baseAddr l'indirizzo base a cui aggiungere l'offset per ottenere l'indirizzo finale di lettura.
     * @param offset l'offset da sommare all'indirizzo base per ottenere l'indirizzo finale di lettura.
     * @param type il tipo di dato da leggere (Integer, Long o Float).
     * @return il valore letto dalla memoria del processo remoto di tipo specificato.
     * @throws IllegalArgumentException se il tipo di dato specificato non è supportato.
     */
    public static <T> T readFromOffset(Ptr baseAddr, long offset, Class<T> type) {
        int offsetAsInt = (int) offset;
        Ptr finalPtr = baseAddr.copy().add(offsetAsInt);

        if (type == Integer.class) {
            return type.cast(finalPtr.readInt());
        } else if (type == Long.class) {
            return type.cast(finalPtr.readLong());
        } else if (type == Float.class) {
            return type.cast(finalPtr.readFloat());
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }

    ////////////////GLOBALS/////////////////////

   public static long GA(long globalPTR, int index){
       return (globalPTR + (8 * (index >> 0x12 & 0x3F))) + (8 * (index & 0x3FFFF));
   }

   /* public static void setGlobalStats(String stat, int value) throws InterruptedException {

        int hash = JOAAT.joaat(stat);
        int oldhash = GG_Int(1665476 + 4);//freemode.c
        int oldvalue = GG_Int(980531 + 5525);//freemode.c mpply_fm_mission_likes
        SG_Int(1665476 + 4, hash);
        SG_Int(980531 + 5525, value);
        SG_Int(1654054 + 1139, -1);
        Thread.sleep(1000);
        SG_Int(1665476 + 4, oldhash);
        SG_Int(980531 + 5525, oldvalue);
    }*/
}