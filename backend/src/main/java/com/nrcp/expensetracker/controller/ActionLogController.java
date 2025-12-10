package com.nrcp.expensetracker.controller;

import com.nrcp.expensetracker.model.ActionLog;
import com.nrcp.expensetracker.service.ActionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "http://localhost:5173") // Vite dev server
public class ActionLogController {
    
    private final ActionLogService actionLogService;
    
    @Autowired
    public ActionLogController(ActionLogService actionLogService) {
        this.actionLogService = actionLogService;
    }
    
    // Get all action logs
    @GetMapping
    public ResponseEntity<List<ActionLog>> getAllLogs() {
        List<ActionLog> logs = actionLogService.getAllLogs();
        return ResponseEntity.ok(logs);
    }
    
    // Get logs by user email
    @GetMapping("/user/{email}")
    public ResponseEntity<List<ActionLog>> getLogsByUser(@PathVariable String email) {
        List<ActionLog> logs = actionLogService.getLogsByUser(email);
        return ResponseEntity.ok(logs);
    }
    
    // Get logs for a specific expense
    @GetMapping("/expense/{id}")
    public ResponseEntity<List<ActionLog>> getLogsByExpense(@PathVariable Long id) {
        List<ActionLog> logs = actionLogService.getLogsByTarget("expense", id);
        return ResponseEntity.ok(logs);
    }
    
    // Get all logs for the expense table
    @GetMapping("/table/expense")
    public ResponseEntity<List<ActionLog>> getExpenseTableLogs() {
        List<ActionLog> logs = actionLogService.getLogsByTable("expense");
        return ResponseEntity.ok(logs);
    }
}