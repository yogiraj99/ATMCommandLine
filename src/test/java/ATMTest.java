import exception.DifferentUserLoggedInException;
import exception.InvalidAmountException;
import exception.NotEnoughBalanceException;
import exception.UserNotLoggedInException;
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
    void shouldThrowWhenLoggingOutWithoutLogin() {
        String username = "ExistingUser";
        ATM atm = new ATM();
        assertThrows(UserNotLoggedInException.class, () -> atm.logoutUser(username));
    }

    @Test
    void shouldThrowWhenDifferentUserTriesToLogout() {
        String username = "ExistingUser";
        String differentUsername = "DifferentUser";
        ATM atm = new ATM();
        assertDoesNotThrow(() -> atm.loginUser(username));
        assertThrows(DifferentUserLoggedInException.class, () -> atm.logoutUser(differentUsername));
    }

    @Test
    void shouldThrowWhenDifferentUserTriesToLoginBeforeExistingUserLoggingOut() {
        String username = "ExistingUser";
        String differentUsername = "DifferentUser";
        ATM atm = new ATM();
        assertDoesNotThrow(() -> atm.loginUser(username));
        assertThrows(DifferentUserLoggedInException.class, () -> atm.loginUser(differentUsername));
    }

    @Test
    void shouldThrowWhenTryingToDepositWithoutLogin() {
        ATM atm = new ATM();
        assertThrows(UserNotLoggedInException.class, () -> atm.deposit(80));
    }

    @Test
    void shouldDepositMoneyInUserAccount() {
        String username = "NewUser";
        int amountToDeposit = 80;

        ATM atm = new ATM();
        assertDoesNotThrow(() -> {
            atm.loginUser(username);
            atm.deposit(amountToDeposit);
            assertEquals(amountToDeposit, atm.getLoggedInUser().getBalance());
        });
    }

    @Test
    void shouldDepositMoneyMoreThanOnceForSameUser() {
        String username = "NewUser";
        int amountToDeposit = 53;

        ATM atm = new ATM();
        assertDoesNotThrow(() -> {
            atm.loginUser(username);
            atm.deposit(amountToDeposit);
            atm.deposit(amountToDeposit);
            assertEquals(2 * amountToDeposit, atm.getLoggedInUser().getBalance());
        });
    }

    @Test
    void shouldThrowWhenDepositingZeroOrNegativeMoney() {
        String username = "NewUser";
        int negativeAmount = -38;

        ATM atm = new ATM();
        assertDoesNotThrow(() -> atm.loginUser(username));
        assertThrows(InvalidAmountException.class, () -> atm.deposit(0));
        assertThrows(InvalidAmountException.class, () -> atm.deposit(negativeAmount));
    }

    @Test
    void shouldThrowWhenTryingToWithdrawWithoutLogin() {
        ATM atm = new ATM();
        assertThrows(UserNotLoggedInException.class, () -> atm.withdraw(80));
    }

    @Test
    void shouldThrowWhenWithdrawingMoneyMoreThanAccountBalance() {
        String username = "NewUser";
        int amountToWithdraw = 38;

        ATM atm = new ATM();
        assertDoesNotThrow(() -> atm.loginUser(username));
        assertThrows(NotEnoughBalanceException.class, () -> atm.withdraw(amountToWithdraw));
    }

    @Test
    void shouldThrowWhenWithdrawingZeroOrNegativeMoney() {
        String username = "NewUser";
        int negativeAmount = -38;
        int randomAmountToDeposit = 56;

        ATM atm = new ATM();
        assertDoesNotThrow(() -> {
            atm.loginUser(username);
            atm.deposit(randomAmountToDeposit);
        });
        assertThrows(InvalidAmountException.class, () -> atm.withdraw(0));
        assertThrows(InvalidAmountException.class, () -> atm.withdraw(negativeAmount));
    }

    @Test
    void shouldWithdrawMoneyFromUserAccount() {
        String username = "NewUser";
        int amountToDeposit = 80;
        int amountToWithdraw = 50;

        ATM atm = new ATM();
        assertDoesNotThrow(() -> {
            atm.loginUser(username);
            atm.deposit(amountToDeposit);
            assertEquals(amountToDeposit - amountToWithdraw, atm.withdraw(amountToWithdraw));
        });
    }

    @Test
    void shouldWithdrawMoneyMoreThanOnceFromSameUser() {
        String username = "NewUser";
        int amountToDeposit = 153;
        int amountToWithdraw = 47;

        ATM atm = new ATM();
        assertDoesNotThrow(() -> {
            atm.loginUser(username);
            atm.deposit(amountToDeposit);
            atm.withdraw(amountToWithdraw);
            assertEquals(amountToDeposit - (2 * amountToWithdraw), atm.withdraw(amountToWithdraw));
        });
    }

}