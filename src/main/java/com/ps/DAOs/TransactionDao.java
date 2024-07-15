package com.ps.DAOs;

import com.ps.Transaction;
import com.ps.interfaces.TransactionInt;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao implements TransactionInt
{
    private BasicDataSource basicDataSource;
    
    public TransactionDao(BasicDataSource basicDataSource)
    {
        this.basicDataSource = basicDataSource;
    }
    
    @Override
    public List<Transaction> getAllTransactions()
    {
        List<Transaction> transactions = new ArrayList<>();
        
        String sql = "SELECT * FROM transactions";
        
        try(
                Connection connection = basicDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet row = ps.executeQuery();
        )
        {
            while(row.next())
            {
                Transaction transaction = mapRow(row);
                
                transactions.add(transaction);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return transactions;
    }
    
    @Override
    public List<Transaction> getAllPayments()
    {
        List<Transaction> payments = new ArrayList<>();
        
        String sql = "SELECT p.*, date, description, vendor, amount " +
                " FROM payments AS p " +
                " JOIN transactions AS t " +
                "   ON p.transaction_id = t.transaction_id";
        
        try(
                Connection connection = basicDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet row = ps.executeQuery();
        )
        {
            while(row.next())
            {
                Transaction transaction = mapRow(row);
                
                payments.add(transaction);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return payments;
    }
    
    @Override
    public List<Transaction> getAllDeposits()
    {
        List<Transaction> deposits = new ArrayList<>();
        
        String sql = "SELECT d.*, date, description, vendor, amount " +
                " FROM deposits AS d " +
                " JOIN transactions AS t " +
                "   ON d.transaction_id = t.transaction_id";
        
        try(
                Connection connection = basicDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet row = ps.executeQuery();
        )
        {
            while(row.next())
            {
                Transaction transaction = mapRow(row);
                
                deposits.add(transaction);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return deposits;
    }
    
    @Override
    public Transaction getTransactionById(int id)
    {
        String sql = "SELECT * " +
                " FROM transactions " +
                " WHERE transaction_id = ?";
        
        try(
                Connection connection = basicDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setInt(1, id);
            
            try(ResultSet row = ps.executeQuery();)
            {
                if(row.next())
                {
                    return mapRow(row);
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public Transaction create(Transaction transaction, boolean isPayment, boolean isDeposit)
    {
        Timestamp date = java.sql.Timestamp.valueOf(transaction.getDate());
        
        String transactionSql = "INSERT INTO transactions (date, description, vendor, amount) " +
                " VALUES (?, ?, ?, ?);";
        
        String paymentSql = "INSERT INTO payments (transaction_id) " +
                "VALUES (?);";
        
        String depositSql = "INSERT INTO deposits (transaction_id) " +
                " VALUES (?);";
        
        try(
                Connection connection = basicDataSource.getConnection();
                PreparedStatement transactionPs = connection.prepareStatement(transactionSql, PreparedStatement.RETURN_GENERATED_KEYS);
                PreparedStatement paymentPs = connection.prepareStatement(paymentSql);
                PreparedStatement depositPs = connection.prepareStatement(depositSql);
        )
        {
            transactionPs.setTimestamp(1, date);
            transactionPs.setString(2, transaction.getDescription());
            transactionPs.setString(3, transaction.getVendor());
            transactionPs.setFloat(4, transaction.getAmount());
            
            int rowsAffected = transactionPs.executeUpdate();
            
            if(rowsAffected > 0)
            {
                try(ResultSet generatedKeys = transactionPs.getGeneratedKeys();)
                {
                    if(generatedKeys.next())
                    {
                        // get id of the newly inserted transaction
                        int transactionId = generatedKeys.getInt(1);
                        
                        // set/update the id of the transaction object that's being passed
                        transaction.setId(transactionId);
                        
                        // add newly created transaction to either the Payments or Deposits table
                        if(isDeposit)
                        {
                            depositPs.setInt(1, transactionId);
                            depositPs.executeUpdate();
                        } else if(isPayment)
                        {
                            paymentPs.setInt(1, transactionId);
                            paymentPs.executeUpdate();
                        }
                        
                        // return the newly inserted transaction
                        return getTransactionById(transactionId);
                    }
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public Transaction update(int id, Transaction transaction)
    {
        Timestamp date = java.sql.Timestamp.valueOf(transaction.getDate());
        
        String sql = "UPDATE transactions" +
                " SET date = ? " +
                "   , description = ? " +
                "   , vendor = ? " +
                "   , amount = ? " +
                " WHERE transaction_id = ?;";
        
        try(
                Connection connection = basicDataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        )
        {
            ps.setTimestamp(1, date);
            ps.setString(2, transaction.getDescription());
            ps.setString(3, transaction.getVendor());
            ps.setFloat(4, transaction.getAmount());
            ps.setInt(5, id);
            
            ps.executeUpdate();
            
            return getTransactionById(id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return  null;
    }
    
    @Override
    public Transaction delete(int id)
    {
        String transactionSql = "DELETE FROM transactions " +
                " WHERE transaction_id = ?;";
        
        String paymentSql = "DELETE FROM payments " +
                " WHERE transaction_id = ?;";
        
        String depositSql = "DELETE FROM deposits " +
                " WHERE transaction_id = ?;";
        
        try(
                Connection connection = basicDataSource.getConnection();
                PreparedStatement transactionPs = connection.prepareStatement(transactionSql);
                PreparedStatement paymentPs = connection.prepareStatement(paymentSql);
                PreparedStatement depositPs = connection.prepareStatement(depositSql);
        )
        {
            // delete from transactions table
            transactionPs.setInt(1, id);
            transactionPs.executeUpdate();
    
            // delete from payments table
            paymentPs.setInt(1, id);
            paymentPs.executeUpdate();
    
            // delete from deposits table
            depositPs.setInt(1, id);
            depositPs.executeUpdate();
            
            return getTransactionById(id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }

    public List<Transaction> searchByOneDate(String date) {

        List<Transaction> transactions = new ArrayList<>();

        String dateQuery = "SELECT * FROM transactions WHERE date LIKE '%?%' ORDER BY date DESC;";

        try(
                Connection connection = basicDataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(dateQuery)
        ) {
            preparedStatement.setString(1, date);

            try(ResultSet row = preparedStatement.executeQuery()) {
                while(row.next()) {
                    Transaction transaction = mapRow(row);

                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;

    }

    public List<Transaction> searchByDates(String startDate, String endDate) {

        List<Transaction> transactions = new ArrayList<>();

        String dateQuery = "SELECT * FROM transactions WHERE date BETWEEN '?' AND '?' ORDER BY date DESC;";

        try(
                Connection connection = basicDataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(dateQuery)
        ) {
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);

            try(ResultSet row = preparedStatement.executeQuery()) {
                while(row.next()) {
                    Transaction transaction = mapRow(row);

                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;

    }

    public List<Transaction> searchByDescription(String description) {

        List<Transaction> transactions = new ArrayList<>();

        String dateQuery = "SELECT * FROM transactions WHERE description LIKE '?' ORDER BY date DESC;";

        try(
                Connection connection = basicDataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(dateQuery)
        ) {
            preparedStatement.setString(1, description);

            try(ResultSet row = preparedStatement.executeQuery()) {
                while(row.next()) {
                    Transaction transaction = mapRow(row);

                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;

    }

    public List<Transaction> searchByVendor(String vendor) {

        List<Transaction> transactions = new ArrayList<>();

        String dateQuery = "SELECT * FROM transactions WHERE vendor LIKE '?' ORDER BY date DESC;";

        try(
                Connection connection = basicDataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(dateQuery)
        ) {
            preparedStatement.setString(1, vendor);

            try(ResultSet row = preparedStatement.executeQuery()) {
                while(row.next()) {
                    Transaction transaction = mapRow(row);

                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;

    }

    public List<Transaction> searchByAmount(float amount) {

        List<Transaction> transactions = new ArrayList<>();

        String dateQuery = "SELECT * FROM transactions WHERE amount = ? ORDER BY date DESC;";

        try(
                Connection connection = basicDataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(dateQuery)
        ) {
            preparedStatement.setFloat(1, amount);

            try(ResultSet row = preparedStatement.executeQuery()) {
                while(row.next()) {
                    Transaction transaction = mapRow(row);

                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;

    }
    
    private Transaction mapRow(ResultSet row) throws SQLException
    {
        int    id          = row.getInt("transaction_id");
        String date        = row.getTimestamp("date").toString();
        String description = row.getString("description");
        String vendor      = row.getString("vendor");
        float  amount      = row.getFloat("amount");
        
        return new Transaction(id, date, description, vendor, amount);
    }
}
