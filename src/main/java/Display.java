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

    public void showBalance(Customer customer) {
        printStream.println("Your balance is $" + customer.getBalance());
    }

    public void showBalance(int balance) {
        printStream.println("Your balance is $" + balance);
    }

    public void showTransferMessage(int amountToTransfer, String transferTo) {
        printStream.println("Transferred " + amountToTransfer + " to " + transferTo);
    }
}
