import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void initialBalanceShouldBe0() {
        User user = new User("username");
        assertEquals(0, user.getBalance());
    }

    @Test
    void shouldIncreaseBalance() {
        User user = new User("username");
        int amountToIncrease = 75;

        user.increaseBalance(amountToIncrease);
        assertEquals(amountToIncrease, user.getBalance());
    }

    @Test
    void shouldIncreaseBalanceMoreThanOnce() {
        User user = new User("username");
        int amountToIncrease = 75;
        int differentAmountToIncrease = 75;

        user.increaseBalance(amountToIncrease);
        user.increaseBalance(differentAmountToIncrease);
        assertEquals(amountToIncrease + differentAmountToIncrease, user.getBalance());
    }

    @Test
    void shouldDecreaseBalance() {
        User user = new User("username");
        int amount = 75;
        int amountToDecrease = 34;

        user.increaseBalance(amount);
        user.decreaseBalance(amountToDecrease);
        assertEquals(amount - amountToDecrease, user.getBalance());
    }

    @Test
    void shouldDecreaseBalanceMoreThanOnce() {
        User user = new User("username");
        int amount = 164;
        int amountToDecrease = 64;
        int differentAmountToDecrease = 21;

        user.increaseBalance(amount);
        user.decreaseBalance(amountToDecrease);
        user.decreaseBalance(differentAmountToDecrease);
        assertEquals(amount - amountToDecrease - differentAmountToDecrease, user.getBalance());
    }

}