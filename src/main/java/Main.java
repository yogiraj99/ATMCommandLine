import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        Display display = new Display(System.out);
        ATMController atmController = new ATMController(atm, display);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.next();
            switch (command) {
                case "exit":
                    System.exit(0);
                case "login":
                    String customerToLogin = scanner.next();
                    atmController.loginCustomer(customerToLogin);
                case "logout":
                    String customerToLogout = scanner.next();
                    atmController.logoutCustomer(customerToLogout);
                case "deposit":
                    int amountToDeposit = scanner.nextInt();
                    atmController.depositMoney(amountToDeposit);
                case "withdraw":
                    int amountToWithdraw = scanner.nextInt();
                    atmController.withdrawMoney(amountToWithdraw);
                case "transfer":
                    String transferTo = scanner.next();
                    int amountToTransfer = scanner.nextInt();
                    atmController.transferMoney(amountToTransfer, transferTo);
            }
        }
    }
}
