package com.ps.DAOs;

import com.ps.Transaction;
import com.ps.interfaces.TransactionInt;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao implements TransactionInt {
    private BasicDataSource basicDataSource;
    
    public TransactionDao(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }
    
    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        
        String sql = "SELECT * FROM transactions";
        
        try (
                Connection connection = basicDataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet row = statement.executeQuery();
        ) {
           while (row.next()) {
               Transaction transaction = mapRow(row);
               
               transactions.add(transaction);
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return transactions;
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
    
    private Transaction mapRow(ResultSet row) throws SQLException {
        int    id          = row.getInt("transaction_id");
        String date        = row.getTimestamp("date").toString();
        String description = row.getString("description");
        String vendor      = row.getString("vendor");
        float  amount      = row.getFloat("amount");
        
        return new Transaction(id, date, description, vendor, amount);
    }
}
