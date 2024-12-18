import exception.DifferentCustomerLoggedInException;
import exception.InvalidAmountException;
import exception.NotEnoughBalanceException;
import exception.CustomerNotLoggedInException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ATMTest {

    @Test
    void loginCustomerShouldGetCustomerWhenAlreadyLoggedInCustomerTryToLogin() {
        String customerName = "ExistingCustomer";
        ATM atm = new ATM();
        assertDoesNotThrow(() -> {
            Customer existingCustomer = atm.loginCustomer(customerName);
            assertEquals(existingCustomer, atm.loginCustomer(customerName));
        });

        assertEquals(atm.getLoggedInCustomer().getCustomerName(), customerName);
    }

    @Test
    void loginCustomerShouldCreateCustomerWhenCustomerLoggingIn1StTime() {
        String customerName = "NewCustomer";
        ATM atm = new ATM();
        Customer customer = new Customer(customerName);
        assertDoesNotThrow(() -> assertEquals(customer, atm.loginCustomer(customerName)));

        assertEquals(atm.getLoggedInCustomer().getCustomerName(), customerName);
    }

    @Test
    void loginCustomerShouldGetCustomerWhenCustomerLoggingIn2NdTime() {
        String customerName = "ExistingCustomer";
        ATM atm = new ATM();

        assertDoesNotThrow(() -> {
            Customer existingCustomer = atm.loginCustomer(customerName);
            atm.logoutCustomer();
            assertEquals(existingCustomer, atm.loginCustomer(customerName));
            assertEquals(atm.getLoggedInCustomer().getCustomerName(), customerName);
        });

    }

    @Test
    void logoutCustomerShouldRemoveCustomerFromLoggedInCustomer() {
        String customerName = "ExistingCustomer";
        ATM atm = new ATM();
        assertDoesNotThrow(() -> atm.loginCustomer(customerName));

        Customer expectedCustomer = new Customer(customerName);
        assertDoesNotThrow(() -> assertEquals(expectedCustomer, atm.logoutCustomer()));
        assertNull(atm.getLoggedInCustomer());
    }

    @Test
    void shouldThrowWhenLoggingOutWithoutLogin() {
        ATM atm = new ATM();
        assertThrows(CustomerNotLoggedInException.class, atm::logoutCustomer);
    }

    @Test
    void shouldThrowWhenDifferentCustomerTriesToLoginBeforeExistingCustomerLoggingOut() {
        String customerName = "ExistingCustomer";
        String differentcustomerName = "DifferentCustomer";
        ATM atm = new ATM();
        assertDoesNotThrow(() -> atm.loginCustomer(customerName));
        assertThrows(DifferentCustomerLoggedInException.class, () -> atm.loginCustomer(differentcustomerName));
    }

    @Test
    void shouldThrowWhenTryingToDepositWithoutLogin() {
        ATM atm = new ATM();
        assertThrows(CustomerNotLoggedInException.class, () -> atm.deposit(80));
    }

    @Test
    void shouldDepositMoneyInCustomerAccount() {
        String customerName = "NewCustomer";
        int amountToDeposit = 80;

        ATM atm = new ATM();
        assertDoesNotThrow(() -> {
            atm.loginCustomer(customerName);
            atm.deposit(amountToDeposit);
            assertEquals(amountToDeposit, atm.getLoggedInCustomer().getBalance());
            atm.logoutCustomer();
            atm.deposit(amountToDeposit, customerName);
            assertEquals(amountToDeposit + amountToDeposit, atm.getCustomers().get(customerName).getBalance());
        });
    }

    @Test
    void shouldDepositMoneyMoreThanOnceForSameCustomer() {
        String customerName = "NewCustomer";
        int amountToDeposit = 53;

        ATM atm = new ATM();
        assertDoesNotThrow(() -> {
            atm.loginCustomer(customerName);
            atm.deposit(amountToDeposit);
            atm.deposit(amountToDeposit);
            assertEquals(2 * amountToDeposit, atm.getLoggedInCustomer().getBalance());
        });
    }

    @Test
    void shouldThrowWhenDepositingZeroOrNegativeMoney() {
        String customerName = "NewCustomer";
        int negativeAmount = -38;

        ATM atm = new ATM();
        assertDoesNotThrow(() -> atm.loginCustomer(customerName));
        assertThrows(InvalidAmountException.class, () -> atm.deposit(0));
        assertThrows(InvalidAmountException.class, () -> atm.deposit(negativeAmount));
    }

    @Test
    void shouldThrowWhenTryingToWithdrawWithoutLogin() {
        ATM atm = new ATM();
        assertThrows(CustomerNotLoggedInException.class, () -> atm.withdraw(80));
    }

    @Test
    void shouldThrowWhenWithdrawingMoneyMoreThanAccountBalance() {
        String customerName = "NewCustomer";
        int amountToWithdraw = 38;

        ATM atm = new ATM();
        assertDoesNotThrow(() -> atm.loginCustomer(customerName));
        assertThrows(NotEnoughBalanceException.class, () -> atm.withdraw(amountToWithdraw));
    }

    @Test
    void shouldThrowWhenWithdrawingZeroOrNegativeMoney() {
        String customerName = "NewCustomer";
        int negativeAmount = -38;
        int randomAmountToDeposit = 56;

        ATM atm = new ATM();
        assertDoesNotThrow(() -> {
            atm.loginCustomer(customerName);
            atm.deposit(randomAmountToDeposit);
        });
        assertThrows(InvalidAmountException.class, () -> atm.withdraw(0));
        assertThrows(InvalidAmountException.class, () -> atm.withdraw(negativeAmount));
    }

    @Test
    void shouldWithdrawMoneyFromCustomerAccount() {
        String customerName = "NewCustomer";
        int amountToDeposit = 80;
        int amountToWithdraw = 50;

        ATM atm = new ATM();
        assertDoesNotThrow(() -> {
            atm.loginCustomer(customerName);
            atm.deposit(amountToDeposit);
            assertEquals(amountToDeposit - amountToWithdraw, atm.withdraw(amountToWithdraw));
        });
    }

    @Test
    void shouldWithdrawMoneyMoreThanOnceFromSameCustomer() {
        String customerName = "NewCustomer";
        int amountToDeposit = 153;
        int amountToWithdraw = 47;

        ATM atm = new ATM();
        assertDoesNotThrow(() -> {
            atm.loginCustomer(customerName);
            atm.deposit(amountToDeposit);
            atm.withdraw(amountToWithdraw);
            assertEquals(amountToDeposit - (2 * amountToWithdraw), atm.withdraw(amountToWithdraw));
        });
    }

    @Test
    void shouldWithdrawAll() {
        String customerName = "NewCustomer";
        int amountToDeposit = 80;

        ATM atm = new ATM();
        assertDoesNotThrow(() -> {
            atm.loginCustomer(customerName);
            atm.deposit(amountToDeposit);
            assertEquals(amountToDeposit, atm.withdrawAll());
            assertEquals(0, atm.getLoggedInCustomer().getBalance());
        });
    }

    @Test
    void shouldTransferMoneyFromOneCustomerAccountToOther() {
        String customerName = "NewCustomer";
        String anotherCustomerName = "AnotherCustomer";
        int amountToDeposit = 80;
        int amountToTransfer = 50;

        ATM atm = new ATM();
        assertDoesNotThrow(() -> {
            atm.loginCustomer(customerName);
            atm.deposit(amountToDeposit);
            atm.logoutCustomer();

            atm.loginCustomer(anotherCustomerName);
            atm.logoutCustomer();

            atm.loginCustomer(customerName);
            atm.transferMoney(amountToTransfer, anotherCustomerName);

            assertEquals(amountToTransfer, atm.getCustomers().get(anotherCustomerName).getBalance());
            assertEquals(amountToDeposit - amountToTransfer, atm.getCustomers().get(customerName).getBalance());
        });
    }
}