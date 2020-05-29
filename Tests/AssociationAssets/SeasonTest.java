package AssociationAssets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SeasonTest {

    Season season;
    League league;
    @Before
    public void setUp() throws Exception {
        season = new Season("2020");
        league = new League("Champions League");
        league.addSeasonToLeague(season);
        season.addLeagueToSeason(season.year,league.seasonBinders );
    }
    @After
    public void after(){

    }

    @Test
    public void addLeague() {
        assertEquals(season.leagueBinders.size(),1);
    }
    @Test
    public void removeLeague() {
        season.removeLeagueFromSeason(season.leagueBinders.get("Champions League").league);
        assertEquals(season.leagueBinders.size(),0);
    }

    @Test
    public void getLeague() {
       assertNotEquals(season.leagueBinders.get("Champions League"),null);
    }

    @Test
    public void getLeagueAfterRemove() {
        season.removeLeagueFromSeason(season.leagueBinders.get("Champions League").league);
        assertEquals(season.leagueBinders.get("Champions League"),null);
    }
}