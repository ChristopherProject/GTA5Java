package it.adrian.code.core.structures;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class MODULEINFO extends Structure {

    public Pointer lpBaseOfDll;
    public int SizeOfImage;
    public Pointer EntryPoint;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("lpBaseOfDll", "SizeOfImage", "EntryPoint");
    }
}