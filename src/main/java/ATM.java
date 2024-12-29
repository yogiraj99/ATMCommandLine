import exception.CustomerDoesNotExistException;
import exception.DifferentCustomerLoggedInException;
import exception.InvalidAmountException;
import exception.NotEnoughBalanceException;
import exception.CustomerNotLoggedInException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Objects;

public class ATM {

    @Getter
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

    public Customer logoutCustomer() throws CustomerNotLoggedInException, DifferentCustomerLoggedInException {
        if (Objects.isNull(this.loggedInCustomer)) {
            throw new CustomerNotLoggedInException();
        }
        Customer customer = this.loggedInCustomer;
        this.loggedInCustomer = null;
        return customer;
    }

    public int deposit(int amountToDeposit) throws CustomerNotLoggedInException, InvalidAmountException {
        if (Objects.isNull(this.loggedInCustomer)) {
            throw new CustomerNotLoggedInException();
        }
        if (amountToDeposit < 1) {
            throw new InvalidAmountException();
        }
        int actualAmountToDeposit = amountToDeposit;
        if (this.loggedInCustomer.doesOwe()) {
            int totalDebt = this.loggedInCustomer.totalDebt();
            if (totalDebt > amountToDeposit) {
                this.settleDebt(amountToDeposit);
                return this.loggedInCustomer.getBalance();
            }
            this.settleDebt(totalDebt);
            actualAmountToDeposit = amountToDeposit - totalDebt;
        }
        return this.loggedInCustomer.increaseBalance(actualAmountToDeposit);
    }

    private void settleDebt(int amountToSettle) {
        this.loggedInCustomer.settleDebt(amountToSettle);
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

    public int transferMoney(int amountToTransfer, String transferTo) throws InvalidAmountException, CustomerNotLoggedInException, NotEnoughBalanceException, CustomerDoesNotExistException {
        if (this.customers.containsKey(transferTo)) {
            int currentBalance = this.withdraw(amountToTransfer);
            this.deposit(amountToTransfer, transferTo);
            return currentBalance;
        }
        throw new CustomerDoesNotExistException();
    }

    public int withdrawAll() {
        return this.loggedInCustomer.makeBalanceZero();
    }

    public void deposit(int amountToDeposit, String transferTo) {
        Customer customer = this.customers.get(transferTo);
        customer.increaseBalance(amountToDeposit);
    }

    public void oweMoney(String owedTo, int amountOwed) {
        Customer customer = this.customers.get(owedTo);
        this.getLoggedInCustomer().addOwedTo(customer, amountOwed);
        this.customers.get(owedTo).addOwedFrom(this.loggedInCustomer, amountOwed);
    }
}
