package com.ps.DAOs;

import com.ps.Transaction;
import com.ps.interfaces.TransactionInt;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;

public class TransactionDao implements TransactionInt {
    private BasicDataSource basicDataSource;
    
    public TransactionDao(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }
    
    @Override
    public List<Transaction> getAllTransactions() {
        return null;
    }
    
    @Override
    public List<Transaction> getAllPayments() {
        return null;
    }
    
    @Override
    public List<Transaction> getAllDeposits() {
        return null;
    }
    
    @Override
    public Transaction getOneTransaction(int id) {
        return null;
    }
    
    @Override
    public int createTransaction(Transaction transaction) {
        return 0;
    }
    
    @Override
    public void updateTransaction(int id, Transaction transaction) {
    
    }
    
    @Override
    public void deleteTransaction(int id) {
    
    }
}
