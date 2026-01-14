package com.mycompany.vigenerecipher.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cipher_operations")
public class CipherOperation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String operationType;
    private String key;
    private String input;
    private String output;
    private LocalDateTime timestamp;
    
    public CipherOperation() {}
    
    public CipherOperation(String operationType, String key, String input, String output) {
        this.operationType = operationType;
        this.key = key;
        this.input = input;
        this.output = output;
        this.timestamp = LocalDateTime.now();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getOperationType() { return operationType; }
    public void setOperationType(String operationType) { this.operationType = operationType; }
    
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    
    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }
    
    public String getOutput() { return output; }
    public void setOutput(String output) { this.output = output; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}