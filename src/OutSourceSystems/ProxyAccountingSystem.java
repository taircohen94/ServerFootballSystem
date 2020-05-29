package OutSourceSystems;

public class ProxyAccountingSystem implements IAccountingSystem {

    private static AccountingSystem accountingSystem;

    @Override
    public boolean connect() {
        if(accountingSystem == null){
            accountingSystem = new AccountingSystem();
            return accountingSystem.connect();
        }
        return accountingSystem.connect();
    }

    @Override
    public boolean addPayment(String teamName, String date, double amount) {
        return accountingSystem.addPayment(teamName, date, amount);
    }
}
