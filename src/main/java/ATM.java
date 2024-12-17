import exception.DifferentCustomerLoggedInException;
import exception.InvalidAmountException;
import exception.NotEnoughBalanceException;
import exception.CustomerNotLoggedInException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Objects;

public class ATM {

    private final HashMap<String, Customer> customers;
    @Getter
    private Customer loggedInCustomer;

    ATM() {
        this.customers = new HashMap<>();
    }

    public Customer loginCustomer(String customerName) throws DifferentCustomerLoggedInException {
        if (Objects.isNull(this.loggedInCustomer)) {
            return this.customers.containsKey(customerName) ? getCustomer(customerName) : createNewCustomer(customerName);
        }
        if (Objects.equals(this.loggedInCustomer.getCustomerName(), customerName)) {
            return this.loggedInCustomer;
        }
        throw new DifferentCustomerLoggedInException();

    }

    private Customer getCustomer(String customerName) {
        Customer customer = this.customers.get(customerName);
        this.loggedInCustomer = customer;
        return customer;
    }

    private Customer createNewCustomer(String customerName) {
        Customer customer = new Customer(customerName);
        this.loggedInCustomer = customer;
        this.customers.put(customerName, customer);
        return customer;
    }

    public Customer logoutCustomer(String customerName) throws CustomerNotLoggedInException, DifferentCustomerLoggedInException {
        if (Objects.isNull(this.loggedInCustomer)) {
            throw new CustomerNotLoggedInException();
        }
        if (Objects.equals(this.loggedInCustomer.getCustomerName(), customerName)) {
            this.loggedInCustomer = null;
            return this.customers.get(customerName);
        }
        throw new DifferentCustomerLoggedInException();
    }

    public int deposit(int amountToDeposit) throws CustomerNotLoggedInException, InvalidAmountException {
        if (Objects.isNull(this.loggedInCustomer)) {
            throw new CustomerNotLoggedInException();
        }
        if (amountToDeposit < 1) {
            throw new InvalidAmountException();
        }
        return this.loggedInCustomer.increaseBalance(amountToDeposit);
    }

    public int withdraw(int amountToWithdraw) throws CustomerNotLoggedInException, InvalidAmountException, NotEnoughBalanceException {
        if (Objects.isNull(this.loggedInCustomer)) {
            throw new CustomerNotLoggedInException();
        }
        if (amountToWithdraw < 1) {
            throw new InvalidAmountException();
        }
        if (amountToWithdraw > this.loggedInCustomer.getBalance()) {
            throw new NotEnoughBalanceException();
        }
        return this.loggedInCustomer.decreaseBalance(amountToWithdraw);
    }
}
