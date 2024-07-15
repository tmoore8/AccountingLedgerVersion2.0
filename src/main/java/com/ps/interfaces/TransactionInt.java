package com.ps.interfaces;

import com.ps.Transaction;

import java.util.List;

public interface TransactionInt {
    List<Transaction> getAllTransactions();
    List<Transaction> getAllPayments();
    List<Transaction> getAllDeposits();
    Transaction getOneTransaction(int id);
    Transaction create(Transaction transaction, boolean isPayment, boolean isDeposit);
    void update(int id, Transaction transaction);
    void delete(int id);
    
    // Add methods for filtering Transactions by a criteria (date, vendor, etc.)
}
