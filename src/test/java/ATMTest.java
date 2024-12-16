import exception.UserNotLoggedInException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {

    //login login?

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
    @Test
    void loginUserShouldGetUserWhenUserLoggingIn2NdTime() {
        String username = "ExistingUser";
        ATM atm = new ATM();
        User existingUser = atm.loginUser(username);

        assertEquals(existingUser, atm.loginUser(username));
        assertTrue(atm.getLoggedInUsers().contains(username));
    }

    //logout
    @Test
    void logoutUserShouldRemoveUserFromLoggedInUser() {
        String username = "ExistingUser";
        ATM atm = new ATM();
        atm.loginUser(username);
        assertDoesNotThrow(() -> atm.logoutUser(username));
        assertFalse(atm.getLoggedInUsers().contains(username));
    }

    @Test
    void shouldTrowWhenLoggingOutWithoutLogin() {
        String username = "ExistingUser";
        ATM atm = new ATM();
        assertThrows(UserNotLoggedInException.class, () -> atm.logoutUser(username));
    }

}