package AcceptanceTests.Tests;
import AcceptanceTests.DataObjects.TeamDetails;
import AcceptanceTests.SystemOperations.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UC2Tests {
    ISystemOperationsBridge systemOperations;
    TeamDetails registeredTeam;
    @Before
    public void Setup(){
        systemOperations = new RealSystemOperations();
        registeredTeam = systemOperations.getRegisteredTeamForTest();
    }

    // Create new group
    @Test
    public void CreateNewTeamTest_tryToCreateNewTeam_SuccessScenario(){
        assertTrue(systemOperations.createNewTeam("newTeam","La Liga","2020","Blomfield"));
    }

    @Test
    public void CreateNewTeamTest_tryToCreateNewTeamWithExistTeamName_FailureScenario(){
        assertFalse(systemOperations.createNewTeam(registeredTeam.getName(),registeredTeam.getLeagueName(),registeredTeam.getSeasonYear(),registeredTeam.getFieldName()));
    }

    @Test
    public void CreateNewTeamTest_tryToCreateNewTeamWithNonExistSeasonYear_FailureScenario(){
        assertFalse(systemOperations.createNewTeam("NonExistSeasonYear","La Liga","NonExistSeasonYear","Blomfield"));
    }

    @Test
    public void CreateNewTeamTest_tryToCreateNewTeamWithNonExistLeagueName_FailureScenario(){
        assertFalse(systemOperations.createNewTeam("NonExistLeagueName","NonExistLeagueName","2020","Blomfield"));
    }

    @Test
    public void CreateNewTeamTest_tryToCreateNewTeamWithNonExistFieldName_FailureScenario(){
        assertFalse(systemOperations.createNewTeam("NonExistFieldName","La Liga","2020","NonExistFieldName"));
    }

    @Test
    public void CreateNewTeamTest_tryToCreateNewTeamWithNotATeamOwner_FailureScenario(){
        assertFalse(systemOperations.createNewTeamWithotTeamOwner("NotTeamOwner","La Liga","2020","Blomfield"));
    }
}