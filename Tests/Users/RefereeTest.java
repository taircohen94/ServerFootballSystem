package Users;
import AssociationAssets.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RefereeTest {
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
        String GID = "11";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.parse("2020-04-12");
        Time time = new Time(hour, 30, 0);
        Score score = new Score();
        league = new League("league");
        season = new Season("1993");
        field = new Field("Teddi", "Beer Sheva", 800);
        host = new Team(1,"Barcelona", null, null, null,null);
        guest = new Team(2, "Beitar",null, null, null,null);
        ;
        e1 = new Event(date, time, EEventType.INJURY, "bla");
        e2 = new Event(date, time, EEventType.OFFSIDE, "bli");
        events = new LinkedList<>();
        events.add(e1);
        events.add(e2);
        main = new Referee("1", "main", "main",EReferee.MAIN);
        side1 = new Referee("2", "side1", "side1",EReferee.ASSISTANT);
        side2 = new Referee("3", "side2", "side1",EReferee.ASSISTANT);
        game = new Game(date, time, field, host, guest, main, side1, side2, season, league);
    }

    @Test
    void getMyGames() {
        try {
            setUp();
            main.addGame(game);
            assertEquals(main.getMyGames().size() , 1);
            main.addGame(null);
            assertEquals(main.getMyGames().size() , 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void setMyGames() {
        try {
            setUp();
            List<Game> nullCase = null;
            assertEquals(main.getMyGames().size() , 0);
            main.setMyGames(nullCase);
            assertEquals(main.getMyGames().size() , 0);
            List<Game> games = new LinkedList<>();
            games.add(game);
            main.setMyGames(games);
            assertEquals(main.getMyGames().size() , 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTraining() {
        try {
            setUp();
            assertEquals(main.getTraining(),EReferee.MAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void setTraining() {
        try {
            setUp();
            assertEquals(main.getTraining(),EReferee.MAIN);
            main.setTraining(null);
            assertEquals(main.getTraining(),EReferee.MAIN);
            main.setTraining(EReferee.ASSISTANT);
            assertEquals(main.getTraining(),EReferee.ASSISTANT);
            main.setTraining(EReferee.MAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void viewAssignedGames() {
        try {
            setUp();
            assertEquals(main.viewAssignedGames().size() , 0);
            main.addGame(game);
            assertEquals(main.viewAssignedGames().size() , 1);
            main.addGame(null);
            assertEquals(main.viewAssignedGames().size() , 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addGame() {
        try {
            setUp();
            assertEquals(main.getMyGames().size() , 0);
            main.addGame(game);
            assertEquals(main.getMyGames().size() , 1);
            main.addGame(null);
            assertEquals(main.getMyGames().size(), 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addEventToAssignedGame() {
        try {
            setUp();
            side1.addGame(game);
            side1.addEventToAssignedGame(game.getGID(),e1.getEventType(),e1.getDescription());
            assertEquals(game.getEvents().size()  , 1);
            game.setTime(new Time(hour+time, 0, 0));
            side1.addEventToAssignedGame(game.getGID(),e1.getEventType(),e1.getDescription());
            assertEquals(game.getEvents().size()  , 1);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void updateEventToAssignedGame() {
        try {
            setUp();
            side1.addGame(game);
            side1.addEventToAssignedGame(game.getGID(),e1.getEventType(),e1.getDescription());
            assertEquals(game.getEvents().size()  , 1);
            side1.updateEventToAssignedGame(game.getGID(),side1.getIndexOfEvent(game.getGID(),e1.getEventType(),e1.getDescription()),e1.getEventType(),"Change");
            assertEquals(game.getEvents().size()  , 1);
            assertEquals(game.getEvents().get(0).getDescription(),"Change");
            side1.addEventToAssignedGame(game.getGID(),e2.getEventType(),e2.getDescription());

            game.setTime(new Time(hour+time, 0, 0));
            side1.updateEventToAssignedGame(game.getGID(),side1.getIndexOfEvent(game.getGID(),e2.getEventType(),e2.getDescription()),e2.getEventType(),"Change");
            assertEquals(game.getEvents().size()  , 2);
            assertNotEquals(game.getEvents().get(1).getDescription(),"Change");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void removeEventFromAssignedGame() {
        try {
            setUp();
            side1.addGame(game);
            side1.addEventToAssignedGame(game.getGID(),e1.getEventType(),e1.getDescription());
            assertEquals(game.getEvents().size()  , 1);
            side1.removeEventFromAssignedGame(game.getGID(),side1.getIndexOfEvent(game.getGID(),e1.getEventType(),e1.getDescription()));
            assertEquals(game.getEvents().size()  , 0);

            side1.addEventToAssignedGame(game.getGID(),e1.getEventType(),e1.getDescription());
            game.setTime(new Time(hour+time, 0, 0));
            assertEquals(game.getEvents().size()  , 1);
            side1.removeEventFromAssignedGame(game.getGID(),side1.getIndexOfEvent(game.getGID(),e1.getEventType(),e1.getDescription()));
            assertEquals(game.getEvents().size()  , 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void editEventsAfterGameOver() {

        try {
            setUp();
            main.addGame(game);
            main.addEventToAssignedGame(game.getGID(),e1.getEventType(),e1.getDescription());
            assertEquals(game.getEvents().size(), 1);
            game.setTime(new Time(hour-2, 0, 0));
            main.editEventsAfterGameOver(game.getGID(),main.getIndexOfEvent(game.getGID(),e1.getEventType(),e1.getDescription()),e1.getEventType(),"Change");
            assertEquals(game.getEvents().size()  , 1);
            assertEquals(game.getEvents().get(0).getDescription(),"Change");

            game.setTime(new Time(hour, 0, 0));
            main.addEventToAssignedGame(game.getGID(),e1.getEventType(),e1.getDescription());
            main.addEventToAssignedGame(game.getGID(),e2.getEventType(),e2.getDescription());

            game.setTime(new Time(hour-5, 0, 0));
            assertEquals(game.getEvents().size()  , 3);
            main.editEventsAfterGameOver(game.getGID(),main.getIndexOfEvent(game.getGID(),e1.getEventType(),e1.getDescription()),e1.getEventType(),"Change");
            assertEquals(game.getEvents().get(0).getDescription(),"Change");
            assertEquals(game.getEvents().size()  , 3);


            game.setTime(new Time(hour-8, 0, 0));
            assertEquals(game.getEvents().size()  , 3);
            main.editEventsAfterGameOver(game.getGID(),main.getIndexOfEvent(game.getGID(),e1.getEventType(),e1.getDescription()),e1.getEventType(),"Change1");
            assertEquals(game.getEvents().get(0).getDescription(),"Change");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void removeEventsAfterGameOver() {
        try {
            setUp();
            main.addGame(game);
            main.addEventToAssignedGame(game.getGID(),e1.getEventType(),e1.getDescription());
            assertEquals(game.getEvents().size(), 1);
            main.removeEventFromAssignedGame(game.getGID(),main.getIndexOfEvent(game.getGID(),e1.getEventType(),e1.getDescription()));
            assertEquals(game.getEvents().size()  , 0);

            main.addEventToAssignedGame(game.getGID(),e1.getEventType(),e1.getDescription());
            main.addEventToAssignedGame(game.getGID(),e2.getEventType(),e2.getDescription());

            game.setTime(new Time(hour-5, 0, 0));
            assertEquals(game.getEvents().size()  , 2);
            main.removeEventsAfterGameOver(game.getGID(),main.getIndexOfEvent(game.getGID(),e1.getEventType(),e1.getDescription()));
            assertEquals(game.getEvents().size()  , 1);


            game.setTime(new Time(hour-8, 0, 0));
            assertEquals(game.getEvents().size()  , 1);
            main.removeEventsAfterGameOver(game.getGID(),main.getIndexOfEvent(game.getGID(),e2.getEventType(),e2.getDescription()));
            assertEquals(game.getEvents().size()  , 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getIndexOfEvent() {
        try {
            setUp();
            side1.addGame(game);
            side1.addEventToAssignedGame(game.getGID(),e1.getEventType(),e1.getDescription());
            int index = side1.getIndexOfEvent(game.getGID(),e1.getEventType(),e1.getDescription());
            assertEquals(index , 0);
            index = side1.getIndexOfEvent(1,null,null);
            assertEquals(index,-1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}