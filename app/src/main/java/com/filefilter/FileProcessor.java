package com.filefilter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.nio.charset.StandardCharsets;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileProcessor {
    private final Config config;
    private final Map<DataType, Statistics> statistics = new HashMap<>();
    private final Map<DataType, BufferedWriter> writers = new HashMap<>();

    public FileProcessor(Config config) {
        this.config = config;
        statistics.put(DataType.INTEGER, new Statistics());
        statistics.put(DataType.FLOAT, new Statistics());
        statistics.put(DataType.STRING, new Statistics());
    }

    public void processFiles() throws IOException {
        Path outputDir = Paths.get(config.getOutputPath());
        if (!Files.exists(outputDir)) {
            Files.createDirectories(outputDir);
        }

        for (String inputFile : config.getInputFiles()) {
            processFile(inputFile);
        }

        closeWriters();
    }

    private void processFile(String filename) throws IOException {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(
            new FileInputStream(filename), StandardCharsets.UTF_8))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }
            processLine(line);
        }
    } catch (FileNotFoundException e) {
        System.err.println("Файл не найден: " + filename + " - пропускаем");
    } catch (IOException e) {
        System.err.println("Ошибка чтения файла: " + filename + " - " + e.getMessage());
    }
}

    private void processLine(String line) throws IOException {
        DataType type = determineType(line);
        if (type != null) {
            writeToFile(type, line);
            updateStatistics(type, line);
        }
    }

    private DataType determineType(String line) {
        try {
            new BigInteger(line);
            return DataType.INTEGER;
        } catch (NumberFormatException e1) {
            try {
                Double.parseDouble(line);
                return DataType.FLOAT;
            } catch (NumberFormatException e2) {
                return DataType.STRING;
            }
        }
    }

    private void writeToFile(DataType type, String line) throws IOException {
    BufferedWriter writer = writers.get(type);
    if (writer == null) {
        String filename = config.getOutputPath() + File.separator + 
                        config.getPrefix() + 
                        type.name().toLowerCase() + "s.txt";
        
        File file = new File(filename);
        if (file.exists() && !config.isAppendMode()) {
            file.delete();
        }
        
        writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(file, config.isAppendMode()), StandardCharsets.UTF_8));
        writers.put(type, writer);
    }
    
    writer.write(line);
    writer.newLine();
}

    private void updateStatistics(DataType type, String line) {
        Statistics stats = statistics.get(type);
        switch (type) {
            case INTEGER:
                stats.addInteger(line);
                break;
            case FLOAT:
                stats.addFloat(line);
                break;
            case STRING:
                stats.addString(line);
                break;
        }
    }

    private void closeWriters() {
        for (BufferedWriter writer : writers.values()) {
            try {
                writer.close();
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии файла: " + e.getMessage());
            }
        }
    }

    public void printStatistics() {
        if (!config.isShortStats() && !config.isFullStats()) {
            return;
        }

        System.out.println("=== СТАТИСТИКА ===");
        
        for (DataType type : DataType.values()) {
            Statistics stats = statistics.get(type);
            if (stats.getCount() > 0) {
                if (config.isFullStats()) {
                    System.out.println(stats.getFullStats(type));
                } else {
                    System.out.println(stats.getShortStats(type));
                }
            }
        }
    }
}