package com.nrcp.expensetracker.service;

import com.nrcp.expensetracker.model.ActionLog;
import com.nrcp.expensetracker.repository.ActionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActionLogService {
    
    private final ActionLogRepository actionLogRepository;
    
    @Autowired
    public ActionLogService(ActionLogRepository actionLogRepository) {
        this.actionLogRepository = actionLogRepository;
    }
    
    // Log an action to the database
    @Transactional
    public ActionLog logAction(ActionLog.ActionType actionType, String targetTable, 
                               Long targetId, String userEmail) {
        ActionLog log = new ActionLog(actionType, targetTable, targetId, userEmail);
        return actionLogRepository.save(log);
    }
    
    // Get all logs for a specific user
    public List<ActionLog> getLogsByUser(String userEmail) {
        return actionLogRepository.findByUserEmail(userEmail);
    }
    
    // Get all logs for a specific record (e.g., all actions on expense ID 5)
    public List<ActionLog> getLogsByTarget(String targetTable, Long targetId) {
        return actionLogRepository.findByTargetTableAndTargetId(targetTable, targetId);
    }
    
    // Get all logs for a specific table
    public List<ActionLog> getLogsByTable(String targetTable) {
        return actionLogRepository.findByTargetTable(targetTable);
    }
    
    // Get all action logs
    public List<ActionLog> getAllLogs() {
        return actionLogRepository.findAll();
    }
}