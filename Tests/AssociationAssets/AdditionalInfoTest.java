package AssociationAssets;

import System.FootballSystem;
import Users.Coach;
import Users.Player;
import Users.TeamManager;
import Users.TeamOwner;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdditionalInfoTest {
    static AdditionalInfo additionalInfo;
    static Season season;
    static Team team;
    static Player player;
    static Coach coach;
    static TeamManager manager;
    static TeamOwner owner;
    static Field field;


    @BeforeClass
    public static void setUp() throws Exception {
        try {
            FootballSystem.getInstance().signIn("tair", "!1", "tair", "cohen");
            owner = (TeamOwner) FootballSystem.getInstance().creatingTeamOwner("tair", "tair", "cohen");
            field = FootballSystem.getInstance().createField("Camp Nou", "Barcelona", 90000);
            season = new Season("2020");
            FootballSystem.getInstance().addSeasonToDB(season);
            team = new Team(1, "Barcelona", season, field, null, owner);
            FootballSystem.getInstance().addTeamToDB(team);
            team.addSeasonToTeam(season);
            owner.addPlayer(team,season,"10-Messi","12","La","al",null,null);
            owner.addCoach(team,season,"pep47","122","pep","Guardiola",null,null);
            owner.addTeamManager(team,season,"yuval","12","yuval","lala");
            additionalInfo = team.getAdditionalInfoWithSeasons().get(season.getYear());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void setSeason() {
        Season season1 = new Season("2021");
        additionalInfo.setSeason(season1);
        assertEquals(team.getCurrentSeason().year, season1.year);
    }

    @Test
    public void findManager() {
        assertNotNull(additionalInfo.findManager("yuval"));
        assertTrue(additionalInfo.findManager("lal") == null);
    }

    @Test
    public void removePlayer() {
        assertEquals(additionalInfo.getPlayers().size(), 1);
        additionalInfo.removePlayer("10-Mess");
        assertEquals(additionalInfo.getPlayers().size(), 1);
        assertEquals(additionalInfo.getPlayers().size(), 1);
        additionalInfo.removePlayer("10-Messi");
        assertEquals(additionalInfo.getPlayers().size(), 0);
    }

    @Test
    public void removeCoach() {
        assertEquals(additionalInfo.getCoaches().size(), 2);
        additionalInfo.removeCoach("shimi");
        assertEquals(additionalInfo.getCoaches().size(), 1);
        additionalInfo.removeCoach("shimi");
        assertEquals(additionalInfo.getCoaches().size(), 1);
    }

    @Test
    public void addTeamOwner() {
        assertEquals(true,additionalInfo.addTeamOwner("shimi", "owner1"));
        assertEquals(additionalInfo.getTeamOwnersHashSet().size(), 2);
        assertEquals(false,additionalInfo.addTeamOwner("shimi", "owner1"));
        assertEquals(additionalInfo.getTeamOwnersHashSet().size(), 2);
        assertEquals(false,additionalInfo.addTeamOwner("", "owner1"));
        assertEquals(additionalInfo.getTeamOwnersHashSet().size(), 2);
        assertEquals(false,additionalInfo.addTeamOwner(null, "owner1"));
        assertEquals(additionalInfo.getTeamOwnersHashSet().size(), 2);
        assertEquals(false,additionalInfo.addTeamOwner("shimi", ""));
        assertEquals(additionalInfo.getTeamOwnersHashSet().size(), 2);
        assertEquals(false,additionalInfo.addTeamOwner("shimi", null));
        assertEquals(additionalInfo.getTeamOwnersHashSet().size(), 2);
    }

    @Test
    public void addPlayer() {
        assertEquals(true,additionalInfo.addPlayer("shimi"));
        assertEquals(additionalInfo.getPlayers().size(), 1);
        assertEquals(false,additionalInfo.addPlayer("shimi"));
        assertEquals(additionalInfo.getPlayers().size(), 1);
        assertEquals(false,additionalInfo.addPlayer(""));
        assertEquals(additionalInfo.getPlayers().size(), 1);
        assertEquals(false,additionalInfo.addPlayer(null));
        assertEquals(additionalInfo.getPlayers().size(), 1);
    }

    @Test
    public void addCoach() {
        assertEquals(true,additionalInfo.addCoach("shimi"));
        assertEquals(additionalInfo.getCoaches().size(), 2);
        assertEquals(false,additionalInfo.addCoach("shimi"));
        assertEquals(additionalInfo.getCoaches().size(), 2);
        assertEquals(false,additionalInfo.addCoach(""));
        assertEquals(additionalInfo.getCoaches().size(), 2);
        assertEquals(false,additionalInfo.addCoach(null));
        assertEquals(additionalInfo.getCoaches().size(), 2);
    }

    @Test
    public void addManager() {
        assertEquals(true,additionalInfo.addManager("shimi", "owner1"));
        assertEquals(additionalInfo.getTeamManagersHashSet().size(), 2);
        assertEquals(false,additionalInfo.addManager("shimi", "owner1"));
        assertEquals(additionalInfo.getTeamManagersHashSet().size(), 2);
        assertEquals(false,additionalInfo.addManager("", "owner1"));
        assertEquals(additionalInfo.getTeamManagersHashSet().size(), 2);
        assertEquals(false,additionalInfo.addManager(null, "owner1"));
        assertEquals(additionalInfo.getTeamManagersHashSet().size(), 2);
        assertEquals(false,additionalInfo.addManager("shimi", ""));
        assertEquals(additionalInfo.getTeamManagersHashSet().size(), 2);
        assertEquals(false,additionalInfo.addManager("shimi", null));
        assertEquals(additionalInfo.getTeamManagersHashSet().size(), 2);
    }

    @Test
    public void removeManager() {
        assertEquals(additionalInfo.getTeamManagersHashSet().size(), 2);
        additionalInfo.removeManager("yuval","tair");
        assertEquals(additionalInfo.getTeamManagersHashSet().size(), 1);
        additionalInfo.removeManager("shimi","lala");
        assertEquals(additionalInfo.getTeamManagersHashSet().size(), 1);
    }

    @Test
    public void removeTeamOwner() {
        assertEquals(additionalInfo.getTeamOwnersHashSet().size(), 2);
        additionalInfo.removeTeamOwner("shimi","owner1");
        assertEquals(additionalInfo.getTeamOwnersHashSet().size(), 1);
        additionalInfo.removeTeamOwner("tair","owner1");
        assertEquals(additionalInfo.getTeamOwnersHashSet().size(), 1);
    }

    @Test
    public void findPlayer() {
//        assertNull(team.findPlayer(player.getUserName()));
//        team.addPlayer(player);
//        assertEquals(team.findPlayer(player.getUserName()),player);
    }

    @Test
    public void findCoach() {
        assertNotNull(additionalInfo.findCoach("pep47"));
        assertTrue(additionalInfo.findCoach("lal") == null);
    }

    @Test
    public void findTeamOwner() {
        assertNotNull(additionalInfo.findTeamOwner("tair"));
        assertTrue(additionalInfo.findTeamOwner("lal") == null);
    }

    @Test
    public void whoNominateTeamManager() {
        assertEquals(true,additionalInfo.whoNominateTeamManager("shimi","owner1"));
        assertEquals(false,additionalInfo.whoNominateTeamManager("shimi","owner"));
    }

    @Test
    public void whoNominateTeamOwner() {
        assertEquals(true,additionalInfo.whoNominateTeamManager("shimi","owner1"));
        assertEquals(false,additionalInfo.whoNominateTeamManager("shimi","owner"));
    }
}