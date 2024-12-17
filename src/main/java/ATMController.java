import exception.CustomerDoesNotExistException;
import exception.DifferentCustomerLoggedInException;
import exception.InvalidAmountException;
import exception.NotEnoughBalanceException;
import exception.CustomerNotLoggedInException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
public class ATMController {

    private final ATM atm;
    private final Display display;

    private void warnCustomerNotLoggedIn(String action) {
        log.warn("[ATMController] Could not {} money as customer is not logged in", action);
    }

    private static void warnInvalidAmount(String action) {
        log.warn("[ATMController] Could not {} money as amount is invalid", action);
    }

    private static void logNotEnoughBalance(String action) {
        log.warn("[ATMController] Could not {} money as customer does not have enough balance", action);
    }

    public void loginCustomer(String customerName) {
        try {
            Customer customer = atm.loginCustomer(customerName);
            display.sayHello(customer);
            display.showBalance(customer);
        } catch (DifferentCustomerLoggedInException e) {
            log.warn("[ATMController] Could not login as different customer is logged in already");
        }
    }

    public void logoutCustomer() {
        try {
            Customer customer = atm.logoutCustomer();
            display.sayGoodBye(customer);
        } catch (CustomerNotLoggedInException e) {
            log.warn("[ATMController] Could not logout as customer has not logged in");
        } catch (DifferentCustomerLoggedInException e) {
            log.warn("[ATMController] Could not logout as different customer is logged in already");
        }
    }

    public int depositMoney(int moneyToDeposit) {
        try {
            // need to check if owed to anyone
            int balance = atm.deposit(moneyToDeposit);
            display.showBalance(balance);
            return balance;
        } catch (CustomerNotLoggedInException e) {
            warnCustomerNotLoggedIn("deposit");
        } catch (InvalidAmountException e) {
            warnInvalidAmount("deposit");
        }
        return 0;
    }

    public int withdrawMoney(int amountToWithdraw) {
        try {
            int balance = atm.withdraw(amountToWithdraw);
            display.showBalance(balance);
            return balance;
        } catch (CustomerNotLoggedInException e) {
            warnCustomerNotLoggedIn("withdraw");
        } catch (InvalidAmountException e) {
            warnInvalidAmount("withdraw");
        } catch (NotEnoughBalanceException e) {
            logNotEnoughBalance("withdraw");
        }
        return 0;
    }

    public void transferMoney(int amountToTransfer, String transferTo) {
        try {
            int balance = atm.transferMoney(amountToTransfer, transferTo);
            display.showTransferMessage(amountToTransfer,transferTo);
            display.showBalance(balance);
        } catch (InvalidAmountException e) {
            warnInvalidAmount("transfer");
        } catch (CustomerNotLoggedInException e) {
            warnCustomerNotLoggedIn("transfer");
        } catch (NotEnoughBalanceException e) {
            // transfer everything in account and owe the rest
            logNotEnoughBalance("transfer");
        } catch (CustomerDoesNotExistException e) {
            log.warn("[ATMController] Could not transfer money as customer does not exist");
        }
    }
}
