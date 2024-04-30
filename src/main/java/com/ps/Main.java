package com.ps;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Ledger ledger = new Ledger;
        //
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
                    System.out.println("Enter deposit information: ");
                    break;
                case 2:
                    // prompt user for debit information to save to file
                    break;
                case 3:
                    //display ledger menu
                    System.out.println("Ledger: ");
                    int ledgerCommand;
                    do {
                        System.out.println("\t1) Display all entries: ");
                        System.out.println("\t2) Display deposits;");
                        System.out.println("\t3) Display Payments");
                        System.out.println("\t4) Reports Menu");
                        System.out.println("\t5) Back");
                        ledgerCommand = scanner.nextInt();
                        switch (ledgerCommand) {
                            case 1:
                                //display all entries
                                break;
                            case 2:
                                // display deposits
                                break;
                            case 3:
                                //display payments
                                break;
                            case 4:
                                //reports menu
                                int reportCommand;
                                do {
                                    System.out.println("Search Reports by: ");
                                    System.out.println("\t1) Month to Date: ");
                                    System.out.println("\t2) Previous Month;");
                                    System.out.println("\t3) Year to Date");
                                    System.out.println("\t4) Previous Year");
                                    System.out.println("\t5) Vendor");
                                    System.out.println("\t6) Back to Cart");
                                    reportCommand = scanner.nextInt();

                                    switch (reportCommand) {
                                        case 1:
                                            //month to date
                                            break;
                                        case 2:
                                            // previous month
                                            break;
                                        case 3:
                                            //year to date
                                            break;
                                        case 4:
                                            //previous year
                                            break;
                                        case 5:
                                            break;
                                        default:
                                            System.out.println("Invalid option.");

                                    }
                                } while (reportCommand != 6);
                                break;
                            case 5:
                                break;
                            default:
                                System.out.println("Invalid option.");

                        }
                    } while (ledgerCommand != 5);
                    System.out.println("Command not found.");
                    break;
                case 4:
                    System.out.println("Thank you for banking with us.");
                    break;
                default:
                    System.out.println("Command not found.");
            }
        } while (userIn != 4);
    }
}











