import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class Main {

    public static void main(String[] args) {
        ATM atm = new ATM();
        Display display = new Display(System.out);
        ATMController atmController = new ATMController(atm, display);
        String commandInfo = """
                ## Commands
                * `login [name]` - Logs in as this customer and creates the customer if not exist
                * `deposit [amount]` - Deposits this amount to the logged in customer
                * `withdraw [amount]` - Withdraws this amount from the logged in customer
                * `transfer [target] [amount]` - Transfers this amount from the logged in customer to the target customer
                * `logout` - Logs out of the current customer
                * `exit` - Exits program
                """;
        System.out.println(commandInfo);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.next();
            switch (command) {
                case "exit":
                    System.exit(0);
                    break;
                case "login":
                    String customerToLogin = scanner.next();
                    atmController.loginCustomer(customerToLogin);
                    break;
                case "logout":
                    atmController.logoutCustomer();
                    System.out.println();
                    break;
                case "deposit":
                    int amountToDeposit = scanner.nextInt();
                    atmController.depositMoney(amountToDeposit);
                    break;
                case "withdraw":
                    int amountToWithdraw = scanner.nextInt();
                    atmController.withdrawMoney(amountToWithdraw);
                    break;
                case "transfer":
                    String transferTo = scanner.next();
                    int amountToTransfer = scanner.nextInt();
                    atmController.transferMoney(amountToTransfer, transferTo);
                    break;
                default:
                    log.warn("Invalid command please input valid command");
            }
        }
    }
}
