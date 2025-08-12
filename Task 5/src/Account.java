import java.util.ArrayList;
import java.util.List;

public class Account {
    private final String accountNumber;
    private final String accountHolderName;
    private double balance;
    private final List<Transaction> transactionHistory;

    public Account(String accountNumber, String accountHolderName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = Math.max(0.0, initialDeposit);
        this.transactionHistory = new ArrayList<>();
        if (initialDeposit > 0) {
            transactionHistory.add(new Transaction("DEPOSIT", initialDeposit));
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public synchronized double getBalance() {
        return balance;
    }

    public synchronized boolean deposit(double amount) {
        if (amount <= 0) return false;
        balance += amount;
        transactionHistory.add(new Transaction("DEPOSIT", amount));
        return true;
    }

    public synchronized boolean withdraw(double amount) {
        if (amount <= 0) return false;
        if (amount > balance) return false; // insufficient funds
        balance -= amount;
        transactionHistory.add(new Transaction("WITHDRAW", amount));
        return true;
    }

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory); // defensive copy
    }

    public void printTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        System.out.println("Transaction history for account " + accountNumber + ":");
        for (Transaction t : transactionHistory) {
            System.out.println("  " + t);
        }
    }

    @Override
    public String toString() {
        return String.format("Account[%s] %s - Balance: %.2f", accountNumber, accountHolderName, balance);
    }
}
