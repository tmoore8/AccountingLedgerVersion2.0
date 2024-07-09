package com.ps.interfaces;

import com.ps.Transaction;

import java.util.List;

public interface TransactionInt {
    List<Transaction> getAllTransactions();
    List<Transaction> getAllPayments();
    List<Transaction> getAllDeposits();
    Transaction getOneTransaction(int id);
    int createTransaction(Transaction transaction);
    void updateTransaction(int id, Transaction transaction);
    void deleteTransaction(int id);
}
