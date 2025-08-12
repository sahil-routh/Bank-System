import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Bank {
    private final List<Account> accounts = new ArrayList<>();
    private int nextAccountNumber = 1001; // simple incremental generator

    public synchronized Account createAccount(String holderName, double initialDeposit) {
        String accNum = String.valueOf(nextAccountNumber++);
        Account acc = new Account(accNum, holderName, initialDeposit);
        accounts.add(acc);
        return acc;
    }

    public synchronized Optional<Account> findAccount(String accountNumber) {
        return accounts.stream()
                .filter(a -> a.getAccountNumber().equals(accountNumber))
                .findFirst();
    }

    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts); // defensive copy
    }

    // Convenience methods that operate via account number
    public boolean depositTo(String accountNumber, double amount) {
        Optional<Account> opt = findAccount(accountNumber);
        return opt.map(a -> a.deposit(amount)).orElse(false);
    }

    public boolean withdrawFrom(String accountNumber, double amount) {
        Optional<Account> opt = findAccount(accountNumber);
        return opt.map(a -> a.withdraw(amount)).orElse(false);
    }
}
