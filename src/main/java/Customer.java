import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Customer {
    private final String customerName;
    private int balance;
    private final HashMap<String, Integer> owedTo = new HashMap<>();
    private final HashMap<String, Integer> owedFrom = new HashMap<>();

    public int increaseBalance(int amountToIncrease) {
        return this.balance += amountToIncrease;
    }

    public int decreaseBalance(int amountToDecrease) {
        return this.balance -= amountToDecrease;
    }

    public int makeBalanceZero() {
        int existingBalance = this.balance;
        this.balance = 0;
        return existingBalance;
    }

    public void addOwedTo(String owedTo, int amountOwed) {
        this.owedTo.put(owedTo, amountOwed);
    }

    public void addOwedFrom(String owedFrom, int amountOwed) {
        this.owedFrom.put(owedFrom, amountOwed);
    }

    public boolean doesOwe() {
        return !this.owedTo.isEmpty();
    }
}
