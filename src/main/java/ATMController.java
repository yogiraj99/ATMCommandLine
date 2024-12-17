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
            warnCustomerNotLoggedIn();
        } catch (InvalidAmountException e) {
            warnInvalidAmount();
        }
        return 0;
    }

    private void warnCustomerNotLoggedIn() {
        log.warn("[ATMController] Could not deposit money as customer is not logged in");
    }

    private static void warnInvalidAmount() {
        log.warn("[ATMController] Could not deposit money as amount is invalid");
    }

    public int withdrawMoney(int amountToWithdraw) {
        try {
            return atm.withdraw(amountToWithdraw);
        } catch (CustomerNotLoggedInException e) {
            warnCustomerNotLoggedIn();
        } catch (InvalidAmountException e) {
            warnInvalidAmount();
        } catch (NotEnoughBalanceException e) {
            log.warn("[ATMController] Could not deposit money as customer does not have enough balance");
        }
        return 0;
    }


}
