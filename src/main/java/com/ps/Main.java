package com.ps;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Ledger ledger = new Ledger();
        int userIn;
        do {
            System.out.println("Welcome to Moorehead Accounting. Please select an option: ");
            System.out.println("\t1) Add deposit");
            System.out.println("\t2) Make Payment");
            System.out.println("\t3) Display Ledger");
            System.out.println("\t4) Exit");
            userIn = scanner.nextInt();

            switch (userIn) {
                case 1:
                    //Add a deposit prompt the user for a deposit information and save it to CSV
                    addDeposit(scanner, ledger);
                    break;
                case 2:
                    // prompt user for debit information to save to file
                    makePayment(scanner, ledger);
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
        System.out.println("Ledger: ");
        int ledgerCommand;
        do {
            System.out.println("\t1) Display all entries: ");
            System.out.println("\t2) Display deposits;");
            System.out.println("\t3) Display Payments");
            System.out.println("\t4) Reports Menu");
            System.out.println("\t5) Back");
            ledgerCommand = scanner.nextInt();
            scanner.nextLine();//consume new line
            switch (ledgerCommand) {
                case 1:
                    //display all entries
                    ledger.displayAllTransactions();
                    break;
                case 2:
                    // display deposits
                    ledger.displayOnlyDeposits();
                    break;
                case 3:
                    //display payments

                    ledger.displayOnlyPayments();
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
            System.out.println("Search Reports by: ");
            System.out.println("\t1) Month to Date: ");
            System.out.println("\t2) Previous Month;");
            System.out.println("\t3) Year to Date");
            System.out.println("\t4) Previous Year");
            System.out.println("\t5) Vendor");
            System.out.println("\t6) Back");
            reportCommand = scanner.nextInt();
            scanner.nextLine();//consume new line;

            switch (reportCommand) {
                case 1:
                    //month to date
                    LocalDate currentDate = LocalDate.now();
                    LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
                    LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

                    ArrayList<Transaction> monthToDateTransactions = ledger.filterTransactionsByDateRange(firstDayOfMonth, lastDayOfMonth);

                    System.out.println("Month to Date Transactions:");
                    for (Transaction transaction : monthToDateTransactions) {
                        System.out.println(transaction);
                    }
                    break;
                case 2:
                    // previous month
                    LocalDate previousMonthFirstDay = LocalDate.now().minusMonths(1).withDayOfMonth(1);
                    LocalDate previousMonthLastDay = previousMonthFirstDay.withDayOfMonth(previousMonthFirstDay.lengthOfMonth());

                    ArrayList<Transaction> previousMonthTransactions = ledger.filterTransactionsByDateRange(previousMonthFirstDay, previousMonthLastDay);

                    System.out.println("Previous Month Transactions:");
                    for (Transaction transaction : previousMonthTransactions) {
                        System.out.println(transaction);
                    }
                    break;
                case 3:
                    //year to date
                    // Year to Date report
                    LocalDate currentYearFirstDay = LocalDate.now().withDayOfYear(1);
                    LocalDate currentYearLastDay = LocalDate.now().plusYears(1).withDayOfYear(1).minusDays(1);

                    ArrayList<Transaction> yearToDateTransactions = ledger.filterTransactionsByDateRange(currentYearFirstDay, currentYearLastDay);

                    System.out.println("Year to Date Transactions:");
                    for (Transaction transaction : yearToDateTransactions) {
                        System.out.println(transaction);
                    }
                    break;
                case 4:
                    LocalDate previousYearFirstDay = LocalDate.now().minusYears(1).withDayOfYear(1);
                    LocalDate previousYearLastDay = LocalDate.now().withDayOfYear(1).minusDays(1);

                    ArrayList<Transaction> previousYearTransactions = ledger.filterTransactionsByDateRange(previousYearFirstDay, previousYearLastDay);

                    System.out.println("Previous Year Transactions:");
                    for (Transaction transaction : previousYearTransactions) {
                        System.out.println(transaction);
                    }
                    //previous year
                    break;
                case 5:
                    System.out.println("Enter vendor name: ");

                    String vendorName = scanner.nextLine();
                    ArrayList<Transaction> vendorTransactions = ledger.searchByVendor(vendorName);
                    if (vendorTransactions.isEmpty()) {
                        System.out.println("No transactions for vendor: " + vendorName);
                    } else {
                        System.out.println("Transactions for vendor " + vendorName + ": ");
                        for (Transaction transaction : vendorTransactions) {
                            System.out.println(transaction);
                        }
                    }
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Invalid option.");

            }
        } while (reportCommand != 6);
    }

    // method to receive deposit inputs
    private static void addDeposit(Scanner scanner, Ledger ledger) {

        LocalDate date = LocalDate.now();

        LocalTime time = LocalTime.now();
        scanner.nextLine();

        System.out.print("Enter deposit description: ");
        String description = scanner.nextLine();

        System.out.print("Enter deposit vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter deposit amount: ");
        float amount = scanner.nextFloat();
        scanner.nextLine();//consume new line

        ledger.addDepositToFile(date, time, description, vendor, amount);
        System.out.println("Deposit added successfully.");
    }

    //method to receive payment inputs
    private static void makePayment(Scanner scanner, Ledger ledger) {

        LocalDate date = LocalDate.now();

        LocalTime time = LocalTime.now();
        scanner.nextLine();

        System.out.print("Enter payment description: ");
        String description = scanner.nextLine();

        System.out.print("Enter payment vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter payment amount: ");
        float amount = scanner.nextFloat();
        scanner.nextLine();//consume new line

        ledger.makePaymentToFile(date, time, description, vendor, amount);
        System.out.println("Payment made successfully.");
    }
}











