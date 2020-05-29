package AssociationAssets;

import Users.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SeasonLeagueBinderTest {
    Team team1, team2;
    Player p;
    Coach c;
    Field field;
    TeamManager m;
    TeamOwner o;
    Season season;
    League league;
    Referee r1,r2,r3;
    Game game1;
    Game game2;
    SeasonLeagueBinder seasonLeagueBinder;

    @Before
        public void setUp() throws Exception {
            league = new League("Champions League");
            c= new Coach("1","a","a", null, null);
            p= new Player("1", "a", "a", null, null);
            m= new TeamManager("1", "a", "a");
            o= new TeamOwner("1", "a", "a");
            field= new Field("Teddi", "Beer Sheva", 800);
            season=new Season("2020");
            r1= new Referee("1","a","a",EReferee.MAIN);
            r2= new Referee("2","a","a",EReferee.VAR);
            r3= new Referee("3","a","a",EReferee.VAR);
            team1= new Team(1,"Barcelona", season, null,null,null);
            team2= new Team(2,"Real Madrid", season, null,null,null);
            game1= new Game(new Date(10,10,2020),
                new Time(19,30,0),
                field,team1,team2,r1,r2,r3,season,
                    league);
        game2= new Game(new Date(5,4,2020),
                new Time(20,00,0),
                field,team2,team1,r1,r3,r2,season,
                league);
            HashMap <Integer,Game> games = new HashMap<>();
            games.put(123,game1);
            games.put(113,game2);
            HashMap <String,Team> teams = new HashMap<>();
            teams.put(team1.getName(),team1);
            teams.put(team2.getName(),team2);
            league.addSeasonToLeague(season); //creating Binder in league
            league.addTeamsToLeague("2020",teams);
            league.addGamesToLeague("2020",games);
            seasonLeagueBinder = season.leagueBinders.get("Champions League");
    }


    @Test
    public void assertTeams() {
        assertEquals(seasonLeagueBinder.teams.size(),2);
    }
    @Test
    public void assertGames() {
        assertEquals(seasonLeagueBinder.games.size(),2);
    }



}
