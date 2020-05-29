package DAL;

import AssociationAssets.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import System.FootballSystem;

import java.util.HashMap;

class JDBCConnectorTest {

    @Test
    void connectDBUploadData() {
        JDBCConnector connector= new JDBCConnector();
        connector.connectDBUploadData();
        HashMap<String, Team> allTeams = FootballSystem.getInstance().getTeamDB().getAllTeams();
        connector.connectDBSaveData();
    }
}