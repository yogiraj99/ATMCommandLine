import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Customer {
    private final String customerName;
    private int balance;
    @EqualsAndHashCode.Exclude
    private final HashMap<Customer, Integer> owedTo = new HashMap<>();
    @EqualsAndHashCode.Exclude
    private final HashMap<Customer, Integer> owedFrom = new HashMap<>();

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

    public void addOwedTo(Customer owedTo, int amountOwed) {
        this.owedTo.put(owedTo, amountOwed);
        owedTo.addOwedFrom(this,amountOwed);
    }

    public void addOwedFrom(Customer owedFrom, int amountOwed) {
        this.owedFrom.put(owedFrom, amountOwed);
    }

    public boolean doesOwe() {
        return !this.owedTo.isEmpty();
    }

    public int totalDebt() {
        return this.owedTo.values().stream().reduce(Integer::sum).orElse(0);
    }

    public void settleDebt(int amountToSettle) {
        int remainingAmount = amountToSettle;
        for (Map.Entry<Customer, Integer> check : this.owedTo.entrySet()) {
            Integer amountOwed = check.getValue();
            Customer owedTo = check.getKey();
            if (amountOwed > remainingAmount) {
                int settleBalance = amountOwed - remainingAmount;
                this.settleDebt(owedTo,settleBalance);
                return;
            }
            this.settleDebt(owedTo);
            remainingAmount -= amountOwed;
            if (remainingAmount == 0) {
                return;
            }
        }
    }

    private void settleDebt(Customer owedTo) {
        this.owedTo.remove(owedTo);
        owedTo.settleDebtFrom(this);
    }

    private void settleDebt(Customer owedTo, int settleBalance) {
        this.owedTo.replace(owedTo, settleBalance);
        owedTo.settleDebtFrom(this,settleBalance);
    }

    public void settleDebtFrom(Customer debtOf) {
        int totalDebtToSettle = this.owedFrom.get(debtOf);
        this.owedFrom.remove(debtOf);
        this.balance += totalDebtToSettle;
    }

    public void settleDebtFrom(Customer debtFrom, int amountToSettle) {
        int totalDebt = this.owedFrom.get(debtFrom);
        this.owedFrom.replace(debtFrom, totalDebt - amountToSettle);
        this.balance += amountToSettle;
    }

    public boolean isOwedFrom() {
        return !this.owedFrom.isEmpty();
    }
}
