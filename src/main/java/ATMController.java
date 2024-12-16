import exception.UserNotLoggedInException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ATMController {

    private final ATM atm;
    private final Display display;

    public void loginUser(String username) {
        User user = atm.loginUser(username);
        display.sayHello(user);
    }

    public void logoutUser(String username) throws UserNotLoggedInException {
        User user = atm.logoutUser(username);
        display.sayGoodBye(user);
    }
}
