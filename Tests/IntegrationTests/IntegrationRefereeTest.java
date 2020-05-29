package IntegrationTests;

import AssociationAssets.EEventType;
import AssociationAssets.Field;
import AssociationAssets.Season;
import Users.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Time;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class IntegrationRefereeTest {
    Referee referee;
    Referee referee2;
    Referee referee3;
    TeamStub teamStub1;
    TeamStub teamStub2;
    GameStub gameStub;
    Field field;

    @Before
    public void set_Up() throws Exception {
        referee = new Referee("ref","fname","lname",EReferee.MAIN);
        referee2= new Referee("ref1","fname","lname",EReferee.ASSISTANT);
        referee3= new Referee("ref2","fname","lname",EReferee.ASSISTANT);
        field = new Field("d","e",1);
        teamStub1 = new TeamStub(2,"team1",new Season("2020"),field,null,new TeamOwner("a","b","c"),1);
        teamStub2 = new TeamStub(3,"team2",new Season("2020"),field,null,new TeamOwner("b","b","c"),1);
        gameStub = new GameStub(new Date(),new Time(23),null,teamStub1,teamStub2,referee,referee2,referee3,null,null,1);
        referee.addGame(gameStub);
        gameStub.addEvent(EEventType.GOALHOST,"amazing goal by tair");
    }

    @Test
    public void removeEventAfterGame(){
        referee.removeEventsAfterGameOver(2,0);
        assertEquals(referee.getMyGames().get(0).getEvents().size(),0);

    }
    @Test
    public void removeEventDuringGame() throws Exception{
        referee.removeEventFromAssignedGame(1,0);
        assertEquals(referee.getMyGames().get(0).getEvents().size(),0);

    }
    @Test
    public void editEventsAfterGameOver(){
        referee.editEventsAfterGameOver(3,0,EEventType.GOALHOST,"amazing goal by yuval");
        assertEquals(referee.getMyGames().get(0).getEvents().get(0).getDescription(),"amazing goal by yuval");

    }
}