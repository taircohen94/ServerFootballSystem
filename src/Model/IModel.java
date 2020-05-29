package Model;

import javafx.util.Pair;

import javax.security.auth.login.FailedLoginException;
import java.util.LinkedList;

public interface IModel {

    //region General
    /**
     * Returns User's full name in order to display it on screen if needed.
     * @return - The user's full name.
     */
    String getUsersFullName();

    /**
     * Returns the user's type (Team Owner, Referee.....) in order to maintain authorizations.
     * @return User's type
     */
    String UserType();

    /*
     * Returns a list of games for a specific season and league.
     * May be used in order to let the user choose a game by it's ID.
     * @return list of games, ordered by game's ID.
     * @throws Exception - in case of no such league or season
     */
    LinkedList<Pair<String,Integer>> getGames(String leagueName, String seasonYear)throws RecordException;

    /**
     * Returns a list of events of a specific game.
     * May be used in order to let the user choose an event by it's ID.
     * @param gameID
     * @return list of events, ordered by event's ID.
     */
    LinkedList<Pair<String,Integer>> getEvents(int gameID) throws RecordException;

    //endregion

    //region Login
    /**
     * Logs in using user's credentials. Assumes input was already validated for null and empty inputs.
     * After a successful login, Model saves the user's credentials and user-type in order to maintain authorizations.
     * @param username - user's username. (should be Email address?)
     * @param password - password.
     * @return true or false for success or failure
     */
    boolean login(String username, String password) throws FailedLoginException;
    //endregion

    //region Sign In
    public boolean signIn(String userName, String password, String firstName,
                         String lastName);
    //endregion

    //region Team Management
    /**
     * Creates a new team out of the given details.
     * Assumes input was already validated for null and empty inputs.
     * Throws exception in any of the following cases: Team's name already exists, No such a season,
     */
    boolean createTeam(String name,String leagueName, String seasonYear, String fieldName)throws RecordException;

    /**
     * Closes a team.
     * Assumes input was already validated for null and empty inputs.
     * @param teamName - team's name
     * @return true or false for success / failure
     */
    boolean closeTeam(String teamName);


    /**
     * Adds a player into an existing team. Assumes input was already validated for null and empty inputs.
     *Assumes input was already validated for null and empty inputs.
     * @param teamName - team's name
     * @param SeasonYear - team's name
     * @param Username - username
     * @param Password - password
     * @param firstName - first name
     * @param lastName - last name
     * @param bDate - format: dd-mm-yyyy
     * @param role - role
     * @return true or false for success / failure
     * @throws RecordException -  in case there is no such team or season, or in case the user is already present in the system.
     */
    boolean addPlayer(String teamName, String SeasonYear, String Username, String Password, String firstName, String lastName, String bDate, String role)throws RecordException;

    /**
     * Removes a player from the team.
     * Assumes input was already validated for null and empty inputs.
     * @param teamName - team's name
     * @param seasonYear - team's name
     * @param username - username
     * @return true or false for success / failure
     */
    boolean removePlayer(String teamName, String seasonYear, String username);

    /**
     * Adds a coach into an existing team. Assumes input was already validated for null and empty inputs.
     * Assumes input was already validated for null and empty inputs.
     * @param teamName - team's name
     * @param SeasonYear - Requested year in which the team plays
     * @param Username - username
     * @param Password - password
     * @param firstName - first name
     * @param lastName - last name
     * @param bDate - format: dd-mm-yyyy
     * @param role - role
     * @param training - Coach's trining
     * @return true or false for success / failure
     * @throws RecordException - in case there is no such team or season, or in case the user is already present in the system.
     */
    boolean addCoach(String teamName, String SeasonYear, String Username, String Password, String firstName, String lastName, String bDate, String role, String training)throws RecordException;

    /**
     * Removes a coach from the team.
     * Assumes input was already validated for null and empty inputs.
     * @param teamName - team's name
     * @param seasonYear - team's name
     * @param username - username
     * @return true or false for success / failure
     */
    boolean removeCoach(String teamName, String seasonYear, String username);

    /**
     * Adds a Team Manager into an existing team. Assumes input was already validated for null and empty inputs.
     * Assumes input was already validated for null and empty inputs.
     * @param teamName - team's name
     * @param SeasonYear - Requested year in which the team plays
     * @param Username - username
     * @param Password - password
     * @param firstName - first name
     * @param lastName - last name
     * @return true or false for success / failure
     * @throws RecordException - in case there is no such team or season, or in case the user is already present in the system.
     */
    boolean addTeamManager(String teamName, String SeasonYear, String Username, String Password, String firstName, String lastName)throws RecordException;

    /**
     * Removes a Team Manager from the team.
     * Assumes input was already validated for null and empty inputs.
     * @param teamName - team's name
     * @param seasonYear - team's name
     * @param username - username
     * @return true or false for success / failure
     */
    boolean removeTeamManager(String teamName, String seasonYear, String username);

