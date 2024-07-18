# Accounting Ledger

## Table of Contents
- [Overview](https://github.com/tmoore8/AccountingLedgerVersion2.0?tab=readme-ov-file#overview)
- [Main Menu](https://github.com/tmoore8/AccountingLedgerVersion2.0?tab=readme-ov-file#main-menu)
- [Ledger Menu](https://github.com/tmoore8/AccountingLedgerVersion2.0?tab=readme-ov-file#ledger-menu)
- [Reports Menu](https://github.com/tmoore8/AccountingLedgerVersion2.0?tab=readme-ov-file#reports-menu)
- [Interesting Pieces of Code](https://github.com/tmoore8/AccountingLedgerVersion2.0?tab=readme-ov-file#interesting-pieces-of-code)
- [Contributors](https://github.com/tmoore8/AccountingLedgerVersion2.0?tab=readme-ov-file#contributors)

## Overview

The Accounting Ledger System is a command-line Java application designed to manage transactions and ledger entries for a small business or personal finance tracking. It allows users to add deposits, make payments, view the ledger, and generate reports based on different criteria such as date range or vendor.

## Features

### Main Menu

#### The Main Menu Screen is where users are prompted to:
- Make a deposit (which is reflected as a credit to their accounts)
- Make a payment (which is reflected as a debit to their accounts)
- View their accounts ledger
- Exit the application

><details>
><summary> Main Menu </summary>
>
> ![MainMenu.JPG](src/main/java/com/ps/Images/MainMenu.JPG)
></details>

#### Adding a deposit

><details>
><summary> Adding a Deposit </summary>
>
> ![AddDeposit.JPG](src/main/java/com/ps/Images/AddDeposit.JPG)
></details>

#### Payments

><details>
><summary> Making a Payment </summary>
>
> ![MakePayment.JPG](src/main/java/com/ps/Images/MakePayment.JPG)
></details>

#### Ledger Screen

><details>
><summary> Accessing the Ledger Screen </summary>
>
> ![LedgerMenu.JPG](src/main/java/com/ps/Images/LedgerMenu.JPG)
></details>

#### Exit Screen

><details>
><summary> Exiting the Application </summary>
>
> ![ExitScreen.JPG](src/main/java/com/ps/Images/ExitScreen.JPG)
></details>

[Back to the Top]()

### Ledger Menu
#### The Ledger Screen is where users are able to:
- View all the transactions on their account
- View all deposits made on their account
- View all their payments made on their account

><details>
><summary> All Transactions </summary>
>
> ![LedgerMenu.JPG](src/main/java/com/ps/Images/LedgerMenu.JPG)
></details>

><details>
><summary> All Deposits </summary>
>
> ![AllDeposits.JPG](src/main/java/com/ps/Images/AllDeposits.JPG)
></details>

><details>
><summary> All Payments </summary>
>
> ![AllPayments.JPG](src/main/java/com/ps/Images/AllPayments.JPG)
></details>

[Back to the Top](https://github.com/tmoore8/AccountingLedgerVersion2.0?tab=readme-ov-file#table-of-contents)

### Reports Menu
#### The Reports Screen allows the user to either execute pre-defined reports or conduct a customized search.
#### These pre-defined reports enable the user to explore their transactions by:
- Month to Date
- Previous Month
- Year to Date
- Previous Year
#### The user is also able to:
- Search by vendor
- Perform a custom search
#### All these reports display both the user's deposits and payments.
><details>
><summary> Reports Menu </summary>
>
> ![ReportsMenu.JPG](src/main/java/com/ps/Images/ReportsMenu.JPG)
></details>

><details>
><summary> Month to Date </summary>
>
> ![MonthToDate.JPG](src/main/java/com/ps/Images/MonthToDate.JPG)
></details>

><details>
><summary> Previous Month </summary>
>
> ![PreviousMonth.JPG](src/main/java/com/ps/Images/PreviousMonth.JPG)
></details>

><details>
><summary> Year to Date </summary>
>
> ![YearToDate.JPG](src/main/java/com/ps/Images/YearToDate.JPG)
></details>

><details>
><summary> Previous Year </summary>
>
> ![PreviousYear.JPG](src/main/java/com/ps/Images/PreviousYear.JPG)
></details>

><details>
><summary> Search by Vendor </summary>
>
> ![VendorSearch.JPG](src/main/java/com/ps/Images/VendorSearch.JPG)
></details>

><details>
><summary> Custom Search </summary>
>
> ![CustomSearch.JPG](src/main/java/com/ps/Images/CustomSearch.JPG)
></details>

[Back to the Top](https://github.com/tmoore8/AccountingLedgerVersion2.0?tab=readme-ov-file#table-of-contents)

## Interesting Pieces of Code

### Hibba's Code

One piece of code that I found interesting was within the makePayment method. This piece of code checks to see if the user inputs the correct data type. If they do, the program continues, but if they not, instead of the program crashing, they are asked to try again.

><details>
><summary> Make Payment Code </summary>
>
> <img width="1055" alt="Screenshot 2024-07-16 at 7 40 23 PM" src="https://github.com/user-attachments/assets/5d79614c-705c-49b9-aeda-58621fec5b3f">
>
></details>

### Marshall's Code

><details>
><summary> Empty Input for Custom Search Amount Code </summary>
>
> ![MarshallInterestingPieceOfCode.PNG](src\main\java\com\ps\Images\MarshallInterestingPieceOfCode.PNG)
></details>

### Tristan's Code

><details>
><summary> Interesting Code </summary>
>
> Image here
></details>

### Alisha's Interesting Piece of Code

If I had to pick a piece of code that I found interesting, I would definitely have to pick where I wrote a  helper method 
which helped me display all the transactions throughout the application without needing to format and print out every time 
I need to show the user their requested transactions.

><details>
><summary> Display Transactions Helper Method </summary>
>
> ![displayTransactionCode.JPG](src/main/java/com/ps/Images/displayTransactionCode.JPG)
></details>

[Back to the Top](https://github.com/tmoore8/AccountingLedgerVersion2.0?tab=readme-ov-file#table-of-contents)

## Contributors

### Hibba Afzal

- [Hibba's Github](https://github.com/hibbaafzal)

### Marshall Ellis-Gibbs

- [Marshall's Github](https://github.com/MarshallE-G)

### Tristan Moorehead 

- [Tristan's Github](https://github.com/tmoore8)

### Alisha Yu

- [Alisha's Github](https://github.com/alyu15)

[Back to the Top](https://github.com/tmoore8/AccountingLedgerVersion2.0?tab=readme-ov-file#table-of-contents)