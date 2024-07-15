package com.ps.interfaces;

import com.ps.Transaction;

import java.util.List;

public interface TransactionInt {
    List<Transaction> getAllTransactions();
    List<Transaction> getAllPayments();
    List<Transaction> getAllDeposits();
    Transaction getOneTransaction(int id);
    Transaction createTransaction(Transaction transaction, boolean isPayment, boolean isDeposit);
    void updateTransaction(int id, Transaction transaction);
    void deleteTransaction(int id);
    
    // Add methods for filtering Transactions by a criteria (date, vendor, etc.)
}
