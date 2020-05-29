package OutSourceSystems;

public class AccountingSystem implements IAccountingSystem {

    @Override
    public boolean connect() {
        return true;
    }

    @Override
    public boolean addPayment(String teamName, String date, double amount) {
        return true;
    }
}
