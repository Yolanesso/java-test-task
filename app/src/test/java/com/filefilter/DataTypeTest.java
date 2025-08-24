package com.filefilter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataTypeTest {

    @Test
    public void testEnumValues() {
        DataType[] values = DataType.values();
        assertEquals(3, values.length);
        assertEquals(DataType.INTEGER, values[0]);
        assertEquals(DataType.FLOAT, values[1]);
        assertEquals(DataType.STRING, values[2]);
    }

    @Test
    public void testEnumNames() {
        assertEquals("INTEGER", DataType.INTEGER.name());
        assertEquals("FLOAT", DataType.FLOAT.name());
        assertEquals("STRING", DataType.STRING.name());
    }
}