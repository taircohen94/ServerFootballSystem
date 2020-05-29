package Users;

import AssociationAssets.Field;
import AssociationAssets.League;
import AssociationAssets.Season;
import AssociationAssets.Team;
import DB.LeagueDB;
import DB.SeasonDB;
import DB.TeamDB;
import org.junit.Before;
import org.junit.Test;
import System.Search;
import System.*;

import javax.security.auth.login.FailedLoginException;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class GuestTest {
    Guest guest;
    Coach coach;
    Player player;
    Team team;
    League league;
    League league1;
    Season season;
   LeagueDB leagueDB;
   TeamDB teamDB;
   SeasonDB seasonDB;
   Search search;
   Field field;
   Referee referee;
   FootballSystem footballSystem = FootballSystem.getInstance();
    @Before
    public void setUp(){
        guest = new Guest();
        leagueDB = new LeagueDB();
        teamDB = new TeamDB();
        seasonDB = new SeasonDB();
        search= guest.getSearch();
        coach = new Coach("coachTest", "Dan", "Levi", ETraining.UEFAA,ECoachRole.AssistantCoach);
        player = new Player("playerTest","Eli","Dan",null,EPlayerRole.GoalKeeper);
        league = new League("champions");
        league1 = new League("leagueTest");
        referee = new Referee("refereeTest","refereeFname","refereeLname",EReferee.ASSISTANT);
        season = new Season("2020");
        field = new Field("fieldTest","city",10);
        team= new Team(1,"Barcelona" ,season, field, null, null);
        footballSystem.getLeagueDB().addLeague(league,league.getLeagueName());
        footballSystem.getLeagueDB().addLeague(league1,league1.getLeagueName());
        footballSystem.getTeamDB().addTeam(team,team.getName());
        footballSystem.getSeasonDB().addSeason(season,season.getYear());
        footballSystem.signIn(coach.getUserName(),"34567",coach.getfName(),coach.getlName());
        footballSystem.addCoach(coach);
        footballSystem.addReferee(referee);
        footballSystem.addPlayer(player);
    }

    @Test
    public void signInGuest() throws FailedLoginException {
        guest.signInGuest("GuestTest","1234567","Dani","Dan");
        assertTrue( footballSystem.getFansHashMap().containsKey("GuestTest"));
    }

    @Test
    public void logInGuest()  throws FailedLoginException {
        guest.signInGuest("GuestTest","1234567","Dani","Dan");
       Fan loginUser= guest.logInGuest("GuestTest","1234567");
       assertNotNull(loginUser);
       Fan loginUser1 =  guest.logInGuest("GuestTest","123456");
       assertNull(loginUser1);
    }

    @Test
    public void viewInformationCoach(){
        String test = guest.viewInformationCoach(coach.getUserName());
       assertEquals(guest.viewInformationCoach(coach.getUserName()),coach.viewProfile());
    }

    @Test
    public void viewInformationPlayer(){
        assertEquals(guest.viewInformationPlayer(player.getUserName()),player.viewProfile());

    }

    @Test
    public void viewInformationTeam(){
        assertEquals(guest.viewInformationTeam(team.getName()),team.viewProfile());
    }

    @Test
    public void viewInformationLeague(){
        assertEquals(guest.viewInformationLeague(league.getLeagueName()),league.viewProfile());

    }
    @Test
    public void viewInformationSeason(){
        assertEquals(guest.viewInformationSeason(season.getYear()),season.viewProfile());
    }

    @Test
    public void searchByNameInUsers() {
        assertEquals(guest.searchByName("coachTest"), search.getAllCoachesProfile().get("coachTest"));
    }
    @Test
    public void searchByNameInLeagues() {
        assertEquals(guest.searchByName(league.getLeagueName()), league.viewProfile());
    }
    @Test
    public void searchByNameInTeams() {
        assertNull(guest.searchByName(team.getName()),null);
    }

    @Test
    public void searchByCategory1() {
        LinkedList<String>resultTest=new LinkedList<>();
        resultTest.add(league.getLeagueName() + " " + "League" + ":" + "\n" + league.viewProfile());
        resultTest.add(league1.getLeagueName()+" " + "League" + ":" + "\n" + league1.viewProfile());
        LinkedList<String> result = guest.searchByCategory("League");
        assertEquals(resultTest.get(0),result.get(0));
        assertEquals(resultTest.get(1),result.get(1));

    }
    @Test
    public void searchByCategory2() {
        LinkedList<String>resultTest=new LinkedList<>();
        resultTest.add( team.getName() + ":" + "\n" + team.viewProfile());
        LinkedList<String> result = guest.searchByCategory("Team");
        assertEquals(result.get(0), resultTest.get(0));
    }

    @Test
    public void searchByCategory3() {
        LinkedList<String> resultTest=new LinkedList<>();
        resultTest.add(referee.getUserName() + ":" + "\n" + referee.viewProfile());
        LinkedList<String> result = guest.searchByCategory("Referee");
        assertEquals(result.get(0), resultTest.get(0));
    }
    @Test
    public void searchByCategory4() {
        LinkedList<String> resultTest=new LinkedList<>();
        resultTest.add( player.getUserName()+":"+"\n"+ player.viewProfile());
        LinkedList<String> result = guest.searchByCategory("Player");
        assertEquals(result.get(0),resultTest.get(0));
    }

    @Test
    public void searchByCategory5() {
        LinkedList<String> resultTest=new LinkedList<>();
        resultTest.add(coach.getUserName()+":"+"\n"+ coach.viewProfile());
        LinkedList<String> result = guest.searchByCategory("Coach");
        assertEquals(result.get(0),resultTest.get(0));
    }

//    @Test
//    public void searchByKeyWord1() {
//      //  assertEquals(guest.searchByName(league.getLeagueName()),league.viewProfile());
//        LinkedList<String>resultTest=new LinkedList<>();
//        resultTest.add( team.getName() + ":" + "\n" + team.viewProfile());
//        LinkedList<String> result = guest.searchByCategory("Team");
//        assertEquals(result.get(0), resultTest.get(0));
//    }
//    @Test
//    public void searchByKeyWord2() {
//        assertNull(guest.searchByName("notExist"),null);
//        LinkedList<String>resultTest=new LinkedList<>();
//        resultTest.add(league.getLeagueName() + " " + "League" + ":" + "\n" + league.viewProfile());
//        resultTest.add(league1.getLeagueName()+" " + "League" + ":" + "\n" + league1.viewProfile());
//        LinkedList<String> result = guest.searchByCategory("League");
//        assertEquals(resultTest.get(0),result.get(0));
//        assertEquals(resultTest.get(1),result.get(1));
//    }

}