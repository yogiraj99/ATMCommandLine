import exception.DifferentUserLoggedInException;
import exception.UserNotLoggedInException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ATMTest {

    @Test
    void loginUserShouldGetUserWhenAlreadyLoggedInUserTryToLogin() {
        String username = "ExistingUser";
        ATM atm = new ATM();
        assertDoesNotThrow(() -> {
            User existingUser = atm.loginUser(username);
            assertEquals(existingUser, atm.loginUser(username));
        });

        assertEquals(atm.getLoggedInUser().getUsername(), username);
    }

    @Test
    void loginUserShouldCreateUserWhenUserLoggingIn1StTime() {
        String username = "NewUser";
        ATM atm = new ATM();
        User user = new User(username);
        assertDoesNotThrow(() -> assertEquals(user, atm.loginUser(username)));

        assertEquals(atm.getLoggedInUser().getUsername(), username);
    }

    @Test
    void loginUserShouldGetUserWhenUserLoggingIn2NdTime() {
        String username = "ExistingUser";
        ATM atm = new ATM();

        assertDoesNotThrow(() -> {
            User existingUser = atm.loginUser(username);
            atm.logoutUser(username);
            assertEquals(existingUser, atm.loginUser(username));
            assertEquals(atm.getLoggedInUser().getUsername(), username);
        });

    }

    @Test
    void logoutUserShouldRemoveUserFromLoggedInUser() {
        String username = "ExistingUser";
        ATM atm = new ATM();
        assertDoesNotThrow(() -> atm.loginUser(username));

        User expectedUser = new User(username);
        assertDoesNotThrow(() -> assertEquals(expectedUser, atm.logoutUser(username)));
        assertNull(atm.getLoggedInUser());
    }

    @Test
    void shouldTrowWhenLoggingOutWithoutLogin() {
        String username = "ExistingUser";
        ATM atm = new ATM();
        assertThrows(UserNotLoggedInException.class, () -> atm.logoutUser(username));
    }

    @Test
    void shouldTrowWhenDifferentUserTriesToLogout() {
        String username = "ExistingUser";
        String differentUsername = "DifferentUser";
        ATM atm = new ATM();
        assertDoesNotThrow(() -> atm.loginUser(username));
        assertThrows(DifferentUserLoggedInException.class, () -> atm.logoutUser(differentUsername));
    }

    @Test
    void shouldTrowWhenDifferentUserTriesToLoginBeforeExistingUserLoggingOut() {
        String username = "ExistingUser";
        String differentUsername = "DifferentUser";
        ATM atm = new ATM();
        assertDoesNotThrow(() -> atm.loginUser(username));
        assertThrows(DifferentUserLoggedInException.class, () -> atm.loginUser(differentUsername));
    }

}