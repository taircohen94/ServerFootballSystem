package IntegrationTests;

import AssociationAssets.*;
import Users.Referee;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameStub extends Game {


    int selector;
    List<Event> events;
    /**
     * The constructor validates that a game does not occur between a team to itself,
     * and there are 3 different referees
     *
     * @param date
     * @param time
     * @param field
     * @param host
     * @param guest
     * @param main
     * @param side1
     * @param side2
     * @param season
     * @param league
     */
    public GameStub(Date date, Time time, Field field, Team host, Team guest, Referee main, Referee side1, Referee side2, Season season, League league,int selector) throws Exception {
        super(date, time, field, host, guest, main, side1, side2, season, league);
        this.selector = selector;
        events = new ArrayList<>();

    }
    public void setSelector(int selector) {
        this.selector = selector;
    }
    @Override
    public boolean isUpdatable(int hoursSinceGameStarted) {
        if(selector == 1){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void addEvent(EEventType eventType, String description) {
        if(selector == 1){
            Time time = Time.valueOf(LocalTime.now());
            Event event = new Event(null, time, eventType, description);
            events.add(event);
        }
    }

    @Override
    public void removeEvent(int eventIndex) {
        if(selector == 1){
            events.remove(eventIndex);
        }
    }

    @Override
    public void editEvent(int eventIndex, EEventType eventType, String description) {
        if(selector == 1){
            Event event = events.get(eventIndex);// We need to verify that the modification affects the node in the list.
            event.setEventType(eventType);
            event.setDescription(description);
        }
    }

    @Override
    public List<Event> getEvents() {
        return this.events;
    }


}
