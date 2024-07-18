package com.ps;

import com.ps.DAOs.TransactionDao;
import org.apache.commons.dbcp2.BasicDataSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private static TransactionDao transactionDao;

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
        int userIn = 0;

        do {
            System.out.println("\nWelcome to HAMM Accounting. Please select an option: ");
            System.out.println("\n\t1) Add deposit");
            System.out.println("\t2) Make Payment");
            System.out.println("\t3) Display Ledger");
            System.out.println("\t4) Exit");

            try {
                userIn = Integer.parseInt(scanner.nextLine()); // Read input as String and parse to int

                switch (userIn) {
                    case 1:
                        // Add a deposit prompt the user for a deposit information and save it to CSV
                        addDeposit(scanner);
                        break;
                    case 2:
                        // prompt user for debit information to save to file
                        makePayment(scanner);
                        break;
                    case 3:
                        // display ledger menu
                        displayLedgerMenu(scanner);
                        break;
                    case 4:
                        System.out.println("Thank you for banking with us.");
                        break;
                    default:
                        System.out.println("Invalid command. Please enter a number between 1 and 4.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }

        } while (userIn != 4);
    }


    //method to display ledger menu
    private static void displayLedgerMenu(Scanner scanner) {
        System.out.println("\nLedger: ");
        int ledgerCommand = 0;

        do {
            System.out.println("\n\t1) Display all entries");
            System.out.println("\t2) Display deposits");
            System.out.println("\t3) Display Payments");
            System.out.println("\t4) Reports Menu");
            System.out.println("\t5) Back");


            try {
                ledgerCommand = Integer.parseInt(scanner.nextLine()); // Read input as String and parse to int

                switch (ledgerCommand) {
                    case 1:
                        // display all entries
                        List<Transaction> allTransactions = transactionDao.getAllTransactions();
                        displayTransactions(allTransactions);
                        break;
                    case 2:
                        // display deposits
                        List<Transaction> onlyDepositsTransactions = transactionDao.getAllDeposits();
                        displayTransactions(onlyDepositsTransactions);
                        break;
                    case 3:
                        // display payments
                        List<Transaction> onlyPaymentsTransactions = transactionDao.getAllPayments();
                        displayTransactions(onlyPaymentsTransactions);
                        break;
                    case 4:
                        // reports menu
                        displayReportsMenu(scanner);
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Invalid option. Please enter a number between 1 and 5.");
                }
            } catch (Exception e) {

                System.out.println("Invalid input. Please enter a valid number.");
            }

        } while (ledgerCommand != 5);
    }



    //method to display reports menu
    private static void displayReportsMenu(Scanner scanner) {
        int reportCommand = 0;

        do {
            System.out.println("\nSearch Reports by: ");
            System.out.println("\n\t1) Month to Date");
            System.out.println("\t2) Previous Month");
            System.out.println("\t3) Year to Date");
            System.out.println("\t4) Previous Year");
            System.out.println("\t5) Vendor");
            System.out.println("\t6) Custom Search");
            System.out.println("\t7) Back");

            try {
                reportCommand = Integer.parseInt(scanner.nextLine()); // Read input as String and parse to int

                switch (reportCommand) {
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
                        if (vendorTransactions.isEmpty()) {
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
                        System.out.println("Enter description: ");
                        String descriptionCustomSearch = scanner.nextLine().trim();
                        System.out.println("Enter vendor name: ");
                        String vendorCustomSearch = scanner.nextLine().trim();
                        System.out.println("Enter amount: ");
                        String amountInput = scanner.nextLine().trim();

                        try {
                            if (!amountInput.isBlank()) {
                                float amountCustomSearch = Float.parseFloat(amountInput);
                                List<Transaction> amountList = transactionDao.search(startDateInput, endDateInput, descriptionCustomSearch, vendorCustomSearch, amountCustomSearch);
                                displayTransactions(amountList);
                            } else if ((!startDateInput.isBlank() ||
                                    !endDateInput.isBlank() ||
                                    !descriptionCustomSearch.isBlank() ||
                                    !vendorCustomSearch.isBlank()) &&
                                    amountInput.isBlank()) {
                                List<Transaction> amountList = transactionDao.search(startDateInput, endDateInput, descriptionCustomSearch, vendorCustomSearch);
                                displayTransactions(amountList);
                            } else if (startDateInput.isBlank() &&
                                    endDateInput.isBlank() &&
                                    descriptionCustomSearch.isBlank() &&
                                    vendorCustomSearch.isBlank() &&
                                    amountInput.isBlank()) {
                                System.out.println("\n\tNo results found.\n");
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid amount entered. Please enter a valid number.");
                        }
                        break;
                    case 7:
                        break;
                    default:
                        System.out.println("Invalid option. Please enter a number between 1 and 7.");
                }
            } catch (Exception e) {

                System.out.println("Invalid input. Please enter a valid number.");
            }

        } while (reportCommand != 7);
    }


    // method to receive deposit inputs
    private static void addDeposit(Scanner scanner) {
        boolean isPayment = false;
        boolean isDeposit = true;
        
        scanner.nextLine(); // consumes new line
        System.out.print("Enter the description of your deposit: ");
        String description = scanner.nextLine();
        
        
        System.out.print("Enter the vendor: ");
        String vendor = scanner.nextLine();
        
        System.out.print("Enter the dollar amount: $");
        
        // checks to see if the user put a double value or not.
        if(scanner.hasNextFloat()) {
            float dollarAmount = scanner.nextFloat();
            scanner.nextLine();
            
            LocalDateTime     dateTime  = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String            date      = dateTime.format(formatter);
            
            Transaction deposit = new Transaction(date, description, vendor, dollarAmount);
            
            System.out.println("\n****************************************  Transactions  *********************************************");
            System.out.println("Date           Time          Description                    Vendor               Amount");
            System.out.println("------------------------------------------------------------------------------------------------------");
            System.out.println(transactionDao.create(deposit, isPayment, isDeposit));
        } else {
            scanner.nextLine();
            System.out.println("\nYou have entered an incorrect input type. Please try again.");
        }
        
        
    }
    
    //method to receive payment inputs
    private static void makePayment(Scanner scanner) {
        boolean isPayment = true;
        boolean isDeposit = false;
        
        scanner.nextLine(); // consumes new line
        System.out.print("Enter the description of your payment: ");
        String description = scanner.nextLine();
        
        
        System.out.print("Enter the vendor: ");
        String vendor = scanner.nextLine();
        
        System.out.print("Enter the dollar amount: $");
        
        // checks to see if the user put a float value or not.
        if(scanner.hasNextFloat()) {
            float dollarAmount = scanner.nextFloat();
            scanner.nextLine();
            
            LocalDateTime     dateTime  = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String            date      = dateTime.format(formatter);
            
            Transaction payment = new Transaction(date, description, vendor, dollarAmount);
            
            System.out.println("\n****************************************  Transactions  *********************************************");
            System.out.println("Date           Time          Description                    Vendor               Amount");
            System.out.println("------------------------------------------------------------------------------------------------------");
            System.out.println(transactionDao.create(payment, isPayment, isDeposit));
        } else {
            scanner.nextLine();
            System.out.println("\nYou have entered an incorrect input type. Please try again.");
        }
        
        
    }
    
    public static void displayTransactions(List<Transaction> transactions) {
        
        System.out.println("\n****************************************  Transactions  *********************************************");
        System.out.println("Date           Time          Description                    Vendor               Amount");
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
