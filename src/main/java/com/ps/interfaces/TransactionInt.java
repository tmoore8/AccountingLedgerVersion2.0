package com.ps.interfaces;

import com.ps.Transaction;

import java.util.List;

public interface TransactionInt {
    List<Transaction> getAllTransactions();
    List<Transaction> getAllPayments();
    List<Transaction> getAllDeposits();
    Transaction getTransactionById(int id);
    Transaction create(Transaction transaction, boolean isPayment, boolean isDeposit);
    Transaction update(int id, Transaction transaction);
    Transaction delete(int id);
    
    // Add methods for filtering Transactions by a criteria (date, vendor, etc.)
}
