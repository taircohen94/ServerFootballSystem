package OutSourceSystems;

public interface IAccountingSystem {
    boolean connect();
    boolean addPayment(String teamName, String date, double amount);
}
