import exception.UserNotLoggedInException;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;

public class ATM {

    private final HashMap<String, User> users;
    @Getter
    private final HashSet<String> loggedInUsers;

    ATM() {
        this.users = new HashMap<>();
        this.loggedInUsers = new HashSet<>();
    }

    public User loginUser(String username) {
        if (this.users.containsKey(username)) {
            return getUser(username);
        }
        return createNewUser(username);
    }

    private User getUser(String username) {
        loggedInUsers.add(username);
        return this.users.get(username);
    }

    private User createNewUser(String username) {
        User user = new User(username);
        loggedInUsers.add(username);
        this.users.put(username, user);
        return user;
    }

    public boolean logoutUser(String username) throws UserNotLoggedInException {
        if (this.loggedInUsers.contains(username)) {
            return this.loggedInUsers.remove(username);
        }
        throw new UserNotLoggedInException();
    }
}
