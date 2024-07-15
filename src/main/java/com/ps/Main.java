package com.ps;

import com.ps.DAOs.TransactionDao;
import org.apache.commons.dbcp2.BasicDataSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static TransactionDao transactionDao;

    public static void main(String[] args) {
        display(args);
    }
    public static void init(String[] args){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/AccountingLedger");
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUsername(args[0]);
        basicDataSource.setPassword(args[1]);

        transactionDao = new TransactionDao(basicDataSource);

    }
    public static void display(String[] args){
        init(args);
        Scanner scanner = new Scanner(System.in);
        Ledger ledger = new Ledger();
        int userIn;
        do {
            System.out.println("\nWelcome to HAMM Accounting. Please select an option: ");
            System.out.println("\n\t1) Add deposit");
            System.out.println("\t2) Make Payment");
            System.out.println("\t3) Display Ledger");
            System.out.println("\t4) Exit");
            userIn = scanner.nextInt();

            switch (userIn) {
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
                    displayLedgerMenu(scanner, ledger);
                    break;
                case 4:
                    System.out.println("Thank you for banking with us.");
                    break;
                default:
                    System.out.println("Command not found.");
            }
        } while (userIn != 4);
    }


    //method to display ledger menu
    private static void displayLedgerMenu(Scanner scanner, Ledger ledger) {
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
            switch (ledgerCommand) {
                case 1:
                    //display all entries
                    ArrayList<Transaction> allTransactions = ledger.displayAllTransactions();
                    displayTransactions(allTransactions);
                    break;
                case 2:
                    // display deposits
                    ArrayList<Transaction> onlyDepositsTransactions = ledger.displayOnlyDeposits();
                    displayTransactions(onlyDepositsTransactions);
                    break;
                case 3:
                    //display payments
                    ArrayList<Transaction> onlyPaymentsTransactions = ledger.displayOnlyPayments();
                    displayTransactions(onlyPaymentsTransactions);
                    break;
                case 4:
                    //reports menu
                    displayReportsMenu(scanner, ledger);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid option.");

            }
        } while (ledgerCommand != 5);

    }

    //method to display reports menu
    private static void displayReportsMenu(Scanner scanner, Ledger ledger) {
        int reportCommand;
        do {
            System.out.println("\nSearch Reports by: ");
            System.out.println("\n\t1) Month to Date: ");
            System.out.println("\t2) Previous Month;");
            System.out.println("\t3) Year to Date");
            System.out.println("\t4) Previous Year");
            System.out.println("\t5) Vendor");
            System.out.println("\t6) Custom Search");
            System.out.println("\t7) Back");
            reportCommand = scanner.nextInt();
            scanner.nextLine();//consume new line;

            switch (reportCommand) {
                case 1:
                    //month to date
                    LocalDate currentDate = LocalDate.now();
                    LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
                    LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

                    ArrayList<Transaction> monthToDateTransactions = ledger.filterTransactionsByDateRange(firstDayOfMonth, lastDayOfMonth);

                    displayTransactions(monthToDateTransactions);
                    break;
                case 2:
                    // previous month
                    LocalDate previousMonthFirstDay = LocalDate.now().minusMonths(1).withDayOfMonth(1);
                    LocalDate previousMonthLastDay = previousMonthFirstDay.withDayOfMonth(previousMonthFirstDay.lengthOfMonth());

                    ArrayList<Transaction> previousMonthTransactions = ledger.filterTransactionsByDateRange(previousMonthFirstDay, previousMonthLastDay);

                    displayTransactions(previousMonthTransactions);
                    break;
                case 3:
                    //year to date
                    LocalDate currentYearFirstDay = LocalDate.now().withDayOfYear(1);
                    LocalDate currentYearLastDay = LocalDate.now().plusYears(1).withDayOfYear(1).minusDays(1);

                    ArrayList<Transaction> yearToDateTransactions = ledger.filterTransactionsByDateRange(currentYearFirstDay, currentYearLastDay);

                    displayTransactions(yearToDateTransactions);
                    break;
                case 4:
                    LocalDate previousYearFirstDay = LocalDate.now().minusYears(1).withDayOfYear(1);
                    LocalDate previousYearLastDay = LocalDate.now().withDayOfYear(1).minusDays(1);

                    ArrayList<Transaction> previousYearTransactions = ledger.filterTransactionsByDateRange(previousYearFirstDay, previousYearLastDay);

                    displayTransactions(previousYearTransactions);
                    //previous year
                    break;
                case 5:
                    System.out.println("Enter vendor name: ");

                    String vendorName = scanner.nextLine();
                    ArrayList<Transaction> vendorTransactions = ledger.searchByVendor(vendorName);
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

                            if(!startDateInput.isEmpty() && endDateInput.isEmpty()) {

                                LocalDate startDate = LocalDate.parse(startDateInput);
                                ArrayList<Transaction> startDateList = ledger.searchByOneDate(startDate);

                                displayTransactions(startDateList);
                                break;

                            } else if(startDateInput.isEmpty() && !endDateInput.isEmpty()) {

                                LocalDate endDate = LocalDate.parse(endDateInput);
                                ArrayList<Transaction> endDateList = ledger.searchByOneDate(endDate);

                                displayTransactions(endDateList);
                                break;

                            } else if (!startDateInput.isEmpty() && !endDateInput.isEmpty()) {

                                LocalDate startDate = LocalDate.parse(startDateInput);
                                LocalDate endDate = LocalDate.parse(endDateInput);
                                ArrayList<Transaction> bothDatesList = ledger.searchByDates(startDate, endDate);

                                displayTransactions(bothDatesList);
                                break;
                            }

                        System.out.println("Enter description: ");
                        String descriptionCustomSearch = scanner.nextLine().trim();

                            if (!descriptionCustomSearch.isEmpty()) {

                                ArrayList<Transaction> descriptionList = ledger.searchByDescription(descriptionCustomSearch);
                                displayTransactions(descriptionList);
                                break;
                            }

                        System.out.println("Enter vendor name: ");
                        String vendorCustomSearch = scanner.nextLine().trim();

                            if (!vendorCustomSearch.isEmpty()) {

                                ArrayList<Transaction> vendorList = ledger.searchByVendor(vendorCustomSearch);
                                displayTransactions(vendorList);
                                break;
                            }

                        System.out.println("Enter amount: ");
                        Float amountCustomSearch = scanner.nextFloat();
                        scanner.nextLine();
                            if (amountCustomSearch != null) {

                                ArrayList<Transaction> amountList = ledger.searchByAmount(amountCustomSearch);
                                displayTransactions(amountList);
                                break;
                            }
                    break;

                case 7:
                    break;
                default:
                    System.out.println("Invalid option.");

            }
        } while (reportCommand != 7);
    }

    // method to receive deposit inputs
    private static String[] addDeposit(Scanner scanner) {


        boolean isPayment = false; // use for transactionDao.create()
        boolean isDeposit = true; // use for transactionDao.create()

        System.out.print("Enter the description of your deposit: ");
        String description = scanner.nextLine();


        System.out.print("Enter the vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter the dollar amount: $");

        // checks to see if the user put a double value or not.
        if (scanner.hasNextDouble()) {
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

        System.out.print("Enter the description of your payment: ");
        String description = scanner.nextLine();


        System.out.print("Enter the vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter the dollar amount: $");

        // checks to see if the user put a double value or not.
        if (scanner.hasNextDouble()) {
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
        System.out.println("Date           Time          Description                    Vendor               Amount");
        System.out.println("------------------------------------------------------------------------------------------------------");

        for(Transaction transaction: transactions) {

            String transactionDate = transaction.getDate().toString();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = transactionDate.formatted(dateFormatter);

            System.out.printf("~ %-12s %-30s %-20s %-10.2f\n",

                    formattedDate,
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