    /**
     * Adds a field into an existing team
     * Assumes input was already validated for null and empty inputs.
     * @param teamName - team's name
     * @param seasonYear - team's name
     * @param fieldName - Field's name
     * @param city  - city
     * @return true or false for success / failure
     * @throws RecordException - in case team/season doesn't exist or a field name is already present in the system.
     */
    boolean addField(String teamName, String seasonYear, String fieldName, String city)throws RecordException;

    /**
     * Removes a Field from the team.
     * Assumes input was already validated for null and empty inputs.
     * @param teamName - team's name
     * @param seasonYear - team's name
     * @param fieldName - Field's name
     * @return true or false for success / failure
     */
    boolean removeField(String teamName, String seasonYear, String fieldName);

    /**
     * Nominates a team Owner.
     * Assumes input was already validated for null and empty inputs.
     * Throws RecordException if there is no such a user, team or season.
     * @param teamName - the requested team
     * @param SeasonYear - Requested year in which the team plays
     * @param Username - Team Owner's user
     * @return true or false for success / failure
     */
    boolean nominateTeamOwner(String teamName, String SeasonYear, String Username)throws RecordException;

    /**
     * Removes a nomination of a team owner.
     * Assumes input was already validated for null and empty inputs.
     * @param teamName - the requested team
     * @param SeasonYear - Requested year in which the team plays
     * @param Username - Team Owner's user
     * @return true or false for success / failure
     */
    boolean discardNominationForTeamOwner(String teamName, String SeasonYear, String Username);

    /**
     * Nominates a team Manager.
     * Assumes input was already validated for null and empty inputs.
     * TODO: think how to specify authorities
     * @param teamName - the requested team
     * @param SeasonYear - Requested year in which the team plays
     * @param Username - Team Owner's user
     * @return true or false for success / failure
     * @throws RecordException  - if there is no such a user, team or season.
     */
    boolean nominateTeamManager(String teamName, String SeasonYear, String Username)throws RecordException;
    //endregion

    //region Policies Management
    /**
     * Receives a policy by its name for a specific season & league and sets it.
     *
     * @param policy     - "Simple Policy" or "Heuristic Policy"
     * @param leagueName -
     * @param seasonYear -
     * @return true for success, false for failure
     */
    boolean defineGameSchedulingPolicy(String policy, String leagueName, String seasonYear) throws RecordException, Exception;

    /**
     * Receives a policy by its name for a specific season & league and sets it.
     *
     * @param policy     - "Policy 1" "Policy 2"
     * @param leagueName -
     * @param seasonYear -
     * @return true for success, false for failure
     */
    boolean defineScoreTablePolicy(String policy, String leagueName, String seasonYear) throws RecordException;


    /**
     * Activates Game Scheduling Algorithm for a specific season & league and sets it.
     *
     * @param leagueName -
     * @param seasonYear -
     * @return true for success, false for failure
     */
    boolean runGameSchedulingAlgorithm(String leagueName, String seasonYear) throws Exception;
    //endregion

    /**
     * Adds an event into a existing game item
     * @param gameID - should use "getGames" method first in order to receive the correct gameID
     * @param eventType- out of event type enum
     * @param description Freestyle Description
     * @return
     */
    boolean addEvent(int gameID, String eventType, String description) throws RecordException;

    /**
     * Updates an event of an existing game item
     * @param gameID - should use "getGames" method first in order to receive the correct gameID
     * @param eventIndex - should use "getEvents" method first
     * @param eventType- out of event type enum
     * @param description Freestyle Description
     * @return
     */
    boolean updateEvent(int gameID,int eventIndex, String eventType, String description) throws RecordException;

    /**
     * Removes an event from an existing game item
     * @param gameID - should use "getGames" method first in order to receive the correct gameID
     * @param eventIndex - should use "getEvents" method first
     * @return
     */
    boolean removeEvent(int gameID,int eventIndex) throws RecordException;

    /**
     * Updates an event of an existing game item, until 5 hours after game is finished.
     * Can be preformed only by main referee.
     * @param gameID - should use "getGames" method first in order to receive the correct gameID
     * @param eventIndex - should use "getEvents" method first
     * @param eventType- out of event type enum
     * @param description Freestyle Description
     * @return
     */
    boolean updateEventAfterGameOver(int gameID, int eventIndex, String eventType, String description ) throws RecordException;
    /**
     * Updates an event of an existing game item, until 5 hours after game is finished.
     * Can be preformed only by main referee.
     * @param gameID - should use "getGames" method first in order to receive the correct gameID
     * @param eventIndex - should use "getEvents" method first
     * @return
     */
    boolean removeEventAfterGameOver(int gameID, int eventIndex) throws RecordException;

    /**
     * Export an Excel report holds all events in the required game
     * @param gameID -
     * @param pathToSave -
     * @param reportName -
     * @return
     */
    boolean exportGameReport(int gameID, String pathToSave, String reportName) throws RecordException;

    /**
     * Change a game's location. Should send a notification to all related referees.
     * @param gameID-
     * @param newFieldName-
     * @return
     */
    boolean changeGameLocation(int gameID, String newFieldName);

    /**
     * Change a game's date. Should send a notification to all related referees.
     * @param gameID-
     * @param newDate-
     * @return
     */
    boolean changeGameDate(int gameID, String newDate);
}
