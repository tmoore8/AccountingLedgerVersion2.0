# --------------------------------------aacAC-------------------------------- #
# Target DBMS:           MySQL                                           #
# Project name:          AccountingLedgerVersion2.0                                        #
# ---------------------------------------------------------------------- #
DROP DATABASE IF EXISTS AccountingLedger;

CREATE DATABASE IF NOT EXISTS AccountingLedger;

USE AccountingLedger;
# ---------------------------------------------------------------------- #
# Tables                                                                 #
# ---------------------------------------------------------------------- #
# ---------------------------------------------------------------------- #
# Add table "Transactions"                                                 #
# ---------------------------------------------------------------------- #
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    date DATETIME,
    description VARCHAR(255),
    vendor VARCHAR(255),
    amount DECIMAL(10, 2)
);
# ---------------------------------------------------------------------- #
# Add table "Payments"                                                 #
# ---------------------------------------------------------------------- #

CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    transaction_id INT NOT NULL,
    FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id)
);
# ---------------------------------------------------------------------- #
# Add table "Deposits"                                                 #
# ---------------------------------------------------------------------- #
CREATE TABLE deposits (
    deposit_id INT AUTO_INCREMENT PRIMARY KEY,
    transaction_id INT NOT NULL,
    FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id)
);

/*  INSERT Transactions  */
INSERT INTO transactions (date, description, vendor, amount) VALUES
('2023-04-15 10:13:25', 'ergonomic keyboard', 'Amazon', -89.50),
('2023-04-15 11:15:00', 'Invoice 1001 paid', 'Joe', 1500.00),
('2024-02-13 17:30:45', 'wireless mouse', 'Amazon', -29.95),
('2024-01-04 15:11:00', 'Invoice 1002 paid', 'Joe', 1250.00),
('2024-01-05 12:00:00', 'Monitor', 'BestBuy', -200.00),
('2024-01-06 14:30:00', 'Invoice 1003 paid', 'Alice', 1350.00),
('2024-01-07 09:45:00', 'Keyboard', 'Walmart', -45.00),
('2024-01-08 16:20:00', 'Invoice 1004 paid', 'Bob', 1425.00),
('2024-01-09 10:10:00', 'Mouse Pad', 'Amazon', -15.00),
('2024-01-10 11:30:00', 'Invoice 1005 paid', 'Charlie', 1575.00),
('2024-01-11 13:00:00', 'USB Cable', 'Amazon', -10.00),
('2024-01-12 15:15:00', 'Invoice 1006 paid', 'Dana', 1650.00),
('2024-01-13 16:45:00', 'Webcam', 'BestBuy', -85.00),
('2024-01-14 18:00:00', 'Invoice 1007 paid', 'Edward', 1725.00),
('2024-01-15 09:30:00', 'Desk Lamp', 'Ikea', -35.00),
('2024-01-16 11:45:00', 'Invoice 1008 paid', 'Frank', 1800.00),
('2024-01-17 13:00:00', 'Chair', 'Ikea', -120.00),
('2024-01-18 14:15:00', 'Invoice 1009 paid', 'Grace', 1875.00),
('2024-01-19 15:30:00', 'Desk', 'Ikea', -250.00),
('2024-01-20 16:45:00', 'Invoice 1010 paid', 'Hannah', 1950.00);
/*  INSERT Payments  */
INSERT INTO payments (transaction_id) VALUES
(1),
(3),
(5),
(7),
(9),
(11),
(13),
(15),
(17),
(19);
/*  INSERT Deposits  */
INSERT INTO deposits (transaction_id) VALUES
(2),
(4),
(6),
(8),
(10),
(12),
(14),
(16),
(18),
(20);

