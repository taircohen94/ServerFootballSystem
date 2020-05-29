package AssociationAssets;


import java.sql.Time;
import java.util.Date;

/**
 * Class Event represent a single event in a game's log. It is composed of the accurate time of the event, it's type (an enum),
 * and a description the user may add for additional information.
 * @Authors: Amit Shakarchy, Alon Gutman.
 */
public class Event {

    //region Fields
    Date date;
    Time time;
    EEventType EventType;
    String description;
    //endregion


    public Event(Date date, Time time, EEventType eventType, String description) {
        this.date = date;
        this.time = time;
        EventType = eventType;
        this.description = description;
    }

    //region Setters & Getters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public EEventType getEventType() {
        return EventType;
    }

    public void setEventType(EEventType eventType) {
        EventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //endregion
}
