import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

class ATMControllerTest {
    private static final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private static final PrintStream printStream = new PrintStream(byteArrayOutputStream);

    @BeforeAll
    static void setup() {
        System.setErr(printStream);
    }

    @Test
    void shouldSayHelloWhenCustomerLogin() {
        String customerName = "newCustomer";
        Customer customer = new Customer(customerName);
        ATM atm = new ATM();
        Display mockDisplay = Mockito.mock(Display.class);

        ATMController atmController = new ATMController(atm, mockDisplay);
        atmController.loginCustomer(customerName);

        Mockito.verify(mockDisplay).sayHello(customer);
        Mockito.verify(mockDisplay).showBalance(customer);
    }

    @Test
    void shouldSayGoodByeWhenCustomerLogout() {
        String customerName = "newCustomer";
        Customer customer = new Customer(customerName);
        ATM atm = new ATM();
        Display mockDisplay = Mockito.mock(Display.class);

        ATMController atmController = new ATMController(atm, mockDisplay);
        atmController.loginCustomer(customerName);
        atmController.logoutCustomer();

        Mockito.verify(mockDisplay).sayHello(customer);
        Mockito.verify(mockDisplay).sayGoodBye(customer);
    }

    @Test
    void shouldDepositMoney() {
        String customerName = "newCustomer";
        int moneyToDeposit = 567;
        ATM atm = new ATM();
        Display mockDisplay = Mockito.mock(Display.class);
        ATMController atmController = new ATMController(atm, mockDisplay);

        assertDoesNotThrow(() -> atmController.loginCustomer(customerName));

        int moneyDeposited = atmController.depositMoney(moneyToDeposit);
        assertEquals(moneyToDeposit, moneyDeposited);

        int totalMoneyToDeposit = moneyToDeposit + moneyToDeposit;
        int actualMoneyDeposited = atmController.depositMoney(moneyToDeposit);
        assertEquals(totalMoneyToDeposit, actualMoneyDeposited);

        Mockito.verify(mockDisplay).sayHello(any());
        Mockito.verify(mockDisplay, Mockito.times(3)).showBalance(any());
    }

    @Test
    void shouldNotDepositAndWarnWhenDepositingMoneyWithoutLogin() {
        int moneyToDeposit = 567;
        ATM atm = new ATM();
        Display display = new Display(printStream);
        ATMController atmController = new ATMController(atm, display);

        int moneyDeposited = atmController.depositMoney(moneyToDeposit);

        assertEquals(0, moneyDeposited);

        System.err.flush();
        String output = byteArrayOutputStream.toString();
        assertTrue(output.contains("Could not deposit money as customer is not logged in"), "This method should warn");
    }

    @Test
    void shouldNotDepositAndWarnWhenDepositingMoneyZeroOrNegativeAmount() {
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
        Display mockDisplay = Mockito.mock(Display.class);
        ATMController atmController = new ATMController(atm, mockDisplay);

        assertDoesNotThrow(() -> atmController.loginCustomer(customerName));
        atmController.depositMoney(moneyToDeposit);

        int remainingAmount = moneyToDeposit - moneyToWithdraw;
        assertEquals(remainingAmount, atmController.withdrawMoney(moneyToWithdraw));

        Mockito.verify(mockDisplay).sayHello(any());
        Mockito.verify(mockDisplay, Mockito.times(3)).showBalance(any());
    }

    @Test
    void shouldNotWithdrawAndWarnWhenWithdrawingMoneyWithoutLogin() {
        int amountToWithdraw = 567;
        ATM atm = new ATM();
        Display display = new Display(printStream);
        ATMController atmController = new ATMController(atm, display);

        int moneyWithdrawn = atmController.withdrawMoney(amountToWithdraw);

        assertEquals(0, moneyWithdrawn);

        System.err.flush();
        String output = byteArrayOutputStream.toString();
        assertTrue(output.contains("Could not deposit money as customer is not logged in"), "This method should warn");
    }

    @Test
    void shouldNotWithdrawAndWarnWhenWithdrawingMoneyZeroOrNegativeAmount() {
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
    void shouldNotWithdrawAndWarnWhenWithdrawingMoneyMoreAmountThanAccountBalance() {
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
        assertTrue(output.contains("Could not withdraw money as customer does not have enough balance"), "This method should warn");
    }

    @Test
    void shouldTransferMoney() {
        String customerName = "customer";
        String secondCustomerName = "secondCustomer";
        int firstCustomerMoneyToDeposit = 567;
        int secondCustomerMoneyToDeposit = 91;
        int amountToTransfer = 48;
        ATM atm = new ATM();
        PrintStream printStream = Mockito.mock(PrintStream.class);
        Display display = new Display(printStream);

        ATMController atmController = new ATMController(atm, display);
        assertDoesNotThrow(() -> atmController.loginCustomer(customerName));
        atmController.depositMoney(firstCustomerMoneyToDeposit);
        assertDoesNotThrow(atmController::logoutCustomer);

        assertDoesNotThrow(() -> atmController.loginCustomer(secondCustomerName));
        atmController.depositMoney(secondCustomerMoneyToDeposit);
        assertDoesNotThrow(atmController::logoutCustomer);

        assertDoesNotThrow(() -> atmController.loginCustomer(customerName));
        atmController.transferMoney(amountToTransfer, secondCustomerName);
        assertEquals(secondCustomerMoneyToDeposit + amountToTransfer, atm.getCustomers().get(secondCustomerName).getBalance());
        assertEquals(firstCustomerMoneyToDeposit - amountToTransfer, atm.getCustomers().get(customerName).getBalance());
    }

    @Test
    void shouldTransferMoneyAndOweTheRemainingMoney() {
        String customerName = "customer";
        String secondCustomerName = "secondCustomer";
        int firstCustomerMoneyToDeposit = 80;
        int amountToTransfer = 100;
        ATM atm = new ATM();
        PrintStream printStream = Mockito.mock(PrintStream.class);
        Display display = new Display(printStream);

        ATMController atmController = new ATMController(atm, display);
        assertDoesNotThrow(() -> atmController.loginCustomer(customerName));
        atmController.depositMoney(firstCustomerMoneyToDeposit);
        assertDoesNotThrow(atmController::logoutCustomer);

        assertDoesNotThrow(() -> atmController.loginCustomer(secondCustomerName));
        assertDoesNotThrow(atmController::logoutCustomer);
        assertDoesNotThrow(() -> atmController.loginCustomer(customerName));

        atmController.transferMoney(amountToTransfer, secondCustomerName);
        assertEquals(0, atm.getLoggedInCustomer().getBalance());
        assertEquals(firstCustomerMoneyToDeposit, atm.getCustomers().get(secondCustomerName).getBalance());

        HashMap<Customer, Integer> owedTo = new HashMap<>();
        int amountOwed = amountToTransfer - firstCustomerMoneyToDeposit;
        Customer secondCustomer = new Customer(secondCustomerName);
        secondCustomer.increaseBalance(firstCustomerMoneyToDeposit);
        owedTo.put(secondCustomer, amountOwed);
        assertEquals(owedTo, atm.getLoggedInCustomer().getOwedTo());

        HashMap<Customer, Integer> owedFrom = new HashMap<>();
        Customer firstCustomer = new Customer(customerName);
        owedFrom.put(firstCustomer, amountOwed);
        assertEquals(owedFrom, atm.getCustomers().get(secondCustomerName).getOwedFrom());
    }

    @Test
    void depositShouldTransferMoneyWhenOwedTo() {
        String customerName = "customer";
        String secondCustomerName = "secondCustomer";
        int firstCustomerMoneyToDeposit = 80;
        int amountToTransfer = 100;
        ATM atm = new ATM();
        PrintStream printStream = Mockito.mock(PrintStream.class);
        Display display = new Display(printStream);

        ATMController atmController = new ATMController(atm, display);
        assertDoesNotThrow(() -> atmController.loginCustomer(customerName));
        atmController.depositMoney(firstCustomerMoneyToDeposit);
        assertDoesNotThrow(atmController::logoutCustomer);

        assertDoesNotThrow(() -> atmController.loginCustomer(secondCustomerName));
        assertDoesNotThrow(atmController::logoutCustomer);
        assertDoesNotThrow(() -> atmController.loginCustomer(customerName));

        atmController.transferMoney(amountToTransfer, secondCustomerName);

        int amountOwed = amountToTransfer - firstCustomerMoneyToDeposit;
        atmController.depositMoney(amountOwed);

        assertEquals(0, atm.getLoggedInCustomer().getBalance());
        assertEquals(amountToTransfer, atm.getCustomers().get(secondCustomerName).getBalance());
        assertEquals(new HashMap<>(), atm.getLoggedInCustomer().getOwedTo());
        assertEquals(new HashMap<>(), atm.getCustomers().get(secondCustomerName).getOwedFrom());
    }

}