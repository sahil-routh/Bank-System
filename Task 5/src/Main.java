import java.util.Scanner;
import java.util.Optional;

public class Main {
    private static final Bank bank = new Bank();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        System.out.println("Welcome to SimpleBank (demo)");
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": createAccountFlow(); break;
                case "2": depositFlow(); break;
                case "3": withdrawFlow(); break;
                case "4": checkBalanceFlow(); break;
                case "5": printHistoryFlow(); break;
                case "6": listAccounts(); break;
                case "0":
                    running = false;
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1. Create account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Check balance");
        System.out.println("5. Print transaction history");
        System.out.println("6. List all accounts");
        System.out.println("0. Exit");
        System.out.print("> ");
    }

    private static void createAccountFlow() {
        System.out.print("Account holder name: ");
        String name = sc.nextLine().trim();
        System.out.print("Initial deposit (0 for none): ");
        double init = readDouble();
        Account acc = bank.createAccount(name, init);
        System.out.println("Account created: " + acc.getAccountNumber());
    }

    private static void depositFlow() {
        System.out.print("Account number: ");
        String accNum = sc.nextLine().trim();
        System.out.print("Amount to deposit: ");
        double amt = readDouble();
        boolean ok = bank.depositTo(accNum, amt);
        System.out.println(ok ? "Deposit successful." : "Deposit failed (check account number or amount).");
    }

    private static void withdrawFlow() {
        System.out.print("Account number: ");
        String accNum = sc.nextLine().trim();
        System.out.print("Amount to withdraw: ");
        double amt = readDouble();
        boolean ok = bank.withdrawFrom(accNum, amt);
        System.out.println(ok ? "Withdrawal successful." : "Withdrawal failed (insufficient funds or bad input).");
    }

    private static void checkBalanceFlow() {
        System.out.print("Account number: ");
        String accNum = sc.nextLine().trim();
        Optional<Account> opt = bank.findAccount(accNum);
        if (opt.isPresent()) {
            System.out.printf("Balance: %.2f%n", opt.get().getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void printHistoryFlow() {
        System.out.print("Account number: ");
        String accNum = sc.nextLine().trim();
        Optional<Account> opt = bank.findAccount(accNum);
        if (opt.isPresent()) {
            opt.get().printTransactionHistory();
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void listAccounts() {
        System.out.println("All accounts:");
        for (Account a : bank.getAllAccounts()) {
            System.out.println("  " + a);
        }
    }

    private static double readDouble() {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Enter again: ");
            }
        }
    }
}
