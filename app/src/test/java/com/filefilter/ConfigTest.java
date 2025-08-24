package com.filefilter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class ConfigTest {

    @Test
    public void testParseArgumentsBasic() {
        String[] args = {"file1.txt", "file2.txt"};
        Config config = Config.parseArguments(args);
        
        assertEquals(".", config.getOutputPath());
        assertEquals("", config.getPrefix());
        assertFalse(config.isAppendMode());
        assertFalse(config.isShortStats());
        assertFalse(config.isFullStats());
        assertEquals(List.of("file1.txt", "file2.txt"), config.getInputFiles());
    }

    @Test
    public void testParseArgumentsWithOptions() {
        String[] args = {"-o", "/tmp", "-p", "test_", "-a", "-s", "file1.txt"};
        Config config = Config.parseArguments(args);
        
        assertEquals("/tmp", config.getOutputPath());
        assertEquals("test_", config.getPrefix());
        assertTrue(config.isAppendMode());
        assertTrue(config.isShortStats());
        assertFalse(config.isFullStats());
        assertEquals(List.of("file1.txt"), config.getInputFiles());
    }

    @Test
    public void testParseArgumentsFullStats() {
        String[] args = {"-f", "file1.txt"};
        Config config = Config.parseArguments(args);
        
        assertFalse(config.isShortStats());
        assertTrue(config.isFullStats());
    }

    @Test
    public void testParseArgumentsMissingValue() {
        String[] args = {"-o"};
        assertThrows(IllegalArgumentException.class, () -> Config.parseArguments(args));
    }

    @Test
    public void testParseArgumentsNoInputFiles() {
        String[] args = {"-o", "/tmp"};
        assertThrows(IllegalArgumentException.class, () -> Config.parseArguments(args));
    }

    @Test
    public void testParseArgumentsUnknownOption() {
        String[] args = {"-x", "file.txt"};
        assertThrows(IllegalArgumentException.class, () -> Config.parseArguments(args));
    }
}