import exception.UserNotLoggedInException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {

    //login login?
    @Test
    void loginUserShouldGetUserWhenAlreadyLoggedInUserTryToLogin() {
        String username = "ExistingUser";
        ATM atm = new ATM();
        User existingUser = atm.loginUser(username);

        assertEquals(existingUser, atm.loginUser(username));
        assertTrue(atm.getLoggedInUsers().contains(username));
    }

    // login
    @Test
    void loginUserShouldCreateUserWhenUserLoggingIn1StTime() {
        String username = "NewUser";
        ATM atm = new ATM();
        User user = new User(username);

        assertEquals(user, atm.loginUser(username));
        assertTrue(atm.getLoggedInUsers().contains(username));
    }

    // login logout login
    @SneakyThrows
    @Test
    void loginUserShouldGetUserWhenUserLoggingIn2NdTime() {
        String username = "ExistingUser";
        ATM atm = new ATM();
        User existingUser = atm.loginUser(username);
        atm.logoutUser(username);

        assertEquals(existingUser, atm.loginUser(username));
        assertTrue(atm.getLoggedInUsers().contains(username));
    }

    //logout
    @Test
    void logoutUserShouldRemoveUserFromLoggedInUser() {
        String username = "ExistingUser";
        ATM atm = new ATM();
        atm.loginUser(username);

        User expectedUser = new User(username);
        assertDoesNotThrow(() -> assertEquals(expectedUser, atm.logoutUser(username)));
        assertFalse(atm.getLoggedInUsers().contains(username));
    }

    @Test
    void shouldTrowWhenLoggingOutWithoutLogin() {
        String username = "ExistingUser";
        ATM atm = new ATM();
        assertThrows(UserNotLoggedInException.class, () -> atm.logoutUser(username));
    }

}