package com.nrcp.expensetracker.repository;

import com.nrcp.expensetracker.model.ActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {
    
    // Find logs by user email
    List<ActionLog> findByUserEmail(String userEmail);
    
    // Find logs by target table and ID
    List<ActionLog> findByTargetTableAndTargetId(String targetTable, Long targetId);
    
    // Find logs by target table
    List<ActionLog> findByTargetTable(String targetTable);
}