import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.PrintStream;

@Getter
@AllArgsConstructor
public class Display {

    private final PrintStream printStream;

    public void sayHello(Customer customer) {
        printStream.println("Hello, " + customer.getCustomerName());
    }

    public void sayGoodBye(Customer customer) {
        printStream.println("Goodbye, " + customer.getCustomerName());
    }
}
