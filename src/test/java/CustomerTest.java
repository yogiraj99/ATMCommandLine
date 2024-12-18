import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void initialBalanceShouldBe0() {
        Customer customer = new Customer("customerName");
        assertEquals(0, customer.getBalance());
    }

    @Test
    void shouldIncreaseBalance() {
        Customer customer = new Customer("customerName");
        int amountToIncrease = 75;

        customer.increaseBalance(amountToIncrease);
        assertEquals(amountToIncrease, customer.getBalance());
    }

    @Test
    void shouldIncreaseBalanceMoreThanOnce() {
        Customer customer = new Customer("customerName");
        int amountToIncrease = 75;
        int differentAmountToIncrease = 75;

        customer.increaseBalance(amountToIncrease);
        customer.increaseBalance(differentAmountToIncrease);
        assertEquals(amountToIncrease + differentAmountToIncrease, customer.getBalance());
    }

    @Test
    void shouldDecreaseBalance() {
        Customer customer = new Customer("customerName");
        int amount = 75;
        int amountToDecrease = 34;

        customer.increaseBalance(amount);
        customer.decreaseBalance(amountToDecrease);
        assertEquals(amount - amountToDecrease, customer.getBalance());
    }

    @Test
    void shouldDecreaseBalanceMoreThanOnce() {
        Customer customer = new Customer("customerName");
        int amount = 164;
        int amountToDecrease = 64;
        int differentAmountToDecrease = 21;

        customer.increaseBalance(amount);
        customer.decreaseBalance(amountToDecrease);
        customer.decreaseBalance(differentAmountToDecrease);
        assertEquals(amount - amountToDecrease - differentAmountToDecrease, customer.getBalance());
    }

    @Test
    void shouldMakeBalanceZero() {
        Customer customer = new Customer("customerName");
        int amount = 164;

        customer.increaseBalance(amount);
        assertEquals(amount, customer.makeBalanceZero());
        assertEquals(0, customer.getBalance());
    }

}