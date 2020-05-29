package OutSourceSystems;
public class ProxyTaxRegulationSystem implements ITaxRegulationSystem {

    private static TaxRegulationSystem taxRegulationSystem;

    @Override
    public boolean connect() {
        if(taxRegulationSystem == null){
            taxRegulationSystem = new TaxRegulationSystem();
            return taxRegulationSystem.connect();
        }
        return taxRegulationSystem.connect();
    }

    @Override
    public double getTaxRate(double revenueAmount) {

        return taxRegulationSystem.getTaxRate(revenueAmount);
    }

}

