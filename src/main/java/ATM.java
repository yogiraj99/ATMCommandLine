import exception.DifferentUserLoggedInException;
import exception.InvalidAmountException;
import exception.NotEnoughBalanceException;
import exception.UserNotLoggedInException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Objects;

public class ATM {

    // change to customers
    private final HashMap<String, User> users;
    @Getter
    private User loggedInUser;

    ATM() {
        this.users = new HashMap<>();
    }

    public User loginUser(String username) throws DifferentUserLoggedInException {
        if (Objects.isNull(this.loggedInUser)) {
            return this.users.containsKey(username) ? getUser(username) : createNewUser(username);
        }
        if (Objects.equals(this.loggedInUser.getUsername(), username)) {
            return this.loggedInUser;
        }
        throw new DifferentUserLoggedInException();

    }

    private User getUser(String username) {
        User user = this.users.get(username);
        this.loggedInUser = user;
        return user;
    }

    private User createNewUser(String username) {
        User user = new User(username);
        this.loggedInUser = user;
        this.users.put(username, user);
        return user;
    }

    public User logoutUser(String username) throws UserNotLoggedInException, DifferentUserLoggedInException {
        if (Objects.isNull(this.loggedInUser)) {
            throw new UserNotLoggedInException();
        }
        if (Objects.equals(this.loggedInUser.getUsername(), username)) {
            this.loggedInUser = null;
            return this.users.get(username);
        }
        throw new DifferentUserLoggedInException();
    }

    public int deposit(int amountToDeposit) throws UserNotLoggedInException, InvalidAmountException {
        if (Objects.isNull(this.loggedInUser)) {
            throw new UserNotLoggedInException();
        }
        if (amountToDeposit < 1) {
            throw new InvalidAmountException();
        }
        return this.loggedInUser.increaseBalance(amountToDeposit);
    }

    public int withdraw(int amountToWithdraw) throws UserNotLoggedInException, InvalidAmountException, NotEnoughBalanceException {
        if (Objects.isNull(this.loggedInUser)) {
            throw new UserNotLoggedInException();
        }
        if (amountToWithdraw < 1) {
            throw new InvalidAmountException();
        }
        if (amountToWithdraw > this.loggedInUser.getBalance()) {
            throw new NotEnoughBalanceException();
        }
        return this.loggedInUser.decreaseBalance(amountToWithdraw);
    }
}
