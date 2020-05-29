package OutSourceSystems;

public class TaxRegulationSystem implements ITaxRegulationSystem {


    @Override
    public boolean connect() {
        return true;
    }

    @Override
    public double getTaxRate(double revenueAmount) {
        return 0;
    }
}
