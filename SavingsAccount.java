package AtmSystemManagementProject.entity;

public class SavingsAccount extends Account {

    public SavingsAccount(int accountNumber, String name, double balance) {
        super(accountNumber, name, balance);
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }
}
