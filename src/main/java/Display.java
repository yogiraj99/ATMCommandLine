import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.PrintStream;
import java.util.Map;

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
        if (customer.doesOwe()) {
            this.showDebtOwed(customer);
        }
        if (customer.isOwedFrom()) {
            this.showDebtOwedFrom(customer);
        }
    }

    private void showDebtOwed(Customer customer) {
        for (Map.Entry<Customer, Integer> check : customer.getOwedTo().entrySet()) {
            Integer amountOwed = check.getValue();
            Customer owedTo = check.getKey();
            printStream.println("Owed " + amountOwed + " to " + owedTo.getCustomerName());
        }
    }

    private void showDebtOwedFrom(Customer customer) {
        for (Map.Entry<Customer, Integer> check : customer.getOwedFrom().entrySet()) {
            Integer amountOwed = check.getValue();
            Customer owedFrom = check.getKey();
            printStream.println("Owed " + amountOwed + " from " + owedFrom.getCustomerName());
        }
    }

    public void showTransferMessage(int amountToTransfer, String transferTo) {
        printStream.println("Transferred " + amountToTransfer + " to " + transferTo);
    }
}
