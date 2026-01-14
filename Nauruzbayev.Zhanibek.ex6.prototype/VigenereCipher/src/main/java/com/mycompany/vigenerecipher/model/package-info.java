/**
 * Model package for the Vigenère Cipher application.
 * Contains the business logic, data structures, and core algorithms implementing the
 * Vigenère cipher encryption/decryption. This package follows the Model-View-Controller
 * (MVC) pattern as the data and business logic layer.
 * 
 * Main Responsibilities:
 * - Implement Vigenère cipher encryption and decryption algorithms
 * - Validate cipher keys and input data
 * - Maintain operation history with timestamps and metadata
 * - Provide statistics on encryption/decryption operations
 * - Offer search capabilities through operation history
 * 
 * Key Components:
 * - Model - Main business logic class handling cipher operations
 * - HistoryRecord - Immutable record representing a single operation
 * - HistoryCollection - Type-safe collection for operation history
 * - OperationType - Enumeration defining operation types
 * - Model.InvalidKeyException - Custom exception for key validation
 * 
 * Algorithm Features:
 * - Supports case-preserving encryption (upper/lower case maintained)
 * - Handles non-alphabetic characters (passed through unchanged)
 * - Implements repeating key mechanism as per Vigenère cipher specification
 * - Provides type-safe generics for history data management
 * 
 * Java Features Demonstrated:
 * - Java Records for immutable data storage
 * - Generics for type-safe collections
 * - Streams API for data processing and filtering
 * - Lambda expressions for functional programming
 * - Custom exceptions for domain-specific error handling
 * 
 * @author Zhanibek
 * @version 4.0
 * @see controller.Controller
 * @see view.MainView
 * @since 1.0
 */
package com.mycompany.vigenerecipher.model;