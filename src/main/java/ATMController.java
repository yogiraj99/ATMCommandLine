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

    public void loginCustomer(String customerName) throws DifferentCustomerLoggedInException {
        Customer customer = atm.loginCustomer(customerName);
        display.sayHello(customer);
    }

    public void logoutCustomer(String customerName) throws CustomerNotLoggedInException, DifferentCustomerLoggedInException {
        Customer customer = atm.logoutCustomer(customerName);
        display.sayGoodBye(customer);
    }

    public int depositMoney(int moneyToDeposit) {
        try {
            return atm.deposit(moneyToDeposit);
        } catch (CustomerNotLoggedInException e) {
            warnCustomerNotLoggedIn("deposit");
        } catch (InvalidAmountException e) {
            warnInvalidAmount("deposit");
        }
        return 0;
    }

    public int withdrawMoney(int amountToWithdraw) {
        try {
            return atm.withdraw(amountToWithdraw);
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
            atm.transferMoney(amountToTransfer, transferTo);
        } catch (InvalidAmountException e) {
            warnInvalidAmount("transfer");
        } catch (CustomerNotLoggedInException e) {
            warnCustomerNotLoggedIn("transfer");
        } catch (NotEnoughBalanceException e) {
            logNotEnoughBalance("transfer");
        } catch (CustomerDoesNotExistException e) {
            log.warn("[ATMController] Could not transfer money as customer does not exist");
        }
    }
}
