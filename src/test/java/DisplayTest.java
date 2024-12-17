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

}