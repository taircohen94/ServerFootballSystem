package Budget;

import AssociationAssets.Season;
import AssociationAssets.Team;
import Users.RepresentativeFootballAssociation;
import javafx.util.Pair;
import java.util.*;


public class TeamBudget extends Observable {
    Team team;
    Season season;
    HashMap<String, Pair<Double,String>> incomes; // String- income name, pair: double-cost, String-description
    HashMap<String, Pair<Double,String>> outcomes;
    double threshHold;
    private RepresentativeFootballAssociation representative;
    /**
     * Constructor
     * @param team
     * @param season
     */
    public TeamBudget(Team team, Season season) {
        this.team = team;
        this.season = season;
        this.incomes = new HashMap<>();
        this.outcomes= new HashMap<>();
    }

    public void checkTeamBudgetExceedRule(RepresentativeFootballAssociation representative) {
        if (representative != null){
            if ((1 + threshHold) * totalIncomes() < totalOutcomes()) {
                setNews(representative);
            }
       }
    }
    public void setNews(RepresentativeFootballAssociation representative) {
        if(representative!=null) {
            this.representative = representative;
            //representative.update(this, this.team.getName());
        }
    }
    /**
     * @return total team's outcomes
     */
    public double totalOutcomes(){
        Double sum=0.0;
        for (Map.Entry<String, Pair<Double, String>> entry : outcomes.entrySet()){
            sum+=entry.getValue().getKey();
        }
        return sum;
    }

    /**
     * @return total team's incomes
     */
    public double totalIncomes(){
        Double sum=0.0;
        for (Map.Entry<String, Pair<Double, String>> entry : incomes.entrySet()){
            sum+=entry.getValue().getKey();
        }
        return sum;
    }

    /**
     * add outcome to outcome's team
     * @param outcomeName
     * @param outcomeValue
     * @param description
     */
    public  void addOutcome (String outcomeName,Double outcomeValue, String description){
        if(!(outcomeName == null || outcomeValue <= 0 || description == null)) {
            outcomes.put(outcomeName, new Pair<>(outcomeValue, description));
            checkTeamBudgetExceedRule(this.representative);
        }
    }

    /**
     * add income to income's team
     * @param incomeName
     * @param incomeName
     * @param description
     */
    public  void addIncome (String incomeName,Double incomeValue, String description ){
        if(!(incomeName == null || incomeValue <= 0 || description == null)) {
            incomes.put(incomeName, new Pair<>(incomeValue, description));
            checkTeamBudgetExceedRule(this.representative);
        }
    }

    //region Getters: getThreshHold, getTeam, getIncomes, getOutcomes
    public double getThreshHold() { return threshHold; }
    public Team getTeam() { return team; }
    public HashMap<String, Pair<Double, String>> getIncomes() { return incomes; }
    public HashMap<String, Pair<Double, String>> getOutcomes() {
        return outcomes;
    }

    //endregion

    //region Setter: setThreshHold, setRepresentative
    public void setThreshHold(double threshHold, RepresentativeFootballAssociation representative) {
        if(threshHold <0){
            return;
        }
        this.threshHold = threshHold;
        checkTeamBudgetExceedRule(representative);
    }
    public void setRepresentative(RepresentativeFootballAssociation representative){
        this.representative = representative;
    }

    //endregion

}
