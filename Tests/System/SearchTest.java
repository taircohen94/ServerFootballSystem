package System;

import AssociationAssets.*;
import DB.*;
import PoliciesAndAlgorithms.GamesAssigningPolicy;
import PoliciesAndAlgorithms.SimpleGamesAssigningPolicy;
import Users.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;

public class SearchTest {
    //<editor-fold desc="Fields">
    FootballSystem footballSystem;
    GamesAssigningPolicy gamePolicy;
    Search search;
    Coach coach;
    Team team;
    Team hostTeam;
    League league;
    Season season;
    Referee ref1;
    Field field;
    Player player;
    Fan fan;
    TeamOwner teamOwner;
    TeamManager teamManager;
    SystemManager systemManager;
    RepresentativeFootballAssociation representative;
    //</editor-fold>
    @Before
    public void setUp() throws Exception {
        footballSystem= FootballSystem.getInstance();
        gamePolicy = new SimpleGamesAssigningPolicy();
        search = new Search();
        field = new Field("fieldTest","Beer-Sheva",1000);
        team = new Team(123,"teamTest",new Season("2019"), field,null, null);
        league = new League("leagueTest");
        season = new Season("2020");
        hostTeam = new Team(456,"hostTest",season,field,null,null);
        footballSystem.signIn("ref1","672", "refTest1","crefTest1");
        ref1=(Referee)footballSystem.creatingReferee("ref1","refTest1","refTest1", EReferee.MAIN);
        //teamOwner
        footballSystem.signIn("teamOwner", "390124","tOwnerFname","tOnerLname");
        teamOwner = (TeamOwner)footballSystem.creatingTeamOwner("teamOwner","tOwnerFname","tOnerLname");
        //teamManager
        footballSystem.signIn("TMUname","67103","TMFname","TMLname");
        teamManager=(TeamManager)footballSystem.creatingTeamManager("TMUname","TMFname","TMLname");
        //systemManager
        footballSystem.signIn("SMenagerUname","678912","SMenagerFname","SMenagerLname");
        //TODO: Tair: add creatingSystemManager in FootballSystem
       // systemManager= (SystemManager)footballSystem.creatingSystemManager("SMenagerUname","SMenagerFname","SMenagerLname");
       //representative
        footballSystem.signIn("repreUname","5689","repreFname","repreLname");
        representative=(RepresentativeFootballAssociation)footballSystem.creatingRepresentativeFootballAssociation("repreUname","repreFname","repreLname",gamePolicy);
       //player
        footballSystem.signIn("playerUName","67267", "playerFname","playerLname");
        player = (Player)footballSystem.creatingPlayer("playerUName","playerFname","playerLname",null,EPlayerRole.GoalKeeper);
        //coach
        footballSystem.signIn("coachTest","94672", "coachFname","coachLname");
        coach=(Coach) footballSystem.creatingCoach("coachName","coachFname","coachLname",ETraining.CDiploma,ECoachRole.AssistantCoach);
        //fan
        fan= footballSystem.signIn("testFan","123456","FanFname","FanLname");
        footballSystem.addTeamToDB(team);

        footballSystem.getTeamDB().addTeam(team,team.getName());
       footballSystem.getLeagueDB().addLeague(league,league.getLeagueName());
        footballSystem.getSeasonDB().addSeason(season,"2020");
        footballSystem.getFieldDB().addField(field,field.getName());
    }

    @Test
    public void getUserByUserName() {
        try {
           assertSame(search.getUserByUserName(ref1.getUserName()), ref1);
           assertSame( search.getUserByUserName(coach.getUserName()),coach);
           assertSame(search.getUserByUserName(player.getUserName()),player);
           assertSame(search.getUserByUserName(fan.getUserName()),fan);
           assertSame(search.getUserByUserName(teamOwner.getUserName()),teamOwner);
           assertSame(search.getUserByUserName(teamManager.getUserName()),teamManager);
           assertSame(search.getUserByUserName(representative.getUserName()),representative);
           assertSame(search.getUserByUserName(systemManager.getUserName()),systemManager);
       }catch (Exception e){
        }
    }

    @Test
    public void getTeamByTeamName() {
            assertSame(search.getTeamByTeamName("teamTest"),team);
    }

    @Test
    public void getLeagueByLeagueName() {
        try {
            assertSame(search.getLeagueByLeagueName(league.getLeagueName()), league);
        }catch(Exception e){
        }
    }

    @Test
    public void getSeasonByYear() {
        try {
            assertSame(search.getSeasonByYear("2020"), season);
        }catch (Exception e){

        }
    }

    @Test
    public void getGameByGameID() throws Exception {
        try{
         Game gameTest = new Game(new Date(), null, field, hostTeam, team, new Referee("ref1Test","ref1FName","ref2LName",EReferee.MAIN), new Referee("ref2Test","ref2FName","ref2LName",EReferee.ASSISTANT), new Referee("re3Test","ref3FName","Ref3LName",EReferee.ASSISTANT), season, league);
        footballSystem.getGameDB().addGame(gameTest,gameTest.getGID());
            assertSame(search.getGameByGameID(gameTest.getGID()), gameTest);
        }catch (Exception e){}
    }

