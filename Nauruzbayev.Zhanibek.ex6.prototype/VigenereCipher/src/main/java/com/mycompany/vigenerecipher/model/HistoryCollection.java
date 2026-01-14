package com.mycompany.vigenerecipher.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Type-safe collection specifically for storing HistoryRecord objects.
 * Provides additional operations for filtering and processing history data.
 * Implements custom generic type requirement.
 * 
 * @param <E> The type of elements in this collection (must be HistoryRecord)
 * 
 * @author Zhanibek
 * @version 4.0
 */
public class HistoryCollection<E extends HistoryRecord> implements Collection<E> {
    
    /** Internal storage for history records */
    private final List<E> records;
    
    /**
     * Constructs an empty HistoryCollection.
     */
    public HistoryCollection() {
        this.records = new ArrayList<>();
    }
    
    /**
     * Constructs a HistoryCollection containing the elements of the specified collection.
     * 
     * @param c The collection whose elements are to be placed into this collection
     * @throws NullPointerException if the specified collection is null
     */
    public HistoryCollection(Collection<? extends E> c) {
        this.records = new ArrayList<>(c);
    }
    
    /**
     * Gets all records for a specific operation type using streams.
     * Demonstrates stream processing requirement.
     * 
     * @param operationType The type of operation to filter by
     * @return List of records matching the specified operation type
     * @throws IllegalArgumentException if operationType is null
     */
    public List<E> getRecordsByType(OperationType operationType) {
        if (operationType == null) {
            throw new IllegalArgumentException("Operation type cannot be null");
        }
        
        return records.stream()
                .filter(record -> record.mode().equals(operationType.name()))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets records containing specific text in input or output using streams and lambda.
     * Demonstrates lambda expression requirement.
     * 
     * @param searchText The text to search for
     * @return List of records containing the search text
     * @throws IllegalArgumentException if searchText is null or empty
     */
    public List<E> searchRecords(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            throw new IllegalArgumentException("Search text cannot be null or empty");
        }
        
        final String searchLower = searchText.toLowerCase();
        
        // Lambda expression for filtering records
        java.util.function.Predicate<E> containsText = record -> 
            record.input().toLowerCase().contains(searchLower) || 
            record.output().toLowerCase().contains(searchLower);
        
        return records.stream()
                .filter(containsText)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets the most recent records using streams.
     * 
     * @param count Maximum number of recent records to return
     * @return List of most recent records
     * @throws IllegalArgumentException if count is negative
     */
    public List<E> getRecentRecords(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }
        
        return records.stream()
                .sorted((r1, r2) -> r2.timestamp().compareTo(r1.timestamp()))
                .limit(count)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets the number of records in the collection.
     * 
     * @return The number of records
     */
    @Override
    public int size() { 
        return records.size(); 
    }
    
    /**
     * Checks if the collection is empty.
     * 
     * @return true if the collection contains no records
     */
    @Override
    public boolean isEmpty() { 
        return records.isEmpty(); 
    }
    
    /**
     * Checks if the collection contains the specified record.
     * 
     * @param o Record to check for presence
     * @return true if the collection contains the record
     */
    @Override
    public boolean contains(Object o) { 
        return records.contains(o); 
    }
    
    /**
     * Returns an iterator over the records in this collection.
     * 
     * @return Iterator over the records
     */
    @Override
    public Iterator<E> iterator() { 
        return records.iterator(); 
    }
    
    /**
     * Returns an array containing all records in this collection.
     * 
     * @return Array of records
     */
    @Override
    public Object[] toArray() { 
        return records.toArray(); 
    }
    
    /**
     * Returns an array containing all records in this collection.
     * 
     * @param <T> The type of the array to contain the records
     * @param a The array into which the records are to be stored
     * @return Array of records
     */
    @Override
    public <T> T[] toArray(T[] a) { 
        return records.toArray(a); 
    }
    
    /**
     * Adds a record to the collection.
     * 
     * @param e Record to be added
     * @return true if the collection changed as a result of the call
     * @throws IllegalArgumentException if the record is null
     */
    @Override
    public boolean add(E e) { 
        if (e == null) {
            throw new IllegalArgumentException("Cannot add null record");
        }
        return records.add(e); 
    }
    
    /**
     * Removes a single record from the collection.
     * 
     * @param o Record to be removed
     * @return true if the record was removed
     */
    @Override
    public boolean remove(Object o) { 
        return records.remove(o); 
    }
    
    /**
     * Checks if the collection contains all records in the specified collection.
     * 
     * @param c Collection to be checked for containment
     * @return true if the collection contains all records
     */
    @Override
    public boolean containsAll(Collection<?> c) { 
        return records.containsAll(c); 
    }
    
    /**
     * Adds all records from the specified collection to this collection.
     * 
     * @param c Collection containing records to be added
     * @return true if the collection changed as a result of the call
     * @throws IllegalArgumentException if the collection is null
     */
    @Override
    public boolean addAll(Collection<? extends E> c) { 
        if (c == null) {
            throw new IllegalArgumentException("Collection cannot be null");
        }
        return records.addAll(c); 
    }
    
    /**
     * Removes all records in the specified collection from this collection.
     * 
     * @param c Collection containing records to be removed
     * @return true if the collection changed as a result of the call
     */
    @Override
    public boolean removeAll(Collection<?> c) { 
        return records.removeAll(c); 
    }
    
    /**
     * Retains only the records in this collection that are contained in the specified collection.
     * 
     * @param c Collection containing records to be retained
     * @return true if the collection changed as a result of the call
     */
    @Override
    public boolean retainAll(Collection<?> c) { 
        return records.retainAll(c); 
    }
    
    /**
     * Removes all records from this collection.
     */
    @Override
    public void clear() { 
        records.clear(); 
    }
    
    /**
     * Compares this collection with the specified object for equality.
     * 
     * @param o Object to be compared for equality
     * @return true if the specified object is equal to this collection
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoryCollection)) return false;
        HistoryCollection<?> that = (HistoryCollection<?>) o;
        return records.equals(that.records);
    }
    
    /**
     * Returns the hash code value for this collection.
     * 
     * @return The hash code value
     */
    @Override
    public int hashCode() { 
        return records.hashCode(); 
    }
    
    /**
     * Returns a string representation of this collection.
     * 
     * @return String representation
     */
    @Override
    public String toString() {
        return "HistoryCollection{records=" + records + '}';
    }
}