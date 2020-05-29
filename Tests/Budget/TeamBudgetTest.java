package Budget;

import AssociationAssets.Field;
import AssociationAssets.Season;
import AssociationAssets.Team;
import PoliciesAndAlgorithms.SimpleGamesAssigningPolicy;
import Users.RepresentativeFootballAssociation;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class TeamBudgetTest {
    TeamBudget teamBudget;
    SimpleGamesAssigningPolicy gamePolicy;
    HashMap<String, Pair<Double, String>> outcomes;
    HashMap<String, Pair<Double, String>> incomes;
    Team team;
    Season season;
    RepresentativeFootballAssociation representative;
    Field field;
    @Before
    public void setUp(){
        gamePolicy =new SimpleGamesAssigningPolicy() ;
        outcomes = new HashMap<>();
        incomes = new HashMap<>();
        field = new Field("fieldTest","city",10);
        team= new Team(1,"Barcelona" ,season, field, null, null);
        season = new Season("2020");
        representative = new RepresentativeFootballAssociation("representative","Dani","Levi",gamePolicy);
        teamBudget = new TeamBudget(team, season);
    }

    @Test
    public void checkTeamBudgetExceedRule() {
        teamBudget.setThreshHold(1.0,representative);
        teamBudget.getOutcomes().put("outcome",new Pair<>(100.5,"outcome test"));
        teamBudget.getIncomes().put("income",new Pair<>(5.0,"income test"));
        teamBudget.checkTeamBudgetExceedRule(representative);
        assertEquals(representative.getTeamsExceedBudget().containsKey(team.getName()),true);
    }

    @Test
    public void totalOutcomes() {
        teamBudget.addOutcome("outcome1",1000.5,"bla");
        teamBudget.addOutcome("outcome2",2000.2,"bla");
        assertEquals(teamBudget.totalOutcomes(),3000.7,0);
    }

    @Test
    public void totalIncomes() {
        teamBudget.addIncome("income1",200.2,"bla");
        teamBudget.addIncome("income2",500.7,"bla");
        assertEquals(teamBudget.totalIncomes(),700.9,0);
    }

    @Test
    public void addOutcome() {
        teamBudget.addOutcome("outcome",100.8,"bla");
        assertTrue(teamBudget.getOutcomes().containsKey("outcome"));
    }

    @Test
    public void addIncome() {
        teamBudget.addIncome("income",500.5,"bla");
        assertTrue(teamBudget.getIncomes().containsKey("income"));
    }

    @Test
    public void getThreshHold() {
        teamBudget.setThreshHold(20.2,representative);
        assertEquals(teamBudget.getThreshHold(),20.2,0);
    }

    @Test
    public void getTeam() {
        assertEquals(teamBudget.getTeam().getTID(),1);
    }

    @Test
    public void getIncomes() {
        Pair  pair = new Pair(30.3, "income");
        teamBudget.addIncome("incomeTest",30.3,"income");
        assertEquals(teamBudget.getIncomes().get("incomeTest"),pair);
    }

    @Test
    public void getOutcomes() {
        Pair pair = new Pair(10.3, "bla");
        teamBudget.addOutcome("outcomeTest",10.3,"bla");
        assertEquals(teamBudget.getOutcomes().get("outcomeTest"),pair);
    }

    @Test
    public void setThreshHold() {
        teamBudget.setThreshHold(10.5,representative);
        assertEquals(teamBudget.getThreshHold(), 10.5,0);
    }

}