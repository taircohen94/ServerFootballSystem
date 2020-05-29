package Users;

import AssociationAssets.*;
import Budget.AssociationBudget;
import PoliciesAndAlgorithms.RegularScorePolicy;
import PoliciesAndAlgorithms.SimpleGamesAssigningPolicy;
import Budget.TeamBudget;
import System.*;
import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import javax.security.auth.login.FailedLoginException;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

public class RepresentativeFootballAssociationTest {
    League league;
    Season season;
    RepresentativeFootballAssociation representative;
    SimpleGamesAssigningPolicy gamePolicy;
    RegularScorePolicy scorePolicy;
    AssociationBudget associationBudget;
    Game game1;
    Game game2;
    Team team1, team2;
    Field field;
    Referee r1,r2,r3;
    HashMap <Integer,Game> games;
    HashMap <String,Team> teams;
    TeamBudget teamBudget;
    HashMap<String,TeamOwner> owners;
    HashMap<String,Coach> coaches;
    HashMap<String, Player>players;
    @Before
    public void set_up() throws Exception {
        league = new League("champions");
        season = new Season("2020");
        owners= new HashMap<>();
        coaches =  new HashMap<>();
        players = new HashMap<>();
        gamePolicy =new SimpleGamesAssigningPolicy() ;
        scorePolicy =  new RegularScorePolicy();
        associationBudget = new AssociationBudget();
        field= new Field("Teddi", "Beer Sheva", 800);
        r1= new Referee("1","a","a",EReferee.MAIN);
        r2= new Referee("2","a","a",EReferee.ASSISTANT);
        r3= new Referee("3","a","a",EReferee.ASSISTANT);
        team1 = new Team(1,"Barcelona",season,field,null,null);
        team2= new Team(2,"Real Madrid",season ,field, null, null);
        game1= new Game(new Date(10,10,2020),
                new Time(19,30,0),
                field,team1,team2,r1,r2,r3,season,
                league);
        game2= new Game(new Date(5,4,2020),
                new Time(20,00,0),
                field,team2,team1,r1,r3,r2,season,
                league);
        games = new HashMap<>();
        games.put(123,game1);
        games.put(113,game2);
        teams = new HashMap<>();
        teams.put(team1.getName(),team1);
        teams.put(team2.getName(),team2);
        representative = new RepresentativeFootballAssociation("representative","Dan","Levi",gamePolicy);
        teamBudget =  new TeamBudget(team1,season);
    }

    @Test
    public void addNewLeague() {
        representative.addNewLeague("leagueTest",games,teams,scorePolicy,gamePolicy,"2020",season);
        assertTrue(FootballSystem.getInstance().existLeagueByName("leagueTest"));
    }

    @Test
    public void setSeasonToLeague() {
        representative.setSeasonToLeague(league,"2019",games,teams);
        assertTrue(league.getSeasonBinders().containsKey("2019"));
    }

    @Test
    public void nominateReferee()  throws FailedLoginException {
        Referee referee = representative.nominateReferee("Dani","Mizrahi",EReferee.ASSISTANT);
        assertTrue(FootballSystem.getInstance().existFanByUserName(referee.getUserName()));
        Referee referee2=  representative.nominateReferee("Dani","Mizrahi",EReferee.ASSISTANT);
        assertTrue(FootballSystem.getInstance().existFanByUserName(referee2.getUserName()));
    }

    @Test
    public void signInReferee() {
        String userName = representative.signInReferee("Alon","Dan","1234567");
        assertEquals(userName,"AlonDan1");
    }

    @Test
    public void removeReferee() {
        Referee referee = (Referee) FootballSystem.getInstance().creatingReferee("refTest","Dna","Levi",EReferee.ASSISTANT);
        representative.removeReferee(referee);
        assertFalse(FootballSystem.getInstance().existFanByUserName("refTest"));
    }

    @Test
    public void assignReferees() throws Exception {
       Referee ref1= new Referee("ref1","ref1","a",EReferee.MAIN);
       Referee  ref2= new Referee("ref2","ref2","a",EReferee.ASSISTANT);
       Referee ref3= new Referee("ref3","ref3","a",EReferee.ASSISTANT);
       representative.assignReferees(ref1,ref2,ref3,game1);
       assertEquals(game1.getMain().getfName(),"ref1");
       assertEquals(game1.getSide1().getfName(),"ref2");
       assertEquals(game1.getSide2().getfName(),"ref3");
    }

    @Test
    public void setGamesAssigningPolicy()  {
        try {
            representative.SetGamesAssigningPolicy(gamePolicy,league,season);
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }
        assertNotEquals(representative.getAssigningPolicy(),null);
    }

    //next iteration
    @Test
    public void activateGamesAssigning() {
    }

    @Test
    public void setTeamBudgetControlRules() {
        representative.setTeamBudgetControlRules(team1,season,20.2, teamBudget);
        assertEquals(teamBudget.getThreshHold(),20.2,0);
    }

    @Test
    public void setAssociationBudgetTutuIntakes() {
        representative.setAssociationBudgetTutuIntakes(10);
        assertEquals(associationBudget.getTutuIntakes(),10,0);
    }

    @Test
    public void setAssociationBudgetRegistrationFee() {
        representative.setAssociationBudgetRegistrationFee(10);
        assertEquals(associationBudget.getRegistrationFee(),10,0);

    }

    @Test
    public void setAssociationBudgetSalaries() {
        Fan user = new Fan("testUser","bla","bla");
        representative.setAssociationBudgetSalaries(user,1000.0);
        boolean bool = associationBudget.userSalaryIsExist(user);
        assertTrue(associationBudget.userSalaryIsExist(user));
    }

    @Test
    public void getTeamsExceedBudget(){
        representative.getTeamsExceedBudget().put("TeamExceedBudget",true);
        assertNotNull(representative.getTeamsExceedBudget());

    }
}