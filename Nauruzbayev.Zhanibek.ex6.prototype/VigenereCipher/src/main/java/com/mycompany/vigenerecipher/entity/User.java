package com.mycompany.vigenerecipher.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "session_id", unique = true, length = 100)
    private String sessionId;
    
    @Column(name = "visit_count")
    private Integer visitCount = 0;
    
    @Column(name = "last_visit")
    private LocalDateTime lastVisit;
    
    @Column(name = "client_ip", length = 50)
    private String clientIp;
    
    // Конструкторы
    public User() {}
    
    public User(String sessionId) {
        this.sessionId = sessionId;
        this.visitCount = 1;
        this.lastVisit = LocalDateTime.now();
    }
    
    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    
    public Integer getVisitCount() { return visitCount; }
    public void setVisitCount(Integer visitCount) { this.visitCount = visitCount; }
    
    public LocalDateTime getLastVisit() { return lastVisit; }
    public void setLastVisit(LocalDateTime lastVisit) { this.lastVisit = lastVisit; }
    
    public String getClientIp() { return clientIp; }
    public void setClientIp(String clientIp) { this.clientIp = clientIp; }
    
    // Вспомогательный метод для увеличения счетчика
    public void incrementVisitCount() {
        this.visitCount++;
        this.lastVisit = LocalDateTime.now();
    }
}