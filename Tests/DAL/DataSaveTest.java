package DAL;

import AssociationAssets.*;
import PoliciesAndAlgorithms.SimpleGamesAssigningPolicy;
import Users.*;
import org.junit.Test;
import System.FootballSystem;

import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

public class DataSaveTest {

    JDBCConnector connector;

    @Test
    public void saveAdditionalInfo() { // CHECKED- with no changes.
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        connector.databaseManager.startConnection();
        connector.dataSave.saveAdditionalInfo();
        connector.databaseManager.closeConnection();
    }

    @Test
    public void saveGames() { // CHECKED
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        FootballSystem.getInstance().getGameDB().getAllGames().get(1).setScore(0,0);
        connector.databaseManager.startConnection();
        connector.dataSave.saveGames();
        connector.databaseManager.closeConnection();
    }

    @Test
    public void saveTeams() { // CHECKED
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        Field teddi=FootballSystem.getInstance().getFieldDB().getAllFields().get("Teddi");
        FootballSystem.getInstance().getTeamDB().getAllTeams().get("Beitar Jerusalem").setMainField(teddi);
        connector.databaseManager.startConnection();
        connector.dataSave.saveTeams();
        connector.databaseManager.closeConnection();
    }

    @Test
    public void saveSeasons() { // CHECKED
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        FootballSystem.getInstance().getSeasonDB().getAllSeasons().put("2021",new Season("2021"));
        connector.databaseManager.startConnection();
        connector.dataSave.saveSeasons();
        connector.databaseManager.closeConnection();
    }

    @Test
    public void saveLeagues() { // CHECKED
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        FootballSystem.getInstance().getLeagueDB().getAllLeagues().put("Youth League", new League("Youth League"));
        connector.databaseManager.startConnection();
        connector.dataSave.saveLeagues();
        connector.databaseManager.closeConnection();
    }

    @Test
    public void saveFields() { // CHECKED
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        FootballSystem.getInstance().getFieldDB().getAllFields().put("AmitF", new Field("AmitF", "AmitFCity", 3000));
        connector.databaseManager.startConnection();
        connector.dataSave.saveFields();
        connector.databaseManager.closeConnection();
    }

    @Test
    public void savePasswordsUsers() { // CHECKED
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        FootballSystem.getInstance().getFansHashMap().put("AmitUP", new Fan("AmitUP","AmitUP","AmitUP"));
        FootballSystem.getInstance().getSecuritySystem().getUsersHashMap("iseFab5").put("AmitUP","AmitUP");
        connector.databaseManager.startConnection();
        connector.dataSave.saveFans();
        connector.dataSave.savePasswordsUsers();
        connector.databaseManager.closeConnection();
    }

    @Test
    public void saveFans() { // CHECKED
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        FootballSystem.getInstance().getFansHashMap().get("amit").setlName("FUNNNN");
        connector.databaseManager.startConnection();
        connector.dataSave.saveFans();
        connector.databaseManager.closeConnection();
    }

    @Test
    public void saveReferees() { // CHECKED
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        Referee ref= new Referee("AmitRef","Amit","Amit", EReferee.VAR);
        FootballSystem.getInstance().getRefereeMap().put("AmitRef", ref);
        FootballSystem.getInstance().getFansHashMap().put("AmitRef",ref);
        connector.databaseManager.startConnection();
        connector.dataSave.saveFans();
        connector.dataSave.saveReferees();
        connector.databaseManager.closeConnection();
    }

    @Test
    public void saveRFAs() { // CHECKED
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        RepresentativeFootballAssociation rep= new RepresentativeFootballAssociation("AmitRep","AmitRep", "AmitRep",new SimpleGamesAssigningPolicy());
        FootballSystem.getInstance().getRepresentativeFootballAssociationMap().put("AmitRep",rep);
        FootballSystem.getInstance().getFansHashMap().put("AmitRep",rep);
        connector.databaseManager.startConnection();
        connector.dataSave.saveFans();
        connector.dataSave.saveRFAs();
        connector.databaseManager.closeConnection();

    }

    @Test
    public void saveSystemManagers() { // CHECKED
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        SystemManager systemManager= new SystemManager("AmitSysMng","AmitSysMng","AmitSysMng");
        FootballSystem.getInstance().getSystemManagerMap().put("AmitSysMng",systemManager);
        FootballSystem.getInstance().getFansHashMap().put("AmitSysMng",systemManager);
        connector.databaseManager.startConnection();
        connector.dataSave.saveFans();
        connector.dataSave.saveSystemManagers();
        connector.databaseManager.closeConnection();
    }

    @Test
    public void saveTeamOwners() { // CHECKED
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        TeamOwner teamOwner= new TeamOwner("AmitTO","AmitTO","AmitTO");
        FootballSystem.getInstance().getTeamOwnerMap().put("AmitTO",teamOwner);
        FootballSystem.getInstance().getFansHashMap().put("AmitTO",teamOwner);
        connector.databaseManager.startConnection();
        connector.dataSave.saveFans();
        connector.dataSave.saveTeamOwners();
        connector.databaseManager.closeConnection();
    }

    @Test
    public void saveTeamManagers() { // CHECKED
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        TeamManager teamManager= new TeamManager("AmitTM","AmitTM","AmitTM");
        FootballSystem.getInstance().getTeamManagerMap().put("AmitTM",teamManager);
        FootballSystem.getInstance().getFansHashMap().put("AmitTM",teamManager);
        connector.databaseManager.startConnection();
        connector.dataSave.saveFans();
        connector.dataSave.saveTeamManagers();
        connector.databaseManager.closeConnection();
    }

    @Test
    public void saveCoaches() { // CHECKED
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        Coach coach= new Coach("AmitC","AmitC","AmitC",ETraining.CDiploma,ECoachRole.AssistantCoach);
        FootballSystem.getInstance().getCoachMap().put("AmitC",coach);
        FootballSystem.getInstance().getFansHashMap().put("AmitC",coach);
        connector.databaseManager.startConnection();
        connector.dataSave.saveFans();
        connector.dataSave.saveCoaches();
        connector.databaseManager.closeConnection();
    }

    @Test
    public void savePlayers() { // CHECKED
        connector= new JDBCConnector();
        connector.connectDBUploadData();
        Player player= new Player("AmitP","AmitP","AmitP",new Date(17/10/1995),EPlayerRole.Forward);
        FootballSystem.getInstance().getPlayerMap().put("AmitP",player);
        FootballSystem.getInstance().getFansHashMap().put("AmitP",player);
        connector.databaseManager.startConnection();
        connector.dataSave.saveFans();
        connector.dataSave.savePlayers();
        connector.databaseManager.closeConnection();
    }
}