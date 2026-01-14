package com.mycompany.vigenerecipher.model;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main model class for the Vigenère Cipher application.
 * Handles encryption, decryption, and maintains operation history.
 * Follows the MVC pattern as the model component.
 * 
 * @author Zhanibek
 * @version 5.0
 */
public final class Model {
    
    /** The cipher key used for encryption/decryption */
    private String key;
    
    /** Type-safe collection for storing operation history */
    private final HistoryCollection<HistoryRecord> history = new HistoryCollection<>();
    
    /**
     * Constructs a model with the specified key.
     * 
     * @param key Cipher key
     * @throws InvalidKeyException if the key is invalid
     */
    public Model(String key) throws InvalidKeyException {
        setKey(key);
    }
    
    /**
     * Gets the type-safe history collection.
     * 
     * @return History collection containing all operation records
     */
    public HistoryCollection<HistoryRecord> getHistory() { 
        return history; 
    }
    
    /**
     * Gets all history records as a list.
     * 
     * @return list of all history records
     */
    public List<HistoryRecord> getAllHistory() {
        return history.searchRecords("");
    }
    
    /**
     * Exception thrown when the cipher key is invalid.
     * 
     * @author Zhanibek
     * @version 5.0
     */
    public static class InvalidKeyException extends Exception {
        /**
         * Constructs a new InvalidKeyException with the specified detail message.
         * 
         * @param message The detail message explaining the key validation failure
         */
        public InvalidKeyException(String message) { 
            super(message); 
        }
    }
    
    /**
     * Gets the current cipher key.
     * 
     * @return The current cipher key
     */
    public String getKey() {
        return key;
    }
    
    /**
     * Sets the cipher key. Key must be non-empty letters-only string.
     * 
     * @param key Cipher key
     * @throws InvalidKeyException if key is null, empty, or contains non-letter characters
     */
    public void setKey(String key) throws InvalidKeyException {
        if (key == null || key.trim().isEmpty()) {
            throw new InvalidKeyException("Key cannot be empty.");
        }
        
        // Using for-each loop for character validation
        for (char c : key.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new InvalidKeyException("Key must contain only letters.");
            }
        }
        this.key = key;
    }
    
    /**
     * Encrypts input text using the current key and adds to history.
     * 
     * @param text Input text to encrypt
     * @return Encrypted text
     * @throws IllegalArgumentException if input text is null
     */
    public String encrypt(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Input text cannot be null");
        }
        
        String result = process(text, true);
        HistoryRecord record = new HistoryRecord(
            OperationType.ENCRYPT.name(), 
            text, 
            result, 
            key, 
            Instant.now()
        );
        history.add(record);
        return result;
    }
    
    /**
     * Decrypts input text using the current key and adds to history.
     * 
     * @param text Input text to decrypt
     * @return Decrypted text
     * @throws IllegalArgumentException if input text is null
     */
    public String decrypt(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Input text cannot be null");
        }
        
        String result = process(text, false);
        HistoryRecord record = new HistoryRecord(
            OperationType.DECRYPT.name(), 
            text, 
            result, 
            key, 
            Instant.now()
        );
        history.add(record);
        return result;
    }
    
    /**
     * Processes text for encryption or decryption using Vigenère cipher algorithm.
     * Uses for-each loop for character processing.
     * 
     * @param text Input text to process
     * @param encrypt true to encrypt, false to decrypt
     * @return Processed text
     */
    private String process(String text, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        String upperKey = key.toUpperCase();
        int keyIndex = 0;
        
        // Using for-each loop for character processing
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int offset = upperKey.charAt(keyIndex % upperKey.length()) - 'A';
                int shift = encrypt ? offset : -offset;
                char newChar = (char) ((c - base + shift + 26) % 26 + base);
                result.append(newChar);
                keyIndex++;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
    
    /**
     * Gets encryption statistics using streams.
     * 
     * @return Array containing [totalOperations, encryptionCount, decryptionCount]
     */
    public int[] getStatistics() {
        long encryptionCount = history.getRecordsByType(OperationType.ENCRYPT).size();
        long decryptionCount = history.getRecordsByType(OperationType.DECRYPT).size();
        
        return new int[] {
            history.size(),
            (int) encryptionCount,
            (int) decryptionCount
        };
    }
    
    /**
     * Searches history records using streams and lambda expressions.
     * 
     * @param searchText Text to search for in input/output
     * @return List of matching history records
     * @throws IllegalArgumentException if searchText is null or empty
     */
    public List<HistoryRecord> searchHistory(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            throw new IllegalArgumentException("Search text cannot be null or empty");
        }
        return history.searchRecords(searchText);
    }
    
    /**
     * Clears the operation history.
     * Useful for testing and resetting the application state.
     */
    public void clearHistory() {
        history.clear();
    }
    
    /**
     * Gets the size of the history collection.
     * 
     * @return number of records in history
     */
    public int getHistorySize() {
        return history.size();
    }
    
    /**
     * Gets recent history records.
     * 
     * @param count maximum number of recent records to return
     * @return list of recent history records
     * @throws IllegalArgumentException if count is negative
     */
    public List<HistoryRecord> getRecentHistory(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }
        return history.getRecentRecords(count);
    }
    
    /**
     * Gets history records filtered by operation type.
     * 
     * @param operationType type of operation to filter by
     * @return list of filtered history records
     * @throws IllegalArgumentException if operationType is null
     */
    public List<HistoryRecord> getHistoryByType(OperationType operationType) {
        if (operationType == null) {
            throw new IllegalArgumentException("Operation type cannot be null");
        }
        return history.getRecordsByType(operationType);
    }
}