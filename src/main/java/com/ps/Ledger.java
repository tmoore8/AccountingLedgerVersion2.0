package com.ps;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.io.IOException;


public class Ledger {
    //ArrayList to store transactions
    public ArrayList<Transaction> transactions;

    // Constructor
    public Ledger() {
        transactions = new ArrayList<>();
        readTransactionsFromFile("transactions.txt");
    }


    //method to read transactions from file and add to the products ArrayList
    private void readTransactionsFromFile(String filename) {
        try (BufferedReader bufReader = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0].trim());
                    LocalTime time = LocalTime.parse(parts[1].trim());
                    String description = parts[2].trim();
                    String vendor = parts[3].trim();
                    float amount = Float.parseFloat(parts[4].trim());
                    transactions.add(new Transaction(date, time, description, vendor, amount));
                }
            }
        } catch (IOException e) {

            System.out.println("Error reading from file");
        }
    }

    /*  //Method to read list of transactions
      public static ArrayList<Transaction> getTransactions() {
          return transactions;
      }
  */
    // method to search transactions by vendor name
    public ArrayList<Transaction> searchByVendor(String vendor) {
        ArrayList<Transaction> searchResults = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getVendor().toLowerCase().contains(vendor.toLowerCase())) {
                searchResults.add(transaction);
            }
        }
        return searchResults;
    }

    //method to add deposit to file(+transaction)
    public void addDepositToFile(LocalDate date, LocalTime time, String description, String vendor, float amount) {
        transactions.add(new Transaction(date, time, description, vendor, amount));
        updateTransactionsFile();
    }

    //method to make payment to file(-transaction)
    public void makePaymentToFile(LocalDate date, LocalTime time, String description, String vendor, float amount) {
        transactions.add(new Transaction(date, time, description, vendor, -amount)); // Negative amount for payments
        updateTransactionsFile();
    }

    // method to write transactions to file
    private void updateTransactionsFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            for (Transaction transaction : transactions) {
                String formattedTransaction = String.format("%s|%s|%s|%s|%.2f", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());

                writer.write(formattedTransaction);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating transactions file");
        }
    }

    public void displayAllTransactions() {
        System.out.println("All Transactions:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public void displayOnlyDeposits() {
        System.out.println("Deposits:");
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction);
            }
        }
    }

    public void displayOnlyPayments() {
        System.out.println("Payments:");
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);
            }
        }
    }

    public ArrayList<Transaction> filterTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate();
            if (!transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate)) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }
}