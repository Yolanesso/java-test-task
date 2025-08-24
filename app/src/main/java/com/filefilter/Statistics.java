package com.filefilter;

import java.math.BigInteger;

public class Statistics {
    private long count = 0;
    
    private BigInteger minInt = null;
    private BigInteger maxInt = null;
    private BigInteger sumInt = BigInteger.ZERO;
    
    private Double minFloat = null;
    private Double maxFloat = null;
    private Double sumFloat = 0.0;
    
    private Integer minStringLength = null;
    private Integer maxStringLength = null;

    public void addInteger(String value) {
        BigInteger num = new BigInteger(value);
        count++;
        
        if (minInt == null || num.compareTo(minInt) < 0) {
            minInt = num;
        }
        if (maxInt == null || num.compareTo(maxInt) > 0) {
            maxInt = num;
        }
        sumInt = sumInt.add(num);
    }

    public void addFloat(String value) {
        double num = Double.parseDouble(value);
        count++;
        
        if (minFloat == null || num < minFloat) {
            minFloat = num;
        }
        if (maxFloat == null || num > maxFloat) {
            maxFloat = num;
        }
        sumFloat += num;
    }

    public void addString(String value) {
        int length = value.length();
        count++;
        
        if (minStringLength == null || length < minStringLength) {
            minStringLength = length;
        }
        if (maxStringLength == null || length > maxStringLength) {
            maxStringLength = length;
        }
    }

    public long getCount() {
        return count;
    }

    public String getShortStats(DataType type) {
        return type + ": " + count + " элементов";
    }

    public String getFullStats(DataType type) {
        StringBuilder stats = new StringBuilder();
        stats.append(type).append(": ").append(count).append(" элементов\n");
        
        switch (type) {
            case INTEGER:
                if (count > 0) {
                    stats.append("  Минимальное: ").append(minInt).append("\n");
                    stats.append("  Максимальное: ").append(maxInt).append("\n");
                    stats.append("  Сумма: ").append(sumInt).append("\n");
                    stats.append("  Среднее: ").append(sumInt.doubleValue() / count).append("\n");
                }
                break;
            case FLOAT:
                if (count > 0) {
                    stats.append("  Минимальное: ").append(minFloat).append("\n");
                    stats.append("  Максимальное: ").append(maxFloat).append("\n");
                    stats.append("  Сумма: ").append(sumFloat).append("\n");
                    stats.append("  Среднее: ").append(sumFloat / count).append("\n");
                }
                break;
            case STRING:
                if (count > 0) {
                    stats.append("  Минимальная длина: ").append(minStringLength).append("\n");
                    stats.append("  Максимальная длина: ").append(maxStringLength).append("\n");
                }
                break;
        }
        
        return stats.toString();
    }
}