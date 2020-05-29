package AssociationAssets;

import Users.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

public class TeamTest {

    Team team1, team2;
    Player p;
    Coach c;
    Field field;
    TeamManager m;
    TeamOwner o;
    Season season;
    Referee r1,r2,r3;
    Game game;
    @Before
    public void setUp() throws Exception {
        c= new Coach("1","a","a", null, null);
        p= new Player("1", "a", "a", null, null);
        m= new TeamManager("1", "a", "a");
        o= new TeamOwner("1", "a", "a");
        field= new Field("Teddy", "Jerusalem", 31000);
        season=new Season("season");

        r1= new Referee("1","a","a",EReferee.MAIN);
        r2= new Referee("2","a","a",EReferee.VAR);
        r3= new Referee("3","a","a",EReferee.VAR);
        team1= new Team(1,"Barcelona", season, null,null,null);
        team2= new Team(2, "Beitar",season, null,null,null);
        team2.addField(field);
        game= new Game(new Date(10,10,2020),
                new Time(19,30,0),
                field,team1,team2,r1,r2,r3,season,
                new League("l"));

    }

    @Test
    public void checkActivness() {
        assertEquals(team1.getIsActive(),true);
    }

    @Test
    public void fieldAdding() {

        assertEquals(team1.getFields().size(),0);
        assertEquals(team2.getFields().size(),1);
        team2.removeField("Teddy");
        assertEquals(team2.getFields().size(),0);

    }
    @Test
    public void cheddkActivness() {
        assertEquals(team1.getIsActive(),true);
    }


//    @Test
//    public void findPlayer() {
//        team1.addPlayer(p);
//        assertEquals(((Player)team1.findPlayer("1")).getfName(),"a");
//    }
//
//    @Test
//    public void addCoach() {
//        team1.addCoach(c);
//        assertEquals(team1.getCoaches().size(),1);
//    }
//
//    @Test
//    public void findCoach() {
//        team1.addCoach(c);
//        assertEquals(((Coach)team1.findCoach("1")).getfName(),"a");
//    }
//
//    @Test
//    public void addManager() {
//        team1.addManager(m);
//        assertEquals(team1.getManagers().size(),1);
//    }
//
//    @Test
//    public void findManager() {
//        team1.addManager(m);
//        assertEquals(((TeamManager)team1.findManager("1")).getfName(),"a");
//    }
//
//    @Test
//    public void addTeamOwner() {
//        team1.addTeamOwner(o);
//        assertEquals(team1.getOwners().size(),1);
//    }
//
//    @Test
//    public void findTeamOwner() {
//        team1.addTeamOwner(o);
//        assertEquals(((TeamOwner)team1.findTeamOwner("1")).getfName(),"a");
//    }
//
//    @Test
//    public void removePlayer() {
//        team1.addPlayer(p);
//        team1.removePlayer(p);
//        assertEquals(team1.getPlayers().size(),0);
//
//    }
//
//    @Test
//    public void removeCoach() {
//        team1.addCoach(c);
//        team1.removeCoach(c);
//        assertEquals(team1.getCoaches().size(),0);
//    }
//
//    @Test
//    public void removeManager() {
//        team1.addManager(m);
//        team1.removeManager(m);
//        assertEquals(team1.getManagers().size(),0);
//    }
//
//    @Test
//    public void removeTeamOwner() {
//        team1.addTeamOwner(o);
//        team1.removeTeamOwner(o);
//        assertEquals(team1.getOwners().size(),0);
//    }
//
//    @Test
//    public void addame() {
//        team1.addame(game);
//        assertEquals(team1.getames().size(),1);
//    }
//
//    @Test
//    public void addAwayGame() {
//        team1.addAwayGame(game);
//        assertEquals(team1.getAwayGames().size(),1);
//    }
//
//    @Test
//    public void removeHomeGame() {
//        team1.addHomeGame(game);
//        team1.removeHomeGame(game);
//        assertEquals(team1.getHomeGames().size(),0);
//    }
//
//    @Test
//    public void removeAwayGame() {
//        team1.addAwayGame(game);
//        team1.removeAwayGame(game);
//        assertEquals(team1.getAwayGames().size(),0);
//    }
//    @Test
//    public void findHomeGame() {
//        assertNull(team1.findHomeGame(game.getGID()));
//        team1.addHomeGame(game);
//        assertNotNull(team1.findHomeGame(game.getGID()));
//    }
//    @Test
//    public void findAwayGame() {
//        assertNull(team1.findAwayGame(game.getGID()));
//        team1.addAwayGame(game);
//        assertNotNull(team1.findAwayGame(game.getGID()));
//    }

}