package com.filefilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestUtils {

    public static File createTestFile(Path directory, String filename, String content) throws IOException {
        File file = directory.resolve(filename).toFile();
        Files.writeString(file.toPath(), content);
        return file;
    }

    public static String readFileContent(File file) throws IOException {
        return Files.readString(file.toPath());
    }

    public static void deleteRecursively(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteRecursively(f);
                }
            }
        }
        file.delete();
    }
}