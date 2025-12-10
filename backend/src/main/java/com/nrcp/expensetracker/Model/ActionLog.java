package com.nrcp.expensetracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "action_log")
public class ActionLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_id")
    private Long actionId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false, length = 10)
    private ActionType actionType;
    
    @Column(name = "target_table", length = 50)
    private String targetTable;
    
    @Column(name = "target_id")
    private Long targetId;
    
    @Column(name = "user_email", nullable = false, length = 100)
    private String userEmail;
    
    @Column(name = "action_timestamp", nullable = false)
    private LocalDateTime actionTimestamp = LocalDateTime.now();
    
    // Action type enum matching database ENUM
    public enum ActionType {
        ADDED,
        UPDATED,
        DELETED,
        VIEWED
    }
    
    // Constructors
    public ActionLog() {}
    
    public ActionLog(ActionType actionType, String targetTable, Long targetId, String userEmail) {
        this.actionType = actionType;
        this.targetTable = targetTable;
        this.targetId = targetId;
        this.userEmail = userEmail;
    }
    
    // Getters and Setters
    
    public Long getActionId() {
        return actionId;
    }
    
    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }
    
    public ActionType getActionType() {
        return actionType;
    }
    
    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
    
    public String getTargetTable() {
        return targetTable;
    }
    
    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }
    
    public Long getTargetId() {
        return targetId;
    }
    
    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public LocalDateTime getActionTimestamp() {
        return actionTimestamp;
    }
    
    public void setActionTimestamp(LocalDateTime actionTimestamp) {
        this.actionTimestamp = actionTimestamp;
    }
}