package AcceptanceTests.Tests;
import AcceptanceTests.DataObjects.EventDetails;
import AcceptanceTests.DataObjects.GameDetails;
import AcceptanceTests.DataObjects.RefereeDetails;
import AcceptanceTests.DataObjects.UserDetails;
import AcceptanceTests.SystemOperations.ISystemOperationsBridge;
import AcceptanceTests.SystemOperations.RealSystemOperations;
import AssociationAssets.EEventType;
import Users.EReferee;
import System.FootballSystem;
import org.junit.Before;
import org.junit.Test;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UC4Tests {
    ISystemOperationsBridge systemOperations;
    RefereeDetails MainReferee;
    RefereeDetails ASSISTANT1Referee;
    RefereeDetails ASSISTANT2Referee;
    GameDetails gameDetails;
    RefereeDetails anotherReferee;
    UserDetails registeredUser;
    EventDetails eventDetails;

    @Before
    public void Setup(){
        systemOperations = new RealSystemOperations();
        MainReferee = new RefereeDetails("1","1","la","lala",EReferee.MAIN);
        ASSISTANT1Referee = new RefereeDetails("2","2","la","lala",EReferee.ASSISTANT);
        ASSISTANT2Referee = new RefereeDetails("3","3","la","lala",EReferee.ASSISTANT);
        Time time =Time.valueOf(LocalTime.of(LocalTime.now().getHour()-1,LocalTime.now().getMinute()));
        //default time zone
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //creating the instance of LocalDate using the day, month, year info
        LocalDate localDate = LocalDate.now();

        //local date + atStartOfDay() + default time zone + toInstant() = Date
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

        gameDetails = new GameDetails(date,time,"Blomfield","team1","team2","1","2","3","2020","La Liga");
        registeredUser = systemOperations.getNewRegisteredUserForTest(); //Registered!!!
        FootballSystem.getInstance().signIn("anotherReferee","3","lala","la");
        FootballSystem.getInstance().creatingReferee("anotherReferee","la","laala", EReferee.MAIN);
        anotherReferee = new RefereeDetails("anotherReferee","3","la","lala",EReferee.MAIN);
        eventDetails = new EventDetails(gameDetails.getGID(),0, EEventType.GOALHOST,"description");
    }

    @Test
    public void GameManagementTest_notPrivilegeUserTryToAddEvent_FailureScenario(){
        assertFalse(systemOperations.addEvent(registeredUser.userName,registeredUser.password, eventDetails.getGameID(), eventDetails.getEventType(), eventDetails.getEventIndex(), eventDetails.getDescription()));
    }

    @Test
    public void GameManagementTest_notPrivilegeUserTryToUpdateEvent_FailureScenario(){
        assertFalse(systemOperations.updateEvent(registeredUser.userName,registeredUser.password, eventDetails.getGameID(), eventDetails.getEventType(), eventDetails.getEventIndex(), eventDetails.getDescription()));
    }

    @Test
    public void GameManagementTest_notPrivilegeUserTryToDeleteEvent_FailureScenario(){
        assertFalse(systemOperations.deleteEvent(registeredUser.userName,registeredUser.password, eventDetails.getGameID(), eventDetails.getEventType(), eventDetails.getEventIndex(), eventDetails.getDescription()));
    }

    @Test
    public void GameManagementTest_notPrivilegeUserTryToUpdateEventAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.editEventAfterGameOver(registeredUser.userName,registeredUser.password, eventDetails.getGameID(), eventDetails.getEventType(), eventDetails.getEventIndex(), eventDetails.getDescription(),4));
    }

    @Test
    public void GameManagementTest_notPrivilegeUserTryToDeleteEventAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.deleteEventAfterGameOver(registeredUser.userName,registeredUser.password, eventDetails.getGameID(), eventDetails.getEventType(), eventDetails.getEventIndex(), eventDetails.getDescription(),4));
    }

    @Test
    public void GameManagementTest_MainRefereeTryToEditEventDuringTheGame_SuccessScenario(){
        assertTrue(systemOperations.updateEvent(MainReferee.userName,MainReferee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description));
    }

    @Test
    public void GameManagementTest_AnotherTypeRefereeTryToEditEventDuringTheGame_SuccessScenario(){
        assertTrue(systemOperations.updateEvent(ASSISTANT1Referee.userName,ASSISTANT1Referee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description));
    }

    @Test
    public void GameManagementTest_NotAssignRefereeTryToEditEventDuringTheGame_FailureScenario(){
        assertFalse(systemOperations.updateEvent(anotherReferee.userName,anotherReferee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description));
    }

    @Test
    public void GameManagementTest_MainRefereeTryToAddEventDuringTheGame_SuccessScenario(){
        assertTrue(systemOperations.addEvent(MainReferee.userName,MainReferee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description));
    }

    @Test
    public void GameManagementTest_AnotherTypeRefereeTryToAddEventDuringTheGame_SuccessScenario(){
        assertTrue(systemOperations.addEvent(ASSISTANT1Referee.userName,ASSISTANT1Referee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description));
    }

    @Test
    public void GameManagementTest_NotAssignRefereeTryToAddEventDuringTheGame_FailureScenario(){
        assertFalse(systemOperations.addEvent(anotherReferee.userName,anotherReferee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description));
    }

    @Test
    public void GameManagementTest_MainRefereeTryToDeleteEventDuringTheGame_SuccessScenario(){
        assertTrue(systemOperations.deleteEvent(MainReferee.userName,MainReferee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description));
    }

    @Test
    public void GameManagementTest_AnotherTypeRefereeTryToDeleteEventDuringTheGame_SuccessScenario(){
        assertTrue(systemOperations.deleteEvent(ASSISTANT1Referee.userName,ASSISTANT1Referee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description));
    }

    @Test
    public void GameManagementTest_NotAssignRefereeTryToDeleteEventDuringTheGame_FailureScenario(){
        assertFalse(systemOperations.deleteEvent(anotherReferee.userName,anotherReferee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description));
    }

    @Test
    public void GameManagementTest_MainRefereeTryToDeleteEventAfterGameOver_SuccessScenario(){
        assertTrue(systemOperations.deleteEventAfterGameOver(MainReferee.userName,MainReferee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description,4));
    }

    @Test
    public void GameManagementTest_AnotherTypeRefereeTryToDeleteAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.deleteEventAfterGameOver(ASSISTANT1Referee.userName,ASSISTANT1Referee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description,4));
    }

    @Test
    public void GameManagementTest_NotAssignRefereeTryToDeleteEventAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.deleteEventAfterGameOver(anotherReferee.userName,anotherReferee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description,4));
    }

    @Test
    public void GameManagementTest_MainRefereeTryToEditEventAfterGameOver_SuccessScenario(){
        assertTrue(systemOperations.editEventAfterGameOver(MainReferee.userName,MainReferee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description,4));
    }

    @Test
    public void GameManagementTest_AnotherTypeRefereeTryToEditAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.editEventAfterGameOver(ASSISTANT1Referee.userName,ASSISTANT1Referee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description,4));
    }

    @Test
    public void GameManagementTest_NotAssignRefereeTryToEditEventAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.editEventAfterGameOver(anotherReferee.userName,anotherReferee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description,4));
    }

    @Test
    public void GameManagementTest_MainRefereeTryToDeleteEventMoreThen5HoursAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.deleteEventAfterGameOver(MainReferee.userName,MainReferee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description,8));
    }

    @Test
    public void GameManagementTest_AnotherTypeRefereeTryToDeleteMoreThen5HoursAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.deleteEventAfterGameOver(ASSISTANT1Referee.userName,ASSISTANT1Referee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description,8));
    }

    @Test
    public void GameManagementTest_NotAssignRefereeTryToDeleteEventMoreThen5HoursAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.deleteEventAfterGameOver(anotherReferee.userName,anotherReferee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description,8));
    }

    @Test
    public void GameManagementTest_MainRefereeTryToEditEventMoreThen5HoursAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.editEventAfterGameOver(MainReferee.userName,MainReferee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description,8));
    }

    @Test
    public void GameManagementTest_AnotherTypeRefereeTryToEditMoreThen5HoursAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.editEventAfterGameOver(ASSISTANT1Referee.userName,ASSISTANT1Referee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description,8));
    }

    @Test
    public void GameManagementTest_NotAssignRefereeTryToEditEventMoreThen5HoursAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.editEventAfterGameOver(anotherReferee.userName,anotherReferee.password,eventDetails.gameID,eventDetails.eventType,eventDetails.eventIndex,eventDetails.description,8));
    }


    @Test
    public void GameManagementTest_MainRefereeExportReportAfterGameOver_SuccessScenario(){
        assertTrue(systemOperations.exportReport(MainReferee.userName,MainReferee.password,eventDetails.gameID,4,"C:\\Users\\יובל בן אליעזר\\Documents\\test"));
    }

    @Test
    public void GameManagementTest_AnotherTypeRefereeExportReportAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.exportReport(ASSISTANT1Referee.userName,ASSISTANT1Referee.password,eventDetails.gameID,4,"C:\\Users\\יובל בן אליעזר\\Desktop"));
    }

    @Test
    public void GameManagementTest_NotAssignRefereeExportReportAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.exportReport(anotherReferee.userName,anotherReferee.password,eventDetails.gameID,4,"C:\\Users\\יובל בן אליעזר\\Desktop"));
    }

    @Test
    public void GameManagementTest_MainRefereeExportReportMoreThen5HoursAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.exportReport(MainReferee.userName,MainReferee.password,eventDetails.gameID,8,"C:\\Users\\יובל בן אליעזר\\Desktop"));
    }

    @Test
    public void GameManagementTest_AnotherTypeRefereeExportReportMoreThen5HoursAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.exportReport(ASSISTANT1Referee.userName,ASSISTANT1Referee.password,eventDetails.gameID,8,"C:\\Users\\יובל בן אליעזר\\Desktop"));
    }

    @Test
    public void GameManagementTest_NotAssignRefereeExportReportMoreThen5HoursAfterGameOver_FailureScenario(){
        assertFalse(systemOperations.exportReport(anotherReferee.userName,anotherReferee.password,eventDetails.gameID,8,"C:\\Users\\יובל בן אליעזר\\Desktop"));
    }

}
