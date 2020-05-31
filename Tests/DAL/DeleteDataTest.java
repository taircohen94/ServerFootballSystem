package DAL;

import BL.AssociationAssets.Event;
import BL.AssociationAssets.Game;
import BL.System.FootballSystem;
import org.junit.Test;

public class DeleteDataTest {
    JDBCConnector connector;

    @Test
    public void deleteEvent() {
        connector = new JDBCConnector();
        connector.connectDBUploadData();
        Game g= FootballSystem.getInstance().getGameDB().getAllGames().get(1);
        Event event= g.getEvents().get(0);
        connector.deleteEvent(event, 1);
    }




}