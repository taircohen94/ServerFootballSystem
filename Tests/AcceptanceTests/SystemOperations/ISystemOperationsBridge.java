package AcceptanceTests.SystemOperations;

import AcceptanceTests.DataObjects.*;
import BL.AssociationAssets.EEventType;

public interface ISystemOperationsBridge {
    /**
     * generates a new random registered user in the system and returns his details
     *
     * @return The information the user entered when he signed up for the system
     */
    UserDetails getNewRegisteredUserForTest();

    /**
     * logs in to the system and validates login successfully
     *
     * @param username - userName
     * @param pw       - password
     * @return Returns true if login successful and false if not
     */
    boolean login(String username, String pw);

    /**
     * This function attempts to register a new user to the system,
     * if it returns true, otherwise returns false
     */
    boolean register(String userName, String password, String firstName, String lastName);

    TeamDetails getNewRegisteredTeamForTest();

    boolean createNewTeam(String name, String leagueName, String seasonYear, String fieldName);

    boolean createNewTeamWithotTeamOwner(String nonExistFieldName, String la_liga, String s, String nonExistFieldName1);

    boolean addEvent(String userName, String password, int gameID, EEventType eventType, int eventIndex, String description);

    boolean updateEvent(String userName, String password, int gameID, EEventType eventType, int eventIndex, String description);

    boolean deleteEvent(String userName, String password, int gameID, EEventType eventType, int eventIndex, String description);

    boolean deleteEventAfterGameOver(String userName, String password, int gameID, EEventType eventType, int eventIndex, String description, int hoursBefore);

    boolean editEventAfterGameOver(String userName, String password, int gameID, EEventType eventType, int eventIndex, String description, int hoursBefore);

    boolean exportReport(String userName, String password, int gameID, int hoursBefore, String path);

    TeamDetails getRegisteredTeamForTest();

    UserDetails getRegisteredTeamOwnerForTest();

    boolean editTeamManagerDetails(String userName, String password, String name, String seasonYear, String teamManagerUserName, String changeFirstName, String lastName);

    UserDetails getUnPrivilegeUserForTest();

    boolean editPlayerDetails(String userName, String password, String teamName, String seasonYear, String playerUserName, String firstName, String lastName, String role);

    PlayerDetails getRegisteredPlayerForTest();

    CoachDetails getRegisteredCoachForTest();

    boolean editCoachDetails(String userName, String password, String teamName, String seasonYear, String userNamePlayer, String firstName, String lastName, String training, String role);

    FieldDetails getExistFieldForTest();

    boolean editFieldDetails(String userName, String password, String name, String seasonYear, String fieldName, String city, String capacity);
}
