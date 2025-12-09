-- db schema for expense tracker 12/10/2025 

CREATE USER IF NOT EXISTS 'expense_tracker'@'%' IDENTIFIED BY 'nrcp';
GRANT ALL PRIVILEGES ON expense_tracker.* TO 'expense_tracker'@'%';
FLUSH PRIVILEGES;

CREATE DATABASE IF NOT EXISTS expense_tracker;
USE expense_tracker;

-- store all expense records. multi-currency support.
CREATE TABLE expense (
    expense_id BIGINT NOT NULL AUTO_INCREMENT, -- unique id

    -- log and user
    date_logged TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'log date and time',
    logged_by VARCHAR(100) NOT NULL COMMENT 'user email who log it',

    -- item details
    category VARCHAR(50) NOT NULL COMMENT 'expense category',
    purpose_item VARCHAR(255) NOT NULL COMMENT 'item name or purpose',
    quantity INT NOT NULL COMMENT 'number of units',
    price_per DECIMAL(10, 2) NOT NULL COMMENT 'unit price in currency spent',
    
    -- date
    date_purchased DATE NOT NULL COMMENT 'date money spent',

    -- finance (multi-currency key)
    total_spent DECIMAL(10, 2) NOT NULL COMMENT 'final total cost in currency spent',
    currency_spent CHAR(3) NOT NULL COMMENT 'iso code of currency spent (cad, php, phb)',
    exchange_rate DECIMAL(10, 4) NULL COMMENT 'rate to convert to base currency',
    base_currency_amount DECIMAL(10, 2) NULL COMMENT 'converted amount in base currency (for fast sum)',
    
    -- status
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'soft delete flag',

    PRIMARY KEY (expense_id),
    INDEX idx_date_purchased (date_purchased) -- fast lookup by date
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'main expense table';


-- log all user action
CREATE TABLE action_log (
    action_id BIGINT NOT NULL AUTO_INCREMENT,

    action_type ENUM('ADDED', 'UPDATED', 'DELETED', 'VIEWED') NOT NULL COMMENT 'type of action',
    target_table VARCHAR(50) NULL COMMENT 'table action happen',
    target_id BIGINT NULL COMMENT 'id of row change',

    user_email VARCHAR(100) NOT NULL COMMENT 'user email key for accountability',
    action_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (action_id),
    INDEX idx_action_target (target_table, target_id) -- fast audit lookup
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'audit log table';