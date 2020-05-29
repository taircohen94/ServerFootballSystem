package Users;

import AssociationAssets.*;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import System.Complains;
import static org.junit.jupiter.api.Assertions.*;

class FanTest {
    Event e1;
    Event e2;
    Field field;
    Team host, guest;
    Season season;
    League league;
    Game game;
    Referee main, side1, side2;
    Date date;
    List<Event> events;
    int time = 6;
    int hour = 18;

    @Before
    public void setUp() throws Exception {
        String GID = "111";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.parse("2020-04-12");
        Time time = new Time(hour, 30, 0);
        Score score = new Score();
        league = new League("league");
        season = new Season("1993");
        field = new Field("Teddi", "Beer Sheva", 800);
        host = new Team(1, "Barcelona", season, field, null,null);
        guest = new Team(2, "Beitar", season, field, null,null);
        ;
        e1 = new Event(date, time, EEventType.INJURY, "bla");
        e2 = new Event(date, time, EEventType.OFFSIDE, "bli");
        events = new LinkedList<>();
        events.add(e1);
        events.add(e2);
        main = new Referee("1", "main", "main", EReferee.MAIN);
        side1 = new Referee("2", "side1", "side1", EReferee.ASSISTANT);
        side2 = new Referee("3", "side2", "side1", EReferee.ASSISTANT);
        game = new Game(date, time, field, host, guest, main, side1, side2, season, league);
    }

    @Test
    void logout() {
        Fan fan = new Fan("1", "1", "1");
        assertEquals(fan.getStatus(), EStatus.ONLINE);
        fan.logout();
        assertEquals(fan.getStatus(), EStatus.OFFLINE);
    }

    @Test
    void setUserName() {
        Fan fan = new Fan("newFan", "yossi", "cohen");
        assertEquals(fan.getUserName(), "newFan");
        fan.setUserName("yosi");
        assertEquals(fan.getUserName(), "yosi");
        fan.setUserName(null);
        assertEquals(fan.getUserName(), "yosi");
    }

    @Test
    void setfName() {
        Fan fan = new Fan("newFan", "yossi", "cohen");
        assertEquals(fan.getfName(), "yossi");
        fan.setfName("yosi");
        assertEquals(fan.getfName(), "yosi");
        fan.setfName(null);
        assertEquals(fan.getfName(), "yosi");
    }

    @Test
    void setlName() {
        Fan fan = new Fan("newFan", "yossi", "cohen");
        assertEquals(fan.getlName(), "cohen");
        fan.setlName("yosi");
        assertEquals(fan.getlName(), "yosi");
        fan.setlName(null);
        assertEquals(fan.getlName(), "yosi");
    }

    @Test
    void setStatus() {
        Fan fan = new Fan("1", "1", "1");
        assertEquals(fan.getStatus(), EStatus.ONLINE);
        fan.setStatus(EStatus.OFFLINE);
        assertEquals(fan.getStatus(), EStatus.OFFLINE);
    }

    @Test
    void subscribePersonalPage() {
        Player player = new Player("newPlayer", "yossi", "cohen", new Date(), EPlayerRole.GoalKeeper);
        APageEditor pageToFollow = player.getMyPage();
        Fan fan = new Fan("1", "1", "1");
        fan.subscribePersonalPage(pageToFollow);
        player.addFeedToMyPage("newFeed");
        assertEquals(player.getMyPage().getObservers().get(0), fan);

        Coach coach = new Coach("newCoach", "yossi", "cohen", ETraining.CDiploma, ECoachRole.AssistantCoach);
        APageEditor coachPage = coach.getMyPage();
        fan.subscribePersonalPage(coachPage);
        coach.addFeedToMyPage("newFeed");
        assertEquals(coach.getMyPage().getObservers().get(0), fan);

    }

    @Test
    void removeRegisterFromPersonalPage() {
        Player player = new Player("newPlayer", "yossi", "cohen", new Date(), EPlayerRole.GoalKeeper);
        APageEditor pageToFollow = player.getMyPage();
        Fan fan = new Fan("1", "1", "1");
        fan.subscribePersonalPage(pageToFollow);
        player.addFeedToMyPage("newFeed");
        assertEquals(player.getMyPage().getObservers().get(0), fan);
        fan.removeRegisterFromPersonalPage(pageToFollow);
        assertEquals(player.getMyPage().getObservers().size(), 0);


        Coach coach = new Coach("newCoach", "yossi", "cohen", ETraining.CDiploma, ECoachRole.AssistantCoach);
        APageEditor coachPage = coach.getMyPage();
        fan.subscribePersonalPage(coachPage);
        coach.addFeedToMyPage("newFeed");
        assertEquals(coach.getMyPage().getObservers().get(0), fan);
        fan.removeRegisterFromPersonalPage(coachPage);
        assertEquals(coachPage.getObservers().size(), 0);
    }

    @Test
    void submitComplainAndgetResponseForComplain() {
        Fan fan = new Fan("newFan", "yossi", "cohen");
        SystemManager systemManager = new SystemManager("1", "1", "1");
        fan.submitComplain("new complain");
        fan.submitComplain("complain");
        assertEquals(Complains.getInstance().getComplain().size(), 2);
        List<Pair<String, Fan>> complains = systemManager.getComplains();
        systemManager.responseOnComplain("take care", complains.get(0));
        assertEquals(Complains.getInstance().getComplain().size(), 1);
    }

    @Test
    void getSearchHistory() {
        Fan fan = new Fan("newFan", "yossi", "cohen");
        //fan.search("newSearch");
        assertEquals(fan.getSearchHistory().size(), 1);
        assertEquals(fan.getSearchHistory().get(0), "newSearch");

    }


    @Test
    void setSearchHistory() {
        Fan fan = new Fan("newFan", "yossi", "cohen");
        //fan.search("newSearch");
        assertEquals(fan.getSearchHistory().size(), 1);
        assertEquals(fan.getSearchHistory().get(0), "newSearch");
        fan.setSearchHistory(null);
        assertEquals(fan.getSearchHistory().size(), 1);
        assertEquals(fan.getSearchHistory().get(0), "newSearch");
    }

    @Test
    void search() {
        Fan fan = new Fan("newFan", "yossi", "cohen");
        //fan.search("newSearch");
        assertEquals(fan.getSearchHistory().size(), 1);
        assertEquals(fan.getSearchHistory().get(0), "newSearch");
        //fan.search(null);
        assertEquals(fan.getSearchHistory().size(), 1);
        assertEquals(fan.getSearchHistory().get(0), "newSearch");
    }


    @Test
    void subscribeGame() {
        try {
            setUp();
            Fan fan = new Fan("1", "1", "1");
            fan.subscribeGames(game);
            assertEquals(game.getObservers().get(0), fan);
            fan.removeRegisterFromGames(game);
            assertEquals(game.getObservers().size(), 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}