package OutSourceSystems;

public interface ITaxRegulationSystem {
    boolean connect();
    double getTaxRate(double revenueAmount);
}
