package System;

import AssociationAssets.*;
import Budget.TeamBudget;
import Users.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.FailedLoginException;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class FootballSystemTest {

    Field field;
    Team team1, team2;
    League league;
    Game game;
    TeamOwner teamOwner1,teamOwner2;
    TeamBudget teamBudget;
    Player player;
    Coach coach;
    TeamManager teamManager;
    Fan fan;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        FootballSystem.getInstance().getFansHashMap().clear();
        FootballSystem.getInstance().getFieldDB().getAllFields().clear();
        FootballSystem.getInstance().getTeamDB().getAllTeams().clear();
        FootballSystem.getInstance().getSeasonDB().getAllSeasons().clear();
    }

    @Test
    void getInstance() {
    }

    @Test
    void signIn() {
        assertEquals(null,FootballSystem.getInstance().signIn(null,"12","tair","cohen"));
        assertEquals(0,FootballSystem.getInstance().getFansHashMap().size());
        assertEquals(null,FootballSystem.getInstance().signIn("tair",null,"tair","cohen"));
        assertEquals(0,FootballSystem.getInstance().getFansHashMap().size());
        assertEquals(null,FootballSystem.getInstance().signIn("tair","12",null,"cohen"));
        assertEquals(0,FootballSystem.getInstance().getFansHashMap().size());
        assertEquals(null,FootballSystem.getInstance().signIn("tair","12","tair",null));
        assertEquals(0,FootballSystem.getInstance().getFansHashMap().size());
        assertTrue(null != FootballSystem.getInstance().signIn("tair12","12","tair","cohen"));
        assertEquals(1,FootballSystem.getInstance().getFansHashMap().size());
        assertEquals(null,FootballSystem.getInstance().signIn("tair12","12","tair","cohen"));
        assertEquals(1,FootballSystem.getInstance().getFansHashMap().size());
    }

    @Test
    void creatingRepresentativeFootballAssociation() {
        assertEquals(0,FootballSystem.getInstance().getFansHashMap().size());
        assertTrue(null != FootballSystem.getInstance().creatingRepresentativeFootballAssociation("Tair",
                "tair","cohen",null));
        assertEquals(1,FootballSystem.getInstance().getFansHashMap().size());

        assertTrue(null == FootballSystem.getInstance().creatingRepresentativeFootballAssociation("Tair",
                "tair","cohen",null));
        assertEquals(1,FootballSystem.getInstance().getFansHashMap().size());
    }

    @Test
    void creatingReferee() {
        assertEquals(0,FootballSystem.getInstance().getFansHashMap().size());
        assertTrue(null != FootballSystem.getInstance().creatingReferee("Tair",
                "tair","cohen",null));
        assertEquals(1,FootballSystem.getInstance().getFansHashMap().size());

        assertTrue(null == FootballSystem.getInstance().creatingReferee("Tair",
                "tair","cohen",null));
        assertEquals(1,FootballSystem.getInstance().getFansHashMap().size());
    }

    @Test
    void creatingCoach() {
        assertEquals(0,FootballSystem.getInstance().getFansHashMap().size());
        assertTrue(null != FootballSystem.getInstance().creatingCoach("Tair",
                "tair","cohen",null,null));
        assertEquals(1,FootballSystem.getInstance().getFansHashMap().size());
        assertTrue(null == FootballSystem.getInstance().creatingCoach("Tair",
                "tair","cohen",null,null));
        assertEquals(1,FootballSystem.getInstance().getFansHashMap().size());
    }


    @Test
    void creatingTeamOwner() {
        assertEquals(0,FootballSystem.getInstance().getFansHashMap().size());
        assertTrue(null != FootballSystem.getInstance().creatingTeamOwner("Tair",
                "tair","cohen"));
        assertEquals(1,FootballSystem.getInstance().getFansHashMap().size());
        assertTrue(null == FootballSystem.getInstance().creatingTeamOwner("Tair",
                "tair","cohen"));
        assertEquals(1,FootballSystem.getInstance().getFansHashMap().size());
    }

    @Test
    void creatingTeamManager() {
        assertEquals(1,FootballSystem.getInstance().getFansHashMap().size());
        assertTrue(null != FootballSystem.getInstance().creatingTeamManager("Tair",
                "tair","cohen"));
        assertEquals(2,FootballSystem.getInstance().getFansHashMap().size());
        assertTrue(null == FootballSystem.getInstance().creatingTeamManager("Tair",
                "tair","cohen"));
        assertEquals(2,FootballSystem.getInstance().getFansHashMap().size());
    }

    @Test
    void creatingPlayer() {
        assertEquals(0,FootballSystem.getInstance().getFansHashMap().size());
        assertTrue(null != FootballSystem.getInstance().creatingPlayer("Tair",
                "tair","cohen",null,null));
        assertEquals(1,FootballSystem.getInstance().getFansHashMap().size());
        assertTrue(null == FootballSystem.getInstance().creatingPlayer("Tair",
                "tair","cohen",null,null));
        assertEquals(1,FootballSystem.getInstance().getFansHashMap().size());
    }

    @Test
    void login()  throws FailedLoginException {
        assertTrue(null != FootballSystem.getInstance().signIn("Tair","12","tair","cohen"));
        assertEquals(1,FootballSystem.getInstance().fansHashMap.size());
        assertTrue(null != FootballSystem.getInstance().login("Tair","12"));
        assertTrue(null == FootballSystem.getInstance().login("Tair","11"));
    }

    @Test
    void removeUser() {
        assertTrue(null != FootballSystem.getInstance().signIn("Tair","12","tair","cohen"));
        assertEquals(1,FootballSystem.getInstance().fansHashMap.size());
        FootballSystem.getInstance().removeUser("Tair");
        FootballSystem.getInstance().removeUser("lalala");
        assertEquals(0,FootballSystem.getInstance().fansHashMap.size());
    }

    @Test
    void getFanByUserName() {
        Fan fan = FootballSystem.getInstance().signIn("Tair","12","tair",
                "cohen");
        assertTrue(fan.equals(FootballSystem.getInstance().getFanByUserName("Tair")));
    }

    @Test
    void existFanByUserName() {
        Fan fan = FootballSystem.getInstance().signIn("Tair","12","tair","cohen");
        assertTrue(FootballSystem.getInstance().existFanByUserName("Tair"));
        assertTrue(!FootballSystem.getInstance().existFanByUserName("lala"));
    }

    @Test
    void addTeamToDB() throws Exception {
        team1 = new Team(1,"Macabi-Tel-aviv",null,null,null, null);
        team2 = new Team(2,"Beitar",null,null,null, null);
        assertEquals(0,FootballSystem.getInstance().getTeamDB().getAllTeams().size());
        FootballSystem.getInstance().addTeamToDB(team1);
        assertEquals(1,FootballSystem.getInstance().getTeamDB().getAllTeams().size());
        FootballSystem.getInstance().addTeamToDB(team2);
        assertEquals(2,FootballSystem.getInstance().getTeamDB().getAllTeams().size());
        FootballSystem.getInstance().addTeamToDB(team2);
        assertEquals(2,FootballSystem.getInstance().getTeamDB().getAllTeams().size());

    }

    @Test
    void removeTeamFromDB() {
        team1 = new Team(1,"Macabi-Tel-aviv",null,null,null, null);
        team2 = new Team(2,"Beitar",null,null,null, null);
        assertEquals(0,FootballSystem.getInstance().getTeamDB().getAllTeams().size());
        try {
            FootballSystem.getInstance().addTeamToDB(team1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(1,FootballSystem.getInstance().getTeamDB().getAllTeams().size());
        FootballSystem.getInstance().removeTeamFromDB(team1.getName());
        assertEquals(0,FootballSystem.getInstance().getTeamDB().getAllTeams().size());
    }

    @Test
    void addLeagueToDB() {
        League league1 = new League("Best");
        League league2 = new League("bad");
        assertEquals(0,FootballSystem.getInstance().getLeagueDB().getAllLeagues().size());
        FootballSystem.getInstance().addLeagueToDB(league1);
        assertEquals(1,FootballSystem.getInstance().getLeagueDB().getAllLeagues().size());
        FootballSystem.getInstance().addLeagueToDB(league1);
        assertEquals(1,FootballSystem.getInstance().getLeagueDB().getAllLeagues().size());
        FootballSystem.getInstance().addLeagueToDB(league2);
        assertEquals(2,FootballSystem.getInstance().getLeagueDB().getAllLeagues().size());
    }

    @Test
    void removeLeagueFromDB() {
        league = new League("Best");
        assertEquals(0,FootballSystem.getInstance().getLeagueDB().getAllLeagues().size());
        FootballSystem.getInstance().addLeagueToDB(league);
        assertEquals(1,FootballSystem.getInstance().getLeagueDB().getAllLeagues().size());
        FootballSystem.getInstance().removeLeagueFromDB(league.getLeagueName());
        assertEquals(0,FootballSystem.getInstance().getLeagueDB().getAllLeagues().size());
    }

    @Test
    void addFieldToDB() {
        Field field1 = new Field("Blom","Tel-Aviv",10000);
        assertEquals(0,FootballSystem.getInstance().getFieldDB().getAllFields().size());
        FootballSystem.getInstance().addFieldToDB(field1);
        assertEquals(1,FootballSystem.getInstance().getFieldDB().getAllFields().size());
        FootballSystem.getInstance().addFieldToDB(field1);
        assertEquals(1,FootballSystem.getInstance().getFieldDB().getAllFields().size());
    }

    @Test
    void removeFieldFromDB() {
        Field field1 = new Field("Blom","Tel-Aviv",10000);
        assertEquals(0,FootballSystem.getInstance().getFieldDB().getAllFields().size());
        FootballSystem.getInstance().addFieldToDB(field1);
        assertEquals(1,FootballSystem.getInstance().getFieldDB().getAllFields().size());
        FootballSystem.getInstance().addFieldToDB(field1);
        assertEquals(1,FootballSystem.getInstance().getFieldDB().getAllFields().size());
        FootballSystem.getInstance().removeFieldFromDB("Blomm");
        assertEquals(1,FootballSystem.getInstance().getFieldDB().getAllFields().size());
        FootballSystem.getInstance().removeFieldFromDB("Blom");
        assertEquals(0,FootballSystem.getInstance().getFieldDB().getAllFields().size());
    }

    @Test
    void addSeasonToDB() {
        Season season1 = new Season("2010");
        Season season2 = new Season("2011");
        assertEquals(0,FootballSystem.getInstance().getSeasonDB().getAllSeasons().size());
        FootballSystem.getInstance().addSeasonToDB(season1);
        assertEquals(1,FootballSystem.getInstance().getSeasonDB().getAllSeasons().size());
        FootballSystem.getInstance().addSeasonToDB(season1);
        assertEquals(1,FootballSystem.getInstance().getSeasonDB().getAllSeasons().size());
        FootballSystem.getInstance().addSeasonToDB(season2);
        assertEquals(2,FootballSystem.getInstance().getSeasonDB().getAllSeasons().size());
    }

    @Test
    void removeSeasonFromDB() {
        Season season1 = new Season("2010");
        Season season2 = new Season("2011");
        assertEquals(0,FootballSystem.getInstance().getSeasonDB().getAllSeasons().size());
        FootballSystem.getInstance().addSeasonToDB(season1);
        assertEquals(1,FootballSystem.getInstance().getSeasonDB().getAllSeasons().size());
        FootballSystem.getInstance().addSeasonToDB(season1);
        assertEquals(1,FootballSystem.getInstance().getSeasonDB().getAllSeasons().size());
        FootballSystem.getInstance().addSeasonToDB(season2);
        assertEquals(2,FootballSystem.getInstance().getSeasonDB().getAllSeasons().size());        FootballSystem.getInstance().removeSeasonFromDB("2011");
        assertEquals(1,FootballSystem.getInstance().getSeasonDB().getAllSeasons().size());
        FootballSystem.getInstance().removeSeasonFromDB("201");
        assertEquals(1,FootballSystem.getInstance().getSeasonDB().getAllSeasons().size());
        FootballSystem.getInstance().removeSeasonFromDB("2010");
        assertEquals(0,FootballSystem.getInstance().getSeasonDB().getAllSeasons().size());
    }

    @Test
    void initialize() {

    }
}