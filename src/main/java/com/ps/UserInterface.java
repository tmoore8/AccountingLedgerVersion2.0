package com.ps;

import com.ps.DAOs.TransactionDao;
import org.apache.commons.dbcp2.BasicDataSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private static TransactionDao transactionDao;
    protected static Scanner scanner = new Scanner(System.in);

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

        welcomeMessage();

        do {
            System.out.println("\n=====================================================================================================");
            System.out.println("                                         **  Main Menu  **");
            System.out.println("                        -------------------------------------------------");
            System.out.println("                                  --  Please Select an Option  --");
            System.out.println("=====================================================================================================");

            System.out.println("\n\t~ 1) Add deposit");
            System.out.println("\t~ 2) Make Payment");
            System.out.println("\t~ 3) Display Ledger");
            System.out.println("\t~ 4) Exit Program");

            try {
                userIn = Integer.parseInt(scanner.nextLine()); // Read input as String and parse to int
                switch (userIn) {
                    case 1:
                        // Add a deposit prompt the user for deposit information and save it to CSV
                        addDeposit();

                        break;
                    case 2:
                        // Prompt user for debit information to save to file
                        makePayment();
                        break;
                    case 3:
                        // Display ledger menu
                        displayLedgerMenu();
                        break;
                    case 4:
                        exitMessage();
                        break;

                }
            } catch (Exception e) {
                commandError();
            }

        } while (userIn != 4);
    }
    //method to display ledger menu
    private static void displayLedgerMenu() {
        int ledgerCommand = 0;

        do {
            System.out.println("\n=====================================================================================================");
            System.out.println("                                        **  Ledger Menu  **");
            System.out.println("                        -------------------------------------------------");
            System.out.println("                                  --  Please Select an Option  --");
            System.out.println("=====================================================================================================");

            System.out.println("\n\t~ 1) Display all entries");
            System.out.println("\t~ 2) Display deposits");
            System.out.println("\t~ 3) Display Payments");
            System.out.println("\t~ 4) Reports Menu");
            System.out.println("\t~ 5) Back to Main Menu");

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

                }
            } catch (Exception e) {
                commandError();
            }

        } while (ledgerCommand != 5);
    }

    //method to display reports menu
    private static void displayReportsMenu(Scanner scanner) {
        int reportCommand = 0;

        do {
            System.out.println("\n=====================================================================================================");
            System.out.println("                                       **  Reports Menu  **");
            System.out.println("                        -------------------------------------------------");
            System.out.println("                                  --  Please Select an Option  --");
            System.out.println("=====================================================================================================");

            System.out.println("\n\t~ 1) Month to Date");
            System.out.println("\t~ 2) Previous Month");
            System.out.println("\t~ 3) Year to Date");
            System.out.println("\t~ 4) Previous Year");
            System.out.println("\t~ 5) Vendor");
            System.out.println("\t~ 6) Custom Search");
            System.out.println("\t~ 7) Back to Ledger Menu");

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
                        System.out.println("\n* Enter vendor name: ");
                        String vendorName = scanner.nextLine();
                        List<Transaction> vendorTransactions = transactionDao.searchByVendor(vendorName);
                        if (vendorTransactions.isEmpty()) {
                            System.out.println("                        -------------------------------------------------");
                            System.out.println("                                     -- No transactions found --");
                            System.out.println("                        -------------------------------------------------");
                        } else {
                            displayTransactions(vendorTransactions);
                        }
                        break;
                    case 6:
                        System.out.println("* Enter start date (yyyy-MM-dd): ");
                        String startDateInput = scanner.nextLine().trim();

                        System.out.println("* Enter end date (yyyy-MM-dd): ");
                        String endDateInput = scanner.nextLine().trim();

                        System.out.println("* Enter description: ");
                        String descriptionCustomSearch = scanner.nextLine().trim();

                        System.out.println("* Enter vendor name: ");
                        String vendorCustomSearch = scanner.nextLine().trim();

                        System.out.println("* Enter amount: ");
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
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid amount entered. Please enter a valid number.");
                        }
                        break;
                    case 7:
                        break;
                    default:

                }
            } catch (NumberFormatException e) {
                commandError();
            }

        } while (reportCommand != 7);
    }

    // method to receive deposit inputs
    private static void addDeposit() {

        boolean isPayment = false;
        boolean isDeposit = true;

        System.out.println("                        -------------------------------------------------");
        System.out.println("                                        -- Add a Deposit --");
        System.out.println("                        -------------------------------------------------");

        System.out.print("\n* Enter the description of your deposit: ");
        String description = scanner.nextLine();

        System.out.print("* Enter the vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("* Enter the dollar amount: $");

        // checks to see if the user put a double value or not.
        if(scanner.hasNextFloat()) {

            float dollarAmount = scanner.nextFloat();
            scanner.nextLine();

            LocalDateTime     dateTime  = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String            date      = dateTime.format(formatter);

            Transaction deposit = new Transaction(date, description, vendor, dollarAmount);

            System.out.println("\n=====================================================================================================");
            System.out.println("****************************************  Transactions  *********************************************");
            System.out.println("  Date       Time            Description                    Vendor               Amount");
            System.out.println("-----------------------------------------------------------------------------------------------------");
            System.out.println(transactionDao.create(deposit, isPayment, isDeposit));

        } else {

            scanner.nextLine();
            inputError();

        }
    }

    //method to receive payment inputs
    private static void makePayment() {

        boolean isPayment = true;
        boolean isDeposit = false;

        System.out.println("                        -------------------------------------------------");
        System.out.println("                                         -- Make a Payment --");
        System.out.println("                        -------------------------------------------------");

        System.out.print("\n* Enter the description of your payment: ");
        String description = scanner.nextLine();

        System.out.print("* Enter the vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("* Enter the dollar amount: $");

        // checks to see if the user put a float value or not.
        if(scanner.hasNextFloat()) {

            float dollarAmount = scanner.nextFloat() * -1;
            scanner.nextLine();

            LocalDateTime     dateTime  = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String            date      = dateTime.format(formatter);

            Transaction payment = new Transaction(date, description, vendor, dollarAmount);

            System.out.println("\n=====================================================================================================");
            System.out.println("****************************************  Transactions  *********************************************");
            System.out.println("  Date       Time            Description                    Vendor               Amount");
            System.out.println("-----------------------------------------------------------------------------------------------------");
            System.out.println(transactionDao.create(payment, isPayment, isDeposit));

        } else {

            scanner.nextLine();
            inputError();

        }
    }

    private static void displayTransactions(List<Transaction> transactions) {

        if(!transactions.isEmpty()) {
            System.out.println("\n=====================================================================================================");
            System.out.println("****************************************  Transactions  *********************************************");
            System.out.println("  ID   Date       Time         Description                    Vendor               Amount");
            System.out.println("-----------------------------------------------------------------------------------------------------");

            for (Transaction transaction : transactions) {

                System.out.printf("~ %-4d %-12s   %-30s %-20s %-10.2f\n",

                        transaction.getId(),
                        transaction.getDate(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount()
                );
            }
        }

        if(transactions.isEmpty()) {

            System.out.println("                        -------------------------------------------------");
            System.out.println("                                     -- No transactions found --");
            System.out.println("                        -------------------------------------------------");

        }

    }

    private static void welcomeMessage() {

        System.out.println("\n=====================================================================================================");
        String art =
                "                            __    __   ______   __       __  __       __                         \n" +
                        "                           /  |  /  | /      \\ /  \\     /  |/  \\     /  |                        \n" +
                        "                           $$ |  $$ |/$$$$$$  |$$  \\   /$$ |$$  \\   /$$ |                        \n" +
                        "                           $$ |__$$ |$$ |__$$ |$$$  \\ /$$$ |$$$  \\ /$$$ |                        \n" +
                        "                           $$    $$ |$$    $$ |$$$$  /$$$$ |$$$$  /$$$$ |                        \n" +
                        "                           $$$$$$$$ |$$$$$$$$ |$$ $$ $$/$$ |$$ $$ $$/$$ |                        \n" +
                        "                           $$ |  $$ |$$ |  $$ |$$ |$$$/ $$ |$$ |$$$/ $$ |                        \n" +
                        "                           $$ |  $$ |$$ |  $$ |$$ | $/  $$ |$$ | $/  $$ |                        \n" +
                        "                           $$/   $$/ $$/   $$/ $$/      $$/ $$/      $$/                         \n" +
                        "     ______                                                       __      __                     \n" +
                        "    /      \\                                                     /  |    /  |                    \n" +
                        "   /$$$$$$  |  _______   _______   ______   __    __  _______   _$$ |_   $$/  _______    ______  \n" +
                        "   $$ |__$$ | /       | /       | /      \\ /  |  /  |/       \\ / $$   |  /  |/       \\  /      \\ \n" +
                        "   $$    $$ |/$$$$$$$/ /$$$$$$$/ /$$$$$$  |$$ |  $$ |$$$$$$$  |$$$$$$/   $$ |$$$$$$$  |/$$$$$$  |\n" +
                        "   $$$$$$$$ |$$ |      $$ |      $$ |  $$ |$$ |  $$ |$$ |  $$ |  $$ | __ $$ |$$ |  $$ |$$ |  $$ |\n" +
                        "   $$ |  $$ |$$ \\_____ $$ \\_____ $$ \\__$$ |$$ \\__$$ |$$ |  $$ |  $$ |/  |$$ |$$ |  $$ |$$ \\__$$ |\n" +
                        "   $$ |  $$ |$$       |$$       |$$    $$/ $$    $$/ $$ |  $$ |  $$  $$/ $$ |$$ |  $$ |$$    $$ |\n" +
                        "   $$/   $$/  $$$$$$$/  $$$$$$$/  $$$$$$/   $$$$$$/  $$/   $$/    $$$$/  $$/ $$/   $$/  $$$$$$$ |\n" +
                        "                                                                                       /  \\__$$ |\n" +
                        "                                                                                       $$    $$/ \n" +
                        "                                                                                        $$$$$$/   ";

        System.out.println(art);
        System.out.println("=====================================================================================================");
        System.out.println("**********************************  Welcome to HAMM Accounting!  ************************************");

    }

    private static void exitMessage() {

        System.out.println("\n=====================================================================================================\n");
        System.out.println("                        -------------------------------------------------");
        System.out.println("                                -- Thank you for banking with us --");
        System.out.println("                        -------------------------------------------------\n");

        String art = ("                    /$$   /$$                                                           \n" +
                "                    | $$  | $$                                                           \n" +
                "                    | $$  | $$  /$$$$$$  /$$    /$$/$$$$$$         /$$$$$$              \n" +
                "                    | $$$$$$$$ |____  $$|  $$  /$$/$$__  $$       |____  $$             \n" +
                "                    | $$__  $$  /$$$$$$$ \\  $$/$$/ $$$$$$$$        /$$$$$$$             \n" +
                "                    | $$  | $$ /$$__  $$  \\  $$$/| $$_____/       /$$__  $$             \n" +
                "                    | $$  | $$|  $$$$$$$   \\  $/ |  $$$$$$$      |  $$$$$$$             \n" +
                "                    |__/  |__/ \\_______/    \\_/   \\_______/       \\_______/             \n" +
                "                                                                                   \n" +
                "           /$$   /$$ /$$                           /$$$$$$$                     /$$\n" +
                "          | $$$ | $$|__/                          | $$__  $$                   | $$\n" +
                "          | $$$$| $$ /$$  /$$$$$$$  /$$$$$$       | $$  \\ $$ /$$$$$$  /$$   /$$| $$\n" +
                "          | $$ $$ $$| $$ /$$_____/ /$$__  $$      | $$  | $$|____  $$| $$  | $$| $$\n" +
                "          | $$  $$$$| $$| $$      | $$$$$$$$      | $$  | $$ /$$$$$$$| $$  | $$|__/\n" +
                "          | $$\\  $$$| $$| $$      | $$_____/      | $$  | $$/$$__  $$| $$  | $$    \n" +
                "          | $$ \\  $$| $$|  $$$$$$$|  $$$$$$$      | $$$$$$$/  $$$$$$$|  $$$$$$$ /$$\n" +
                "          |__/  \\__/|__/ \\_______/ \\_______/      |_______/ \\_______/ \\____  $$|__/\n" +
                "                                                                      /$$  | $$    \n" +
                "                                                                     |  $$$$$$/    \n" +
                "                                                                      \\______/     ");

        System.out.println(art);
        System.out.println("\n=====================================================================================================");

    }

    private static void inputError() {

        System.out.println("\n                        -------------------------------------------------");
        System.out.println("                          -- You have entered an incorrect input type --");
        System.out.println("                                      -- Please try again --");
        System.out.println("                        -------------------------------------------------");

    }

    private static void commandError() {

        System.out.println("                        -------------------------------------------------");
        System.out.println("                                     -- Command Not Found --");
        System.out.println("                         -- Please enter in one of the listed options --");
        System.out.println("                        -------------------------------------------------");

    }

}