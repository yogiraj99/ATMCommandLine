import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.PrintStream;

class DisplayTest {

    @Test
    void shouldSayHelloCustomer() {
        String customerName = "newCustomer";
        Customer customer = new Customer(customerName);
        PrintStream mockPrintStream = Mockito.mock(PrintStream.class);
        Display display = new Display(mockPrintStream);
        display.sayHello(customer);

        Mockito.verify(mockPrintStream).println("Hello, " + customerName);
    }

    @Test
    void shouldSayGoodByeCustomer() {
        String customerName = "newCustomer";
        Customer customer = new Customer(customerName);
        PrintStream mockPrintStream = Mockito.mock(PrintStream.class);
        Display display = new Display(mockPrintStream);
        display.sayGoodBye(customer);

        Mockito.verify(mockPrintStream).println("Goodbye, " + customerName);
    }

    @Test
    void shouldShowBalanceOfCustomer() {
        int balance = 320;
        Customer customer = Mockito.mock(Customer.class);
        Mockito.when(customer.getBalance()).thenReturn(balance);
        PrintStream mockPrintStream = Mockito.mock(PrintStream.class);
        Display display = new Display(mockPrintStream);
        display.showBalance(customer);

        Mockito.verify(mockPrintStream).println("Your balance is $" + balance);
    }

}