package Users;
import AssociationAssets.*;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import System.*;

import java.io.File;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SystemManagerTest {
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
        c= new Coach("1","l","a", null, null);
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
        game= new Game(new Date(2021,10,2020),
                new Time(19,30,0),
                field,team1,team2,r1,r2,r3,season,
                new League("l"));

    }
    @Test
    void closeTeam() {
        try {
            setUp();
            team1.addHomeGame(game);
            FootballSystem.getInstance().addTeamToDB(team1);
            SystemManager systemManager = new SystemManager("new","new","new");
            systemManager.closeTeam(String.valueOf(team1.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void getComplainsAndresponseOnComplain() {
        Player player = new Player("newPlayer", "yossi","cohen", new Date(),EPlayerRole.GoalKeeper);
        SystemManager systemManager = new SystemManager("1","1","1");
        player.submitComplain("new complain");
        player.submitComplain("complain");
        assertEquals(Complains.getInstance().getComplain().size() , 2 );
        List<Pair<String, Fan>> complains = systemManager.getComplains();
        systemManager.responseOnComplain("take care" , complains.get(0));
        assertEquals(Complains.getInstance().getComplain().size() , 1 );
        Complains.getInstance().WriteObjectToFile(new File("C:\\Users\\יובל בן אליעזר\\Desktop\\complain.txt"));
        Logger.getInstance().WriteActionLoggerToFile(new File("C:\\Users\\יובל בן אליעזר\\Desktop\\logger.txt"));

    }


    @Test
    void getLogInformation() {
        Logger.getInstance();
        Coach coach = new Coach("newCoach", "yossi","cohen",ETraining.CDiploma, ECoachRole.AssistantCoach);
        SystemManager systemManager = new SystemManager("1","1","1");
        assertEquals(systemManager.getActionLogInformation().size() , 2);
    }

}