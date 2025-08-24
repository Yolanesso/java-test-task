package com.filefilter;

public class Main {
    public static void main(String[] args) {
        try {
            Config config = Config.parseArguments(args);
            FileProcessor processor = new FileProcessor(config);
            processor.processFiles();
            processor.printStatistics();
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка аргументов: " + e.getMessage());
            printUsage();
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    private static void printUsage() {
        System.out.println("Использование: java -jar utility.jar [опции] файлы...");
        System.out.println("Опции:");
        System.out.println("  -o путь      Путь для выходных файлов");
        System.out.println("  -p префикс   Префикс имен выходных файлов");
        System.out.println("  -a           Режим добавления в существующие файлы");
        System.out.println("  -s           Краткая статистика");
        System.out.println("  -f           Полная статистика");
        System.out.println("Если не указана ни -s, ни -f, статистика не выводится");
    }
}