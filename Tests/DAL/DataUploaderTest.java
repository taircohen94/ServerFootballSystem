package DAL;

import AssociationAssets.League;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import System.*;
public class DataUploaderTest {

    JDBCConnector connector;
    

    @Test
    void uploadDataFans() {
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        assertEquals(FootballSystem.getInstance().getFansHashMap().size(),67);
    }
    @Test
    void uploadDataSeasons() {
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        assertEquals(FootballSystem.getInstance().getSeasonDB().getAllSeasons().size(),1);
    }

    @Test
    void uploadDataLeagues() {
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        HashMap<String, League> allLeagues = FootballSystem.getInstance().getLeagueDB().getAllLeagues();
        assertEquals(allLeagues.size(),1);
    }
    @Test
    void uploadDataTeams() {
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        assertEquals(FootballSystem.getInstance().getTeamDB().getAllTeams().size(),2);
    }
    @Test
    void uploadDataFields() {
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        assertEquals(FootballSystem.getInstance().getFieldDB().getAllFields().size(),2);
    }
    @Test
    void uploadDataGames() {
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        assertEquals(FootballSystem.getInstance().getGameDB().getAllGames().size(),1);
    }
}