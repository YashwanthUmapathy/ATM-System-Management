package AtmSystemManagementProject.service;

import AtmSystemManagementProject.entity.Account;
import AtmSystemManagementProject.entity.CurrentAccount;
import AtmSystemManagementProject.entity.SavingsAccount;
import AtmSystemManagementProject.exception.InsufficientBalanceException;
import AtmSystemManagementProject.exception.InvalidAmountException;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class ATMService {

    private Map<Integer, Account> accounts = new ConcurrentHashMap<>();

    public void createAccount(
            int accNo,
            String name,
            double balance,
            String type) {

        Account account;

        if(type.equalsIgnoreCase("savings")) {
            account = new SavingsAccount(accNo, name, balance);
        } else {
            account = new CurrentAccount(accNo, name, balance);
        }

        accounts.put(accNo, account);

        System.out.println("Account Created Successfully");
    }

    public synchronized void deposit(int accNo, double amount)
            throws InvalidAmountException {

        if(amount <= 0) {
            throw new InvalidAmountException(
                    "Invalid Deposit Amount");
        }

        Account acc = accounts.get(accNo);

        if(acc != null) {
            acc.deposit(amount);
            System.out.println("Amount Deposited Successfully");
        } else {
            System.out.println("Account Not Found");
        }
    }

    public synchronized void withdraw(int accNo, double amount)
            throws InsufficientBalanceException,
            InvalidAmountException {

        if(amount <= 0) {
            throw new InvalidAmountException(
                    "Invalid Withdraw Amount");
        }

        Account acc = accounts.get(accNo);

        if(acc != null) {

            if(acc.getBalance() < amount) {
                throw new InsufficientBalanceException(
                        "Insufficient Balance");
            }

            acc.withdraw(amount);

            System.out.println("Withdrawal Successful");

        } else {
            System.out.println("Account Not Found");
        }
    }

    public void searchAccount(int accNo) {

        Account acc = accounts.get(accNo);

        if(acc != null) {

            System.out.println("Account Number : "
                    + acc.getAccountNumber());

            System.out.println("Name : "
                    + acc.getName());

            System.out.println("Balance : "
                    + acc.getBalance());

            System.out.println("Type : "
                    + acc.getAccountType());

        } else {
            System.out.println("Account Not Found");
        }
    }

    public Callable<Double> checkBalance(int accNo) {

        return () -> {

            Account acc = accounts.get(accNo);

            if(acc != null) {
                return acc.getBalance();
            }

            return 0.0;
        };
    }
}