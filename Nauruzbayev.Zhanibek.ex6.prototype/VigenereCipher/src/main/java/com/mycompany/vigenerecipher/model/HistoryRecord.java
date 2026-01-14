package com.mycompany.vigenerecipher.model;

import java.time.Instant;
import java.util.Objects;

/**
 * Represents a single Vigenère cipher operation record.
 * Immutable data class for storing operation history.
 * 
 * @author Zhanibek
 * @version 5.0
 */
public class HistoryRecord {
    
    private final String mode;
    private final String input;
    private final String output;
    private final String key;
    private final Instant timestamp;
    
    /**
     * Constructs a HistoryRecord with validation.
     * 
     * @param mode Operation mode (ENCRYPT/DECRYPT)
     * @param input Input text
     * @param output Output text
     * @param key Cipher key
     * @param timestamp Operation timestamp
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public HistoryRecord(String mode, String input, String output, 
                        String key, Instant timestamp) {
        if (mode == null || mode.trim().isEmpty()) {
            throw new IllegalArgumentException("Mode cannot be null or empty");
        }
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        if (output == null) {
            throw new IllegalArgumentException("Output cannot be null");
        }
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        
        this.mode = mode;
        this.input = input;
        this.output = output;
        this.key = key;
        this.timestamp = timestamp;
    }
    
    // Getters
    public String mode() {
        return mode;
    }
    
    public String input() {
        return input;
    }
    
    public String output() {
        return output;
    }
    
    public String key() {
        return key;
    }
    
    public Instant timestamp() {
        return timestamp;
    }
    
    // equals() and hashCode() for proper comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryRecord that = (HistoryRecord) o;
        return Objects.equals(mode, that.mode) &&
               Objects.equals(input, that.input) &&
               Objects.equals(output, that.output) &&
               Objects.equals(key, that.key) &&
               Objects.equals(timestamp, that.timestamp);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(mode, input, output, key, timestamp);
    }
    
    @Override
    public String toString() {
        return "HistoryRecord{" +
               "mode='" + mode + '\'' +
               ", input='" + input + '\'' +
               ", output='" + output + '\'' +
               ", key='" + key + '\'' +
               ", timestamp=" + timestamp +
               '}';
    }
}