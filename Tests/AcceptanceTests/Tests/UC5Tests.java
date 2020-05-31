package AcceptanceTests.Tests;

import AcceptanceTests.DataObjects.*;
import AcceptanceTests.SystemOperations.ISystemOperationsBridge;
import AcceptanceTests.SystemOperations.RealSystemOperations;
import BL.Users.ECoachRole;
import BL.Users.EPlayerRole;
import BL.Users.ETraining;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UC5Tests {
    ISystemOperationsBridge systemOperations;
    UserDetails teamOwner;
    TeamDetails teamDetails;
    UserDetails teamManager;
    UserDetails unPrivilege;
    PlayerDetails playerDetails;
    CoachDetails coachDetails;
    FieldDetails fieldDetails;

    @Before
    public void Setup(){
        systemOperations = new RealSystemOperations();
        teamDetails = systemOperations.getRegisteredTeamForTest();
        teamOwner = systemOperations.getRegisteredTeamOwnerForTest();
        playerDetails = systemOperations.getRegisteredPlayerForTest();
        coachDetails = systemOperations.getRegisteredCoachForTest();
        unPrivilege = systemOperations.getUnPrivilegeUserForTest();
        fieldDetails = systemOperations.getExistFieldForTest();
        teamManager = new UserDetails("TM-2020","123","la","la");
    }

    @Test
    public void EditTeamAssetsTest_TeamOwnerTryToEditFirstNameOfTeamManager_SuccessScenario() {
        assertTrue(systemOperations.editTeamManagerDetails(teamOwner.userName, teamOwner.password, teamDetails.getName(), teamDetails.getSeasonYear(), teamManager.userName, "changeFirstName", teamManager.LastName));
    }

    @Test
    public void EditTeamAssetsTest_UnPrivilegeUserTryToEditFirstNameOfTeamManager_FailureScenario(){
        assertFalse(systemOperations.editTeamManagerDetails(unPrivilege.userName, unPrivilege.password, teamDetails.getName(), teamDetails.getSeasonYear(), teamManager.userName, "changeFirstName", teamManager.LastName));
    }

    @Test
    public void EditTeamAssetsTest_TeamOwnerTryToEditLastNameOfTeamManager_SuccessScenario(){
        assertTrue(systemOperations.editTeamManagerDetails(teamOwner.userName, teamOwner.password, teamDetails.getName(), teamDetails.getSeasonYear(), teamManager.userName, teamManager.firstName, "changeLastName"));
    }

    @Test
    public void EditTeamAssetsTest_UnPrivilegeUserTryToEditLastNameOfTeamManager_FailureScenario(){
        assertFalse(systemOperations.editTeamManagerDetails(unPrivilege.userName, unPrivilege.password, teamDetails.getName(), teamDetails.getSeasonYear(), teamManager.userName, teamManager.firstName, "changeLastName"));

    }

    @Test
    public void EditTeamAssetsTest_TeamOwnerTryToEditFirstNameOfPlayer_SuccessScenario(){
        assertTrue(systemOperations.editPlayerDetails(teamOwner.userName, teamOwner.password, teamDetails.getName(), teamDetails.getSeasonYear(), playerDetails.userName, "changeFirstName", playerDetails.LastName, playerDetails.training.toString()));
    }

    @Test
    public void EditTeamAssetsTest_UnPrivilegeUserTryToEditFirstNameOfPlayer_FailureScenario(){
        assertFalse(systemOperations.editPlayerDetails(unPrivilege.userName, unPrivilege.password, teamDetails.getName(), teamDetails.getSeasonYear(), playerDetails.userName, "changeFirstName", playerDetails.LastName, playerDetails.training.toString()));

    }

    @Test
    public void EditTeamAssetsTest_TeamOwnerTryToEditLastNameOfPlayer_SuccessScenario(){
        assertTrue(systemOperations.editPlayerDetails(teamOwner.userName, teamOwner.password, teamDetails.getName(), teamDetails.getSeasonYear(), playerDetails.userName, playerDetails.firstName, "changeLastName", playerDetails.training.toString()));

    }

    @Test
    public void EditTeamAssetsTest_UnPrivilegeUserTryToEditLastNameOfPlayer_FailureScenario(){
        assertFalse(systemOperations.editPlayerDetails(unPrivilege.userName, unPrivilege.password, teamDetails.getName(), teamDetails.getSeasonYear(), playerDetails.userName, playerDetails.firstName, "changeLastName", playerDetails.training.toString()));

    }

//    @Test
//    public void EditTeamAssetsTest_TeamOwnerTryToEditBDayOfPlayer_SuccessScenario(){
//    }
//
//    @Test
//    public void EditTeamAssetsTest_UnPrivilegeUserTryToEditBDayOfPlayer_FailureScenario(){
//    }

    @Test
    public void EditTeamAssetsTest_TeamOwnerTryToEditRoleOfPlayer_SuccessScenario(){
        assertTrue(systemOperations.editPlayerDetails(teamOwner.userName, teamOwner.password, teamDetails.getName(), teamDetails.getSeasonYear(), playerDetails.userName, playerDetails.firstName, playerDetails.LastName, EPlayerRole.Forward.toString()));

    }

    @Test
    public void EditTeamAssetsTest_TeamOwnerTryToEditRoleOfPlayerWithWrongFormatOfRole_FailureScenario(){
        assertFalse(systemOperations.editPlayerDetails(teamOwner.userName, teamOwner.password, teamDetails.getName(), teamDetails.getSeasonYear(), playerDetails.userName, playerDetails.firstName, playerDetails.LastName, "DifferentFormat"));

    }

    @Test
    public void EditTeamAssetsTest_UnPrivilegeUserTryToEditRoleOfPlayer_FailureScenario(){
        assertFalse(systemOperations.editPlayerDetails(unPrivilege.userName, unPrivilege.password, teamDetails.getName(), teamDetails.getSeasonYear(), playerDetails.userName, playerDetails.firstName, playerDetails.LastName, EPlayerRole.Forward.toString()));
    }



    @Test
    public void EditTeamAssetsTest_TeamOwnerTryToEditFieldCityName_SuccessScenario(){
        assertTrue(systemOperations.editFieldDetails(teamOwner.userName, teamOwner.password, teamDetails.getName(), teamDetails.getSeasonYear(), fieldDetails.getName(),"changeCity",fieldDetails.getCapacity()));

    }

    @Test
    public void EditTeamAssetsTest_UnPrivilegeUserTryToEditFieldCityName_FailureScenario(){
        assertFalse(systemOperations.editFieldDetails(unPrivilege.userName, unPrivilege.password, teamDetails.getName(), teamDetails.getSeasonYear(), fieldDetails.getName(),"changeCity",fieldDetails.getCapacity()));

    }

    @Test
    public void EditTeamAssetsTest_TeamOwnerTryToEditFieldCapacity_SuccessScenario(){
        assertTrue(systemOperations.editFieldDetails(teamOwner.userName, teamOwner.password, teamDetails.getName(), teamDetails.getSeasonYear(), fieldDetails.getName(),fieldDetails.getCity(),"100"));

    }

    @Test
    public void EditTeamAssetsTest_UnPrivilegeUserTryToEditFieldCapacity_FailureScenario(){
        assertFalse(systemOperations.editFieldDetails(unPrivilege.userName, unPrivilege.password, teamDetails.getName(), teamDetails.getSeasonYear(), fieldDetails.getName(),fieldDetails.getCity(),"100"));

    }

    @Test
    public void EditTeamAssetsTest_TeamOwnerTryToEditFieldCapacityWithNegativeValue_FailureScenario(){
        assertFalse(systemOperations.editFieldDetails(teamOwner.userName, teamOwner.password, teamDetails.getName(), teamDetails.getSeasonYear(), fieldDetails.getName(),fieldDetails.getCity(),"-100"));

    }

    @Test
    public void EditTeamAssetsTest_TeamOwnerTryToEditFieldCapacityWithNonNumericValue_FailureScenario(){
        assertFalse(systemOperations.editFieldDetails(teamOwner.userName, teamOwner.password, teamDetails.getName(), teamDetails.getSeasonYear(), fieldDetails.getName(),fieldDetails.getCity(),"aa"));

    }

    @Test
    public void EditTeamAssetsTest_TeamOwnerTryToEditFirstNameOfCoach_SuccessScenario(){
        assertTrue(systemOperations.editCoachDetails(teamOwner.userName, teamOwner.password, teamDetails.getName(), teamDetails.getSeasonYear(), coachDetails.userName, "changeFirstName", coachDetails.LastName,coachDetails.training.toString(),coachDetails.role.toString()));

    }

    @Test
    public void EditTeamAssetsTest_UnPrivilegeUserTryToEditFirstNameOfCoach_FailureScenario(){
        assertFalse(systemOperations.editCoachDetails(unPrivilege.userName, unPrivilege.password, teamDetails.getName(), teamDetails.getSeasonYear(), coachDetails.userName, "changeFirstName", coachDetails.LastName,coachDetails.training.toString(),coachDetails.role.toString()));

    }

    @Test
    public void EditTeamAssetsTest_TeamOwnerTryToEditLastNameOfCoach_SuccessScenario(){
        assertTrue(systemOperations.editCoachDetails(teamOwner.userName, teamOwner.password, teamDetails.getName(), teamDetails.getSeasonYear(), coachDetails.userName, coachDetails.firstName, "changeLastName",coachDetails.training.toString(),coachDetails.role.toString()));

    }

    @Test
    public void EditTeamAssetsTest_UnPrivilegeUserTryToEditLastNameOfCoach_FailureScenario(){
        assertFalse(systemOperations.editCoachDetails(unPrivilege.userName, unPrivilege.password, teamDetails.getName(), teamDetails.getSeasonYear(), coachDetails.userName, coachDetails.firstName, "changeLastName",coachDetails.training.toString(),coachDetails.role.toString()));

    }

    @Test
    public void EditTeamAssetsTest_TeamOwnerTryToEditRoleOfCoach_SuccessScenario(){
        assertTrue(systemOperations.editCoachDetails(teamOwner.userName, teamOwner.password, teamDetails.getName(), teamDetails.getSeasonYear(), coachDetails.userName, coachDetails.firstName, coachDetails.LastName, coachDetails.training.toString(),ECoachRole.YouthCoach.toString()));

    }

    @Test
    public void EditTeamAssetsTest_TeamOwnerTryToEditRoleOfCoachWithWrongFormatOfRole_FailureScenario(){
        assertFalse(systemOperations.editCoachDetails(teamOwner.userName, teamOwner.password, teamDetails.getName(), teamDetails.getSeasonYear(), coachDetails.userName, coachDetails.firstName, coachDetails.LastName, coachDetails.training.toString(),"wrongFormat"));

    }

    @Test
    public void EditTeamAssetsTest_UnPrivilegeUserTryToEditRoleOfCoach_FailureScenario(){
        assertFalse(systemOperations.editCoachDetails(unPrivilege.userName, unPrivilege.password, teamDetails.getName(), teamDetails.getSeasonYear(), coachDetails.userName, coachDetails.firstName, coachDetails.LastName, coachDetails.training.toString(),ECoachRole.YouthCoach.toString()));

    }

    @Test
    public void EditTeamAssetsTest_TeamOwnerTryToEditTrainingOfCoach_SuccessScenario(){
        assertTrue(systemOperations.editCoachDetails(teamOwner.userName, teamOwner.password, teamDetails.getName(), teamDetails.getSeasonYear(), coachDetails.userName, coachDetails.firstName, coachDetails.LastName, ETraining.UEFAB.toString(),coachDetails.role.toString()));

    }

    @Test
    public void EditTeamAssetsTest_TeamOwnerTryToEditTrainingOfCoachWithWrongFormatOfTraining_FailureScenario(){
        assertFalse(systemOperations.editCoachDetails(teamOwner.userName, teamOwner.password, teamDetails.getName(), teamDetails.getSeasonYear(), coachDetails.userName, coachDetails.firstName, coachDetails.LastName, "wrongFormat",coachDetails.role.toString()));

    }

    @Test
    public void EditTeamAssetsTest_UnPrivilegeUserTryToEditTrainingOfCoach_FailureScenario(){
        assertFalse(systemOperations.editCoachDetails(unPrivilege.userName, unPrivilege.password, teamDetails.getName(), teamDetails.getSeasonYear(), coachDetails.userName, coachDetails.firstName, coachDetails.LastName, ETraining.UEFAB.toString(),coachDetails.role.toString()));

    }

}