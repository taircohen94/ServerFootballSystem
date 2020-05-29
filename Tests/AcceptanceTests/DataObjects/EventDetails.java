package AcceptanceTests.DataObjects;

import AssociationAssets.EEventType;
import AssociationAssets.Event;

public class EventDetails {
    public int gameID;
    public int eventIndex;
    public EEventType eventType;
    public String description;

    public EventDetails(int gameID, int eventIndex, EEventType eventType, String description) {
        this.gameID = gameID;
        this.eventIndex = eventIndex;
        this.eventType = eventType;
        this.description = description;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getEventIndex() {
        return eventIndex;
    }

    public void setEventIndex(int eventIndex) {
        this.eventIndex = eventIndex;
    }

    public EEventType getEventType() {
        return eventType;
    }

    public void setEventType(EEventType eventType) {
        this.eventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