    @Test
    public void getFieldByFieldName() {
        try {
            setUp();
            assertSame(search.getFieldByFieldName("fieldTest"), field);
        }catch (Exception e){
        }
    }

    @Test
    public void getAllLeaguesProfile() {
        try {
            HashMap<String, String> testHashMap = new HashMap<>();
            testHashMap.put(league.getLeagueName(), league.viewProfile());
            Iterator it = testHashMap.entrySet().iterator();
            Map.Entry entryTest = (Map.Entry) it.next();
            while (it.hasNext()) {
                for (Map.Entry<String, String> entry : search.getAllLeaguesProfile().entrySet()) {
                    assertSame(entryTest, entry);
                    entryTest = (Map.Entry) it.next();
                }
            }
        }catch (Exception e){
        }
    }

    @Test
    public void getAllTeamsProfile() {
        try {
            HashMap<String, String> testHashMap = new HashMap<>();
            testHashMap.put(team.getName(), team.viewProfile());
            Iterator it = testHashMap.entrySet().iterator();
            Map.Entry entryTest = (Map.Entry) it.next();
            while (it.hasNext()) {
                for (Map.Entry<String, String> entry : search.getAllTeamsProfile().entrySet()) {
                    assertSame(entryTest, entry);
                    entryTest = (Map.Entry) it.next();
                }
            }
        }catch (Exception e){
        }
    }

    @Test
    public void getAllRefereesProfile() {
        try {
            HashMap<String, String> testHashMap = new HashMap<>();
            testHashMap.put(ref1.getUserName(), ref1.viewProfile());
            Iterator it = testHashMap.entrySet().iterator();
            Map.Entry entryTest = (Map.Entry) it.next();
            while (it.hasNext()) {
                for (Map.Entry<String, String> entry : search.getAllRefereesProfile().entrySet()) {
                    assertSame(entryTest, entry);
                    entryTest = (Map.Entry) it.next();
                }
            }
        }catch (Exception e){
        }
    }

    @Test
    public void getAllCoachesProfile() {
        try {
            HashMap<String, String> testHashMap = new HashMap<>();
            testHashMap.put(coach.getUserName(), coach.viewProfile());
            Iterator it = testHashMap.entrySet().iterator();
            Map.Entry entryTest = (Map.Entry) it.next();
            while (it.hasNext()) {
                for (Map.Entry<String, String> entry : search.getAllCoachesProfile().entrySet()) {
                    assertSame(entryTest, entry);
                    entryTest = (Map.Entry) it.next();
                }
            }
        }catch (Exception e){
        }
    }

    @Test
    public void getAllPlayersProfile() {
        try {
            HashMap<String, String> testHashMap = new HashMap<>();
            testHashMap.put(player.getUserName(), player.viewProfile());
            Iterator it = testHashMap.entrySet().iterator();
            Map.Entry entryTest = (Map.Entry) it.next();
            while (it.hasNext()) {
                for (Map.Entry<String, String> entry : search.getAllPlayersProfile().entrySet()) {
                    assertSame(entryTest, entry);
                    entryTest = (Map.Entry) it.next();
                }
            }
        }catch (Exception e){
        }
    }

//    @Test
//    public void setLeagueDB() {
//        try {
//            LeagueDB leagueDBTest = new LeagueDB();
//            search.setLeagueDB(leagueDBTest);
//            assertSame(search.getLeagueDB(), leagueDBTest);
//        }catch (Exception e){
//        }
//    }
//
//    @Test
//    public void setTeamDB() {
//        try {
//            TeamDB teamDBTest = new TeamDB();
//            search.setTeamDB(teamDBTest);
//            assertSame(search.getTeamDB(), teamDBTest);
//        }catch (Exception e){
//        }
//    }
//
//    @Test
//    public void setSeasonDB() {
//        try {
//            SeasonDB seasonDBTest = new SeasonDB();
//            search.setSeasonDB(seasonDBTest);
//            assertSame(search.getSeasonDB(), seasonDBTest);
//        }catch (Exception e) {
//        }
//    }
//
//    @Test
//    public void getTeamDB() {
//        try {
//            assertSame(search.getTeamDB().getAllTeams().get(team.getName()), team);
//        }catch (Exception e){
//        }
//    }
//
//    @Test
//    public void getLeagueDB() {
//        try {
//            assertSame(search.getLeagueDB().getAllLeagues().get(league.getLeagueName()), league);
//        }catch (Exception e){
//        }
//    }
//
//    @Test
//    public void getSeasonDB() {
//        try {
//            assertSame(search.getSeasonDB().getAllSeasons().get(season.getYear()), season);
//        }catch (Exception e){
//        }
//    }
//
//    @Test
//    public void getGameDB() {
//        try {
//            assertSame(search.getGameDB().getAllGames().get(game.getGID()), game);
//        }catch (Exception e){
//        }
//    }
//
//    @Test
//    public void getFieldDB() {
//        try {
//            assertSame(search.getFieldDB().getAllFields().get(field.getName()), field);
//        }catch (Exception e){
//        }
//    }
}