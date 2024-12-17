import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

class ATMControllerTest {
    private static ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private static PrintStream printStream = new PrintStream(byteArrayOutputStream);

    @BeforeAll
    static void setup() {
        System.setErr(printStream);
    }

    @SneakyThrows
    @Test
    void shouldSayHelloWhenCustomerLogin() {
        String customerName = "newCustomer";
        ATM mockATM = new ATM();
        PrintStream printStream = Mockito.mock(PrintStream.class);
        Display display = new Display(printStream);

        ATMController atmController = new ATMController(mockATM, display);
        atmController.loginCustomer(customerName);

        Mockito.verify(printStream).println(anyString());
    }

    @SneakyThrows
    @Test
    void shouldSayGoodByeWhenCustomerLogout() {
        String customerName = "newCustomer";
        ATM atm = new ATM();
        PrintStream printStream = Mockito.mock(PrintStream.class);
        Display display = new Display(printStream);

        ATMController atmController = new ATMController(atm, display);
        atmController.loginCustomer(customerName);
        atmController.logoutCustomer(customerName);

        Mockito.verify(printStream, Mockito.times(2)).println(anyString());
    }

    @Test
    void shouldDepositMoney() {
        String customerName = "newCustomer";
        int moneyToDeposit = 567;
        ATM atm = new ATM();
        PrintStream printStream = Mockito.mock(PrintStream.class);
        Display display = new Display(printStream);
        ATMController atmController = new ATMController(atm, display);

        assertDoesNotThrow(() -> atmController.loginCustomer(customerName));

        int moneyDeposited = atmController.depositMoney(moneyToDeposit);
        assertEquals(moneyToDeposit, moneyDeposited);

        int totalMoneyToDeposit = moneyToDeposit + moneyToDeposit;
        int actualMoneyDeposited = atmController.depositMoney(moneyToDeposit);
        assertEquals(totalMoneyToDeposit, actualMoneyDeposited);

        Mockito.verify(printStream).println(anyString());
    }

    @Test
    void shouldWarnWhenDepositingMoneyWithoutLogin() {
        int moneyToDeposit = 567;
        ATM atm = new ATM();
        Display display = new Display(printStream);
        ATMController atmController = new ATMController(atm, display);

        int moneyDeposited = atmController.depositMoney(moneyToDeposit);

        assertEquals(0, moneyDeposited);

        System.err.flush();
        String output = byteArrayOutputStream.toString();
        assertTrue(output.contains("Could not deposit money as Customer is not logged in"), "This method should warn");
    }

    @Test
    void shouldWarnWhenDepositingMoneyZeroOrNegativeAmount() {
        String customerName = "newCustomer";
        int negativeAmount = -78;
        ATM atm = new ATM();
        Display display = new Display(printStream);
        ATMController atmController = new ATMController(atm, display);
        assertDoesNotThrow(() -> atmController.loginCustomer(customerName));

        int moneyDeposited = atmController.depositMoney(0);
        assertEquals(0, moneyDeposited);
        System.err.flush();
        String output = byteArrayOutputStream.toString();
        assertTrue(output.contains("Could not deposit money as amount is invalid"), "This method should warn");

        moneyDeposited = atmController.depositMoney(negativeAmount);
        assertEquals(0, moneyDeposited);
        System.err.flush();
        output = byteArrayOutputStream.toString();
        assertTrue(output.contains("Could not deposit money as amount is invalid"), "This method should warn");
    }

    @Test
    void shouldWithdrawMoney() {
        String customerName = "newCustomer";
        int moneyToDeposit = 567;
        int moneyToWithdraw = 48;
        ATM atm = new ATM();
        PrintStream printStream = Mockito.mock(PrintStream.class);
        Display display = new Display(printStream);
        ATMController atmController = new ATMController(atm, display);

        assertDoesNotThrow(() -> atmController.loginCustomer(customerName));
        atmController.depositMoney(moneyToDeposit);
        assertEquals(moneyToDeposit - moneyToWithdraw, atmController.withdrawMoney(moneyToWithdraw));

        Mockito.verify(printStream).println(anyString());
    }

    @Test
    void shouldWarnWhenWithdrawingMoneyWithoutLogin() {
        int amountToWithdraw = 567;
        ATM atm = new ATM();
        Display display = new Display(printStream);
        ATMController atmController = new ATMController(atm, display);

        int moneyWithdrawn = atmController.withdrawMoney(amountToWithdraw);

        assertEquals(0, moneyWithdrawn);

        System.err.flush();
        String output = byteArrayOutputStream.toString();
        assertTrue(output.contains("Could not deposit money as Customer is not logged in"), "This method should warn");
    }

    @Test
    void shouldWarnWhenWithdrawingMoneyZeroOrNegativeAmount() {
        String customerName = "newCustomer";
        int moneyToDeposit = 567;
        int negativeAmount = -78;
        ATM atm = new ATM();
        Display display = new Display(printStream);
        ATMController atmController = new ATMController(atm, display);
        assertDoesNotThrow(() -> atmController.loginCustomer(customerName));
        atmController.depositMoney(moneyToDeposit);

        int amountWithdrawn = atmController.withdrawMoney(0);
        assertEquals(0, amountWithdrawn);
        System.err.flush();
        String output = byteArrayOutputStream.toString();
        assertTrue(output.contains("Could not deposit money as amount is invalid"), "This method should warn");

        amountWithdrawn = atmController.withdrawMoney(negativeAmount);
        assertEquals(0, amountWithdrawn);
        System.err.flush();
        output = byteArrayOutputStream.toString();
        assertTrue(output.contains("Could not deposit money as amount is invalid"), "This method should warn");
    }

    @Test
    void shouldWarnWhenWithdrawingMoneyMoreAmountThanAccountBalance() {
        String customerName = "newCustomer";
        int amountToWithdraw = 567;
        ATM atm = new ATM();
        Display display = new Display(printStream);
        ATMController atmController = new ATMController(atm, display);
        assertDoesNotThrow(() -> atmController.loginCustomer(customerName));

        int amountWithdrawn = atmController.withdrawMoney(amountToWithdraw);
        assertEquals(0, amountWithdrawn);
        System.err.flush();
        String output = byteArrayOutputStream.toString();
        assertTrue(output.contains("Could not deposit money as customer does not have enough balance"), "This method should warn");
    }
}