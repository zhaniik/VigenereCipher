package com.mycompany.vigenerecipher.model;

/**
 * Enumeration defining the types of cipher operations supported by the application.
 * Provides type-safe operation identifiers throughout the application and
 * ensures compile-time checking of operation types.
 * 
 * @author Zhanibek
 * @version 4.0
 * @see Model
 * @see HistoryRecord
 */
public enum OperationType {
    
    /** 
     * Represents an encryption operation.
     * Converts plaintext to ciphertext using the Vigenère cipher algorithm.
     */
    ENCRYPT,
    
    /** 
     * Represents a decryption operation.
     * Converts ciphertext back to plaintext using the Vigenère cipher algorithm.
     */
    DECRYPT;
    
    /**
     * Gets the display name for the operation type.
     * Converts the enum constant name to a properly formatted string
     * with the first letter capitalized and the rest lowercase.
     * 
     * @return Formatted string representation of the operation type
     * @since 1.0
     */
    public String getDisplayName() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}