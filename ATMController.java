package AtmSystemManagementProject.controller;


import AtmSystemManagementProject.service.ATMService;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ATMController {

    private ATMService service = new ATMService();

    public void start() {

        Scanner sc = new Scanner(System.in);

        ExecutorService executor =
                Executors.newFixedThreadPool(3);

        while(true) {

            System.out.println("\n===== ATM SYSTEM =====");

            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Search Account");
            System.out.println("5. Check Balance");
            System.out.println("6. Exit");

            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();

            try {

                switch(choice) {

                    case 1:

                        System.out.print("Account Number : ");
                        int accNo = sc.nextInt();

                        System.out.print("Name : ");
                        String name = sc.next();
                        
                        if(!name.matches("[a-zA-Z]+")){
                            System.out.println("Enter the name number are not allowed!");
                            break;
                        }

                        System.out.print("Initial Balance : ");
                        double balance = sc.nextDouble();

                        System.out.print("Type (Savings/Current) : ");
                        String type = sc.next();

                        service.createAccount(
                                accNo,
                                name,
                                balance,
                                type);

                        break;

                    case 2:

                        System.out.print("Account Number : ");
                        int depAcc = sc.nextInt();

                        System.out.print("Amount : ");
                        double depAmt = sc.nextDouble();

                        executor.execute(() -> {
                            try {
                                service.deposit(depAcc, depAmt);
                            } catch(Exception e) {
                                System.out.println(e.getMessage());
                            }
                        });

                        break;

                    case 3:

                        System.out.print("Account Number : ");
                        int withAcc = sc.nextInt();

                        System.out.print("Amount : ");
                        double withAmt = sc.nextDouble();

                        executor.execute(() -> {
                            try {
                                service.withdraw(withAcc, withAmt);
                            } catch(Exception e) {
                                System.out.println(e.getMessage());
                            }
                        });

                        break;

                    case 4:

                        System.out.print("Account Number : ");
                        int searchAcc = sc.nextInt();

                        service.searchAccount(searchAcc);

                        break;

                    case 5:

                        System.out.print("Account Number : ");
                        int balAcc = sc.nextInt();

                        Future<Double> future =
                                executor.submit(
                                        service.checkBalance(balAcc));

                        System.out.println(
                                "Balance : "
                                + future.get());

                        break;

                    case 6:

                        executor.shutdown();

                        System.out.println(
                                "Thank You");

                        sc.close();

                        System.exit(0);

                    default:

                        System.out.println(
                                "Invalid Choice");
                }

            }  catch(Exception e) {

                System.out.println(
                        "Invalid Input");
            }
        }
    }
}