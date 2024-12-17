import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class User {
    private final String username;
    private int balance;

    public int increaseBalance(int amountToIncrease) {
        return this.balance += amountToIncrease;
    }

    public int decreaseBalance(int amountToDecrease) {
        return this.balance -= amountToDecrease;
    }
}
