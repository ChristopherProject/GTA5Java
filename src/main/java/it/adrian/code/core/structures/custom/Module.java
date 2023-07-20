package it.adrian.code.core.structures.custom;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class Module extends Structure {
    public long dwBase;
    public long dwSize;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("dwBase", "dwSize");
    }
}