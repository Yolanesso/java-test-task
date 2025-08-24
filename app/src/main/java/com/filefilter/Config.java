package com.filefilter;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private String outputPath = ".";
    private String prefix = "";
    private boolean appendMode = false;
    private boolean shortStats = false;
    private boolean fullStats = false;
    private List<String> inputFiles = new ArrayList<>();

    public static Config parseArguments(String[] args) {
        Config config = new Config();
        int i = 0;
        
        while (i < args.length) {
            String arg = args[i];
            switch (arg) {
                case "-o":
                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException("Опция -o требует путь");
                    }
                    config.outputPath = args[++i];
                    break;
                case "-p":
                    if (i + 1 >= args.length) {
                        throw new IllegalArgumentException("Опция -p требует префикс");
                    }
                    config.prefix = args[++i];
                    break;
                case "-a":
                    config.appendMode = true;
                    break;
                case "-s":
                    config.shortStats = true;
                    break;
                case "-f":
                    config.fullStats = true;
                    break;
                default:
                    if (arg.startsWith("-")) {
                        throw new IllegalArgumentException("Неизвестная опция: " + arg);
                    }
                    config.inputFiles.add(arg);
                    break;
            }
            i++;
        }

        if (config.inputFiles.isEmpty()) {
            throw new IllegalArgumentException("Не указаны входные файлы");
        }

        return config;
    }

    // Getters
    public String getOutputPath() { return outputPath; }
    public String getPrefix() { return prefix; }
    public boolean isAppendMode() { return appendMode; }
    public boolean isShortStats() { return shortStats; }
    public boolean isFullStats() { return fullStats; }
    public List<String> getInputFiles() { return inputFiles; }
}