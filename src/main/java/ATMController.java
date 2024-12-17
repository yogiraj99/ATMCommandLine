import exception.DifferentUserLoggedInException;
import exception.InvalidAmountException;
import exception.NotEnoughBalanceException;
import exception.UserNotLoggedInException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
public class ATMController {

    private final ATM atm;
    private final Display display;

    public void loginUser(String username) throws DifferentUserLoggedInException {
        User user = atm.loginUser(username);
        display.sayHello(user);
    }

    public void logoutUser(String username) throws UserNotLoggedInException, DifferentUserLoggedInException {
        User user = atm.logoutUser(username);
        display.sayGoodBye(user);
    }

    public int depositMoney(int moneyToDeposit) {
        try {
            return atm.deposit(moneyToDeposit);
        } catch (UserNotLoggedInException e) {
            warnUserNotLoggedIn();
        } catch (InvalidAmountException e) {
            warnInvalidAmount();
        }
        return 0;
    }

    private void warnUserNotLoggedIn() {
        log.warn("[ATMController] Could not deposit money as user is not logged in");
    }

    private static void warnInvalidAmount() {
        log.warn("[ATMController] Could not deposit money as amount is invalid");
    }

    public int withdrawMoney(int amountToWithdraw) {
        try {
            return atm.withdraw(amountToWithdraw);
        } catch (UserNotLoggedInException e) {
            warnUserNotLoggedIn();
        } catch (InvalidAmountException e) {
            warnInvalidAmount();
        } catch (NotEnoughBalanceException e) {
            log.warn("[ATMController] Could not deposit money as user does not have enough balance");
        }
        return 0;
    }


}
