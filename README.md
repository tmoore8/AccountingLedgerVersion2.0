# Accounting Ledger

## Table of Contents
- [Overview]()
- [Main Menu]()
- [Ledger Menu]()
- [Reports Menu]()
- [Interesting Pieces of Code]()
- [Contributors]()

## Overview

The Accounting Ledger System is a command-line Java application designed to manage transactions and ledger entries for a small business or personal finance tracking. It allows users to add deposits, make payments, view the ledger, and generate reports based on different criteria such as date range or vendor.

## Features

### Main Menu

#### The Main Menu Screen is where users are prompted to:
- Make a deposit (which is reflected as a credit to their accounts)
- Make a payment (which is reflected as a debit to their accounts)
- View their accounts ledger
- Access and manage their financial goals
- Exit the application

><details>
><summary> Main Menu </summary>
>
> ![img.png](src/main/java/com/ps/images/img.png)
></details>

#### Adding a deposit

><details>
><summary> Deposits </summary>
>
> ![img_2.png](src/main/java/com/ps/images/img_2.png)
></details>

#### Payments

><details>
><summary> Making a Payment </summary>
>
>![img_3.png](src/main/java/com/ps/images/img_3.png)
></details>

#### Ledger Screen

><details>
><summary> Accessing the Ledger Screen </summary>
>
> ![img_4.png](src/main/java/com/ps/images/img_4.png)
></details>

#### Exit Screen

><details>
><summary> Exiting the Application </summary>
>
> ![img_5.png](src/main/java/com/ps/images/img_5.png)
></details>

[Back to the Top]()

### Ledger Menu
From the Ledger Menu all transactions can be displayed or they can be sorted into deposits (+) or payments (-) or the report menu can be opened

><details>
><summary> All Transactions </summary>
>
> ![img_6.png](src/main/java/com/ps/images/img_6.png)
></details>

><details>
><summary> Deposits </summary>
>
> ![img_7.png](src/main/java/com/ps/images/img_7.png)
></details>

><details>
><summary> Payments </summary>
>
> ![img_8.png](src/main/java/com/ps/images/img_8.png)
></details>

[Back to the Top]()

### Reports Menu
From the Reports menu you can display reports containing all transactions within a given time period or search by vendor

><details>
><summary> Reports Menu </summary>
>
> ![img_9.png](src/main/java/com/ps/images/img_9.png)
></details>

><details>
><summary> Month to Date </summary>
>
> ![img_10.png](src/main/java/com/ps/images/img_10.png)
></details>

><details>
><summary> Search by Vendor </summary>
>
> ![img_11.png](src/main/java/com/ps/images/img_11.png)
></details>

><details>
><summary> Custom Search </summary>
>
> Image here
></details>

[Back to the Top]()

## Interesting Pieces of Code

### Hibba's Code

One piece of code that I found interesting was within the makePayment method. This piece of code checks to see if the user inputs the correct data type. If they do, the program continues, but if they not, instead of the program crashing, they are asked to try again.

><details>
><summary> Make Payment Code </summary>
>
> <img width="1055" alt="Screenshot 2024-07-16 at 7 40 23 PM" src="https://github.com/user-attachments/assets/5d79614c-705c-49b9-aeda-58621fec5b3f">

></details>



### Marshall's Code

### Tristan's Code
One piece of code I found interesting was for my menus. Instead of using nest do while loops with switches I created methods containing the submenus to keep the code more concise and readable.

><details>
><summary> Main Menu Code </summary>
>
> ![img_12.png](src/main/java/com/ps/images/img_12.png)
></details>

><details>
><summary> Ledger Menu Code </summary>
>
> ![img_13.png](src/main/java/com/ps/images/img_13.png)
></details>

><details>
><summary> Reports Menu Code </summary>
>
> ![img_14.png](src/main/java/com/ps/images/img_14.png)
></details>

### Alisha's Interesting Piece of Code

If I had to pick a piece of code that I found interesting, I would definitely have to pick where I wrote a  helper method 
which helped me display all the transactions throughout the application without needing to format and print out every time 
I need to show the user their requested transactions.

><details>
><summary> Displaying Transactions Helper Method </summary>
>
> Image here
></details>

[Back to the Top]()

## Contributors

### Hibba Afzal

- [Hibba's Github](https://github.com/hibbaafzal)

### Marshall Ellis-Gibbs

- [Marshall's Github](https://github.com/MarshallE-G)

### Tristan Moorehead 

- [Tristan's Github](https://github.com/tmoore8)

### Alisha Yu

- [Alisha's Github](https://github.com/alyu15)

[Back to the Top]()



