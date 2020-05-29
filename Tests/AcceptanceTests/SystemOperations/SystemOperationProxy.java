package AcceptanceTests.SystemOperations;

import AcceptanceTests.DataObjects.TeamDetails;
import AcceptanceTests.DataObjects.UserDetails;
import AssociationAssets.EEventType;

public class SystemOperationProxy implements  ISystemOperationsBridge{
    ISystemOperationsBridge real;

    public SystemOperationProxy( ) {
        this.real = null;
    }

    @Override
    public UserDetails getNewRegisteredUserForTest() {
        if (real != null){
            return real.getNewRegisteredUserForTest();
        }
        return null;
    }

    @Override
    public boolean login(String username, String pw) {
        if (real != null){
            return real.login(username, pw) ;
        }
        return false;
    }


    @Override
    public boolean register(String userName, String password, String firstName, String lastName) {
        if (real != null){
            return real.register(userName, password,firstName,lastName) ;
        }
        return true;
    }

    @Override
    public boolean createNewTeam(String name, String leagueName, String seasonYear, String fieldName) {
        if (real != null){
            return real.createNewTeam(name,leagueName,seasonYear,fieldName);
        }
        return false;
    }

    @Override
    public TeamDetails getNewRegisteredTeamForTest() {
        return null;
    }

    @Override
    public boolean createNewTeamWithotTeamOwner(String nonExistFieldName, String la_liga, String s, String nonExistFieldName1) {
        return false;
    }

    @Override
    public boolean addEvent(String userName,String password, int gameID, EEventType eventType, int eventIndex, String description) {
        if (real != null){
            return real.addEvent( userName, password, gameID,  eventType,  eventIndex,  description);
        }
        return false;
    }

    @Override
    public boolean updateEvent(String userName,String password, int gameID, EEventType eventType, int eventIndex, String description) {
        if (real != null){
            return real.updateEvent( userName, password, gameID,  eventType,  eventIndex,  description);
        }
        return false;
    }

    @Override
    public boolean deleteEvent(String userName,String password, int gameID, EEventType eventType, int eventIndex, String description) {
        if (real != null){
            return real.deleteEvent( userName, password, gameID,  eventType,  eventIndex,  description);
        }
        return false;
    }

    @Override
    public boolean deleteEventAfterGameOver(String userName, String password, int gameID, EEventType eventType, int eventIndex, String description,int hoursBefore) {
        if (real != null){
            return real.deleteEventAfterGameOver( userName, password, gameID,  eventType,  eventIndex,  description, hoursBefore);
        }
        return false;
    }

    @Override
    public boolean editEventAfterGameOver(String userName, String password, int gameID, EEventType eventType, int eventIndex, String description,int hoursBefore) {
        if (real != null){
            return real.editEventAfterGameOver( userName, password, gameID,  eventType,  eventIndex,  description, hoursBefore);
        }
        return false;
    }

    @Override
    public boolean exportReport(String userName, String password, int gameID, int hoursBefore, String path) {
        if (real != null){
            return real.exportReport( userName,  password,  gameID,  hoursBefore,  path);
        }
        return false;
    }

    @Override
    public TeamDetails getRegisteredTeamForTest() {
        if (real != null){
            return real.getRegisteredTeamForTest();
        }
        return null;
    }

    @Override
    public UserDetails getRegisteredTeamOwnerForTest() {
        if (real != null){
            return real.getRegisteredTeamOwnerForTest();
        }
        return null;
    }

    @Override
    public boolean editTeamManagerDetails(String userName, String password, String name, String seasonYear, String teamManagerUserName, String changeFirstName, String lastName) {
        if (real != null){
            return real.editTeamManagerDetails( userName,  password,  name,  seasonYear,  teamManagerUserName,  changeFirstName,  lastName);
        }
        return false;
    }
}
