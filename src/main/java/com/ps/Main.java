package com.ps;

import com.ps.DAOs.TransactionDao;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static TransactionDao transactionDao;
    
    public static void main(String[] args) {
        display(args);
    }
    
    public static void init(String[] args) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/AccountingLedger");
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUsername(args[0]);
        basicDataSource.setPassword(args[1]);
        
        transactionDao = new TransactionDao(basicDataSource);
        
    }
    
    public static void display(String[] args) {
        init(args);
        Scanner scanner = new Scanner(System.in);
        int     userIn;
        do {
            System.out.println("\nWelcome to HAMM Accounting. Please select an option: ");
            System.out.println("\n\t1) Add deposit");
            System.out.println("\t2) Make Payment");
            System.out.println("\t3) Display Ledger");
            System.out.println("\t4) Exit");
            userIn = scanner.nextInt();
            
            switch(userIn) {
                case 1:
                    //Add a deposit prompt the user for a deposit information and save it to CSV
                    addDeposit(scanner);
                    break;
                case 2:
                    // prompt user for debit information to save to file
                    makePayment(scanner);
                    break;
                case 3:
                    //display ledger menu
                    displayLedgerMenu(scanner);
                    break;
                case 4:
                    System.out.println("Thank you for banking with us.");
                    break;
                default:
                    System.out.println("Command not found.");
            }
        } while(userIn != 4);
    }
    
    
    //method to display ledger menu
    private static void displayLedgerMenu(Scanner scanner) {
        System.out.println("\nLedger: ");
        int ledgerCommand;
        do {
            System.out.println("\n\t1) Display all entries: ");
            System.out.println("\t2) Display deposits;");
            System.out.println("\t3) Display Payments");
            System.out.println("\t4) Reports Menu");
            System.out.println("\t5) Back");
            ledgerCommand = scanner.nextInt();
            scanner.nextLine();//consume new line
            switch(ledgerCommand) {
                case 1:
                    //display all entries
                    List<Transaction> allTransactions = transactionDao.getAllTransactions();
                    displayTransactions(allTransactions);
                    break;
                case 2:
                    // display deposits
                    List<Transaction> onlyDepositsTransactions = transactionDao.getAllDeposits();
                    displayTransactions(onlyDepositsTransactions);
                    break;
                case 3:
                    //display payments
                    List<Transaction> onlyPaymentsTransactions = transactionDao.getAllPayments();
                    displayTransactions(onlyPaymentsTransactions);
                    break;
                case 4:
                    //reports menu
                    displayReportsMenu(scanner);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid option.");
                
            }
        } while(ledgerCommand != 5);
        
    }
    
    //method to display reports menu
    private static void displayReportsMenu(Scanner scanner) {
        int reportCommand;
        do {
            System.out.println("\nSearch Reports by: ");
            System.out.println("\n\t1) Month to Date");
            System.out.println("\t2) Previous Month");
            System.out.println("\t3) Year to Date");
            System.out.println("\t4) Previous Year");
            System.out.println("\t5) Vendor");
            System.out.println("\t6) Custom Search");
            System.out.println("\t7) Back");
            reportCommand = scanner.nextInt();
            scanner.nextLine();//consume new line;
            
            switch(reportCommand) {
                case 1:
                    List<Transaction> monthToDateTransactions = transactionDao.monthToDate();
                    
                    displayTransactions(monthToDateTransactions);
                    break;
                case 2:
                    List<Transaction> previousMonthTransactions = transactionDao.previousMonth();
                    
                    displayTransactions(previousMonthTransactions);
                    break;
                case 3:
                    List<Transaction> yearToDateTransactions = transactionDao.yearToDate();

                    displayTransactions(yearToDateTransactions);
                    break;
                case 4:
                    List<Transaction> previousYearTransactions = transactionDao.previousYear();
                    
                    displayTransactions(previousYearTransactions);
                    break;
                case 5:
                    System.out.println("Enter vendor name: ");
                    
                    String vendorName = scanner.nextLine();
                    List<Transaction> vendorTransactions = transactionDao.searchByVendor(vendorName);
                    if(vendorTransactions.isEmpty()) {
                        System.out.println("No transactions for vendor: " + vendorName);
                    } else {
                        displayTransactions(vendorTransactions);
                    }
                    break;
                case 6:
                    
                    System.out.println("Enter start date (yyyy-MM-dd): ");
                    String startDateInput = scanner.nextLine().trim();
                    
                    System.out.println("Enter end date (yyyy-MM-dd): ");
                    String endDateInput = scanner.nextLine().trim();
                    
                    if(!startDateInput.isEmpty() && endDateInput.isEmpty()) {
                        
                        List<Transaction> startDateList = transactionDao.searchByOneDate(startDateInput);
                        
                        displayTransactions(startDateList);
                        break;
                        
                    } else if(startDateInput.isEmpty() && !endDateInput.isEmpty()) {
                        
                        List<Transaction> endDateList = transactionDao.searchByOneDate(endDateInput);
                        
                        displayTransactions(endDateList);
                        break;
                        
                    } else if(!startDateInput.isEmpty() && !endDateInput.isEmpty()) {
                        
                        List<Transaction> bothDatesList = transactionDao.searchByDates(startDateInput, endDateInput);
                        
                        displayTransactions(bothDatesList);
                        break;
                    }
                    
                    System.out.println("Enter description: ");
                    String descriptionCustomSearch = scanner.nextLine().trim();
                    
                    if(!descriptionCustomSearch.isEmpty()) {
                        
                        List<Transaction> descriptionList = transactionDao.searchByDescription(descriptionCustomSearch);
                        displayTransactions(descriptionList);
                        break;
                    }
                    
                    System.out.println("Enter vendor name: ");
                    String vendorCustomSearch = scanner.nextLine().trim();
                    
                    if(!vendorCustomSearch.isEmpty()) {
                        
                        List<Transaction> vendorList = transactionDao.searchByVendor(vendorCustomSearch);
                        displayTransactions(vendorList);
                        break;
                    }
                    
                    System.out.println("Enter amount: ");
                    Float amountCustomSearch = scanner.nextFloat();
                    scanner.nextLine();
                    if(amountCustomSearch != null) {
                        
                        List<Transaction> amountList = transactionDao.searchByAmount(amountCustomSearch);
                        displayTransactions(amountList);
                        break;
                    }
                    break;
                
                case 7:
                    break;
                default:
                    System.out.println("Invalid option.");
                
            }
        } while(reportCommand != 7);
    }
    
    // method to receive deposit inputs
    private static String[] addDeposit(Scanner scanner) {
        boolean isPayment = false; // use for transactionDao.create()
        boolean isDeposit = true; // use for transactionDao.create()
    
        scanner.nextLine(); // consumes new line
        System.out.print("Enter the description of your deposit: ");
        String description = scanner.nextLine();
        
        
        System.out.print("Enter the vendor: ");
        String vendor = scanner.nextLine();
        
        System.out.print("Enter the dollar amount: $");
        
        // checks to see if the user put a double value or not.
        if(scanner.hasNextDouble()) {
            double dollarAmount = scanner.nextDouble();
            scanner.nextLine();
            
            return new String[]{description, vendor, Double.toString(dollarAmount)};
        } else {
            scanner.nextLine();
            System.out.println("\nYou have entered an incorrect input type. Please try again.");
            return null;
        }
    }
    
    //method to receive payment inputs
    private static String[] makePayment(Scanner scanner) {
        boolean isPayment = true; // use for transactionDao.create()
        boolean isDeposit = false; // use for transactionDao.create()
    
        scanner.nextLine(); // consumes new line
        System.out.print("Enter the description of your payment: ");
        String description = scanner.nextLine();
        
        
        System.out.print("Enter the vendor: ");
        String vendor = scanner.nextLine();
        
        System.out.print("Enter the dollar amount: $");
        
        // checks to see if the user put a double value or not.
        if(scanner.hasNextDouble()) {
            double dollarAmount = scanner.nextDouble();
            scanner.nextLine();
            
            return new String[]{description, vendor, Double.toString(dollarAmount)};
        } else {
            scanner.nextLine();
            System.out.println("\nYou have entered an incorrect input type. Please try again.");
            return null;
        }
    }
    
    public static void displayTransactions(List<Transaction> transactions) {
        
        System.out.println("\n****************************************  Transactions  *********************************************");
        System.out.println("Date           Description                    Vendor               Amount");
        System.out.println("------------------------------------------------------------------------------------------------------");
        
        for(Transaction transaction : transactions) {
            
            System.out.printf("~ %-12s %-30s %-20s %-10.2f\n",
            
                              transaction.getDate(),
                              transaction.getDescription(),
                              transaction.getVendor(),
                              transaction.getAmount()
            
            );
        }
        
        if(transactions.isEmpty()) {
            System.out.println("\n No transactions founds");
        }
        
    }
}










