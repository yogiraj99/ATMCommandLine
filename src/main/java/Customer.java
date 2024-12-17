import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Customer {
    private final String customerName;
    private int balance;

    public int increaseBalance(int amountToIncrease) {
        return this.balance += amountToIncrease;
    }

    public int decreaseBalance(int amountToDecrease) {
        return this.balance -= amountToDecrease;
    }
}
