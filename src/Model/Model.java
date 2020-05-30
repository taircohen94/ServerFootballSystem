package Model;

import AssociationAssets.*;
import DAL.JDBCConnector;
import DB.FieldDB;
import DB.LeagueDB;
import DB.SeasonDB;
import PoliciesAndAlgorithms.OneRoundGamesAssigningPolicy;
import PoliciesAndAlgorithms.RegularScorePolicy;
import PoliciesAndAlgorithms.ScoreTablePolicy2;
import PoliciesAndAlgorithms.SimpleGamesAssigningPolicy;
import System.FootballSystem;
import Users.*;
import javafx.util.Pair;

import javax.security.auth.login.FailedLoginException;
import java.util.*;

public class Model extends Observable implements IModel {

    Fan user;
    FootballSystem footballSystem = FootballSystem.getInstance();
    ValidateObject validate;
    static int TEAM_ID = 1;

    //region General

    /**
     * Returns User's full name in order to display it on screen if needed.
     *
     * @return - The user's full name.
     */
    @Override
    public String getUsersFullName() {
        if (user == null)
            return "";

        return user.getfName() + " " + user.getlName();
    }

    /**
     * Returns the user's type (Team Owner, Referee.....) in order to maintain authorizations.
     *
     * @return User's type
     */
    @Override
    public String UserType() {
        if (user == null)
            return "";

        String type = "";
        Fan fanByUserName = footballSystem.getFanByUserName(user.getUserName());

        if (fanByUserName instanceof Coach) {
            type = "Coach";
        } else if (fanByUserName instanceof Player) {
            type = "Player";
        } else if (fanByUserName instanceof Referee) {
            type = "Referee";
        } else if (fanByUserName instanceof RepresentativeFootballAssociation) {
            type = "RepresentativeFootballAssociation";
        } else if (fanByUserName instanceof SystemManager) {
            type = "SystemManager";
        } else if (fanByUserName instanceof TeamManager) {
            type = "TeamManager";
        } else if (fanByUserName instanceof TeamOwner) {
            type = "TeamOwner";
        }
        return type;
    }

    /*
     * Returns a list of games for a specific season and league.
     * May be used in order to let the user choose a game by it's ID.
     *
     * @return list of games, ordered by game's ID.
     * @throws RecordException - in case of no such league or season
     */
    @Override
    public LinkedList<Pair<String, Integer>> getGames(String leagueName, String seasonYear) throws RecordException {
        LinkedList<Pair<String, Integer>> allGames = new LinkedList<Pair<String, Integer>>();
        League league = ValidateObject.getValidatedLeague(leagueName);
        Season season = ValidateObject.getValidatedSeason(leagueName, seasonYear);
        for (Game game : league.getGames(season.getYear()).values()) {
            allGames.add(new Pair<String, Integer>(game.getDate().toString(), game.getGID()));
        }
        return allGames;
    }


    /**
     * Returns a list of events of a specific game.
     * May be used in order to let the user choose an event by it's ID.
     *
     * @param gameID
     * @return list of events, ordered by event's ID.
     */
    @Override
    public LinkedList<Pair<String, Integer>> getEvents(int gameID) throws RecordException {
        Game game = ValidateObject.getValidatedGame(gameID);
        LinkedList<Pair<String, Integer>> allEvents = new LinkedList<>();
        String eventList = "";
        int i = 0;
        for (Event event : game.getEvents()) {
            allEvents.add(new Pair<String, Integer>
                    ("Type: " + event.getEventType() + ", Description: " + event.getDescription(), i));

            i++;
        }
        return allEvents;
    }
//endregion

    //region Login

    /**
     * Logs in using user's credentials. Assumes input was already validated for null and empty inputs.
     * After a successful login, Model saves the user's credentials and user-type in order to maintain authorizations.
     *
     * @param username - user's username. (should be Email address?)
     * @param password - password.
     * @return true or false for success or failure
     */
    @Override
    public boolean login(String username, String password) throws FailedLoginException {
        Fan tmpUser = footballSystem.login(username, password);
        // Save the user as an object
        user = footballSystem.getFanByUserName(username);
        if (user instanceof RepresentativeFootballAssociation) {
            ArrayList<String> notifications = ((RepresentativeFootballAssociation) user).getNotificationTeams();
            if (notifications.size() > 0) {
                setChanged();
                notifyObservers(user);
            }
        }
        //else if team owner/referee

        return true;
    }
    //endregion

    //region Sign In
    @Override
    public boolean signIn(String userName, String password, String firstName, String lastName) {
        return footballSystem.signIn(userName, password, firstName, lastName) != null;
    }
    //endregion

    //region Team Management

    /**
     * Creates a new team out of the given details.
     * Assumes input was already validated for null and empty inputs.
     * Throws exception in any of the following cases: Team's name already exists, No such a season,
     *
     * @param name-       team's name
     * @param seasonYear-
     * @param fieldName-
     * @throws RecordException- in case there is no such league or season or team owner.
     */
    @Override
    public boolean createTeam(String name, String leagueName, String seasonYear, String fieldName) throws RecordException {
        // Only TeamOwner is allowed to create a team.
        if (!(user instanceof TeamOwner)) {
            throw new RecordException("You dont have permission to create new team");
        }
        if (validateDuplicateTeamName(name)) {
            throw new RecordException("This team name already exist");
        }
        TeamOwner teamOwnerUser = (TeamOwner) user;
        Season season = ValidateObject.getValidatedSeason(leagueName, seasonYear);
        // Get an existing field or create one and add it TO fields DB
        Field field = footballSystem.getFieldDB().getAllFields().get(fieldName);
        if (field == null) {
            throw new RecordException("The field " + fieldName + " is not exits");
        }
        // Create a new team.
        try {
            Team newTeam = new Team(TEAM_ID++, name, season, field, null, teamOwnerUser);
            FootballSystem.getInstance().addTeamToDB(newTeam);
            newTeam.setCurrentLeague(FootballSystem.getInstance().getLeagueDB().getAllLeagues().get(leagueName));
            newTeam.addSeasonToTeam(season);
        } catch (Exception e) {
            String cause = e.getMessage();
            throw new RecordException(cause);
        }
        JDBCConnector jdbcConnector = new JDBCConnector();
        jdbcConnector.connectDBSaveData();
        jdbcConnector.connectDBUploadData();
        return true;

    }

    public void editTeamManagerDetails(String teamName, String seasonYear, String userName, String firstName, String lastName) throws RecordException {
        if (!(user instanceof TeamOwner)) {
            throw new RecordException("You dont have permission to edit team property details");
        }
        TeamOwner teamOwner = (TeamOwner) user;
        Team team = FootballSystem.getInstance().getTeamDB().getAllTeams().get(teamName);
        Season season = FootballSystem.getInstance().getSeasonDB().getAllSeasons().get(seasonYear);
        try {
            teamOwner.editTMDetails(team, season, userName, firstName,
                    lastName);
        } catch (Exception e) {
            String cause = e.getMessage();
            throw new RecordException(cause);
        }
        JDBCConnector jdbcConnector = new JDBCConnector();
        jdbcConnector.connectDBSaveData();
        jdbcConnector.connectDBUploadData();
    }

    public void editCoachDetails(String teamName, String seasonYear, String userName, String firstName, String lastName, String training, String role) throws RecordException {
        if (!(user instanceof TeamOwner)) {
            throw new RecordException("You dont have permission to edit team property details");
        }
        TeamOwner teamOwner = (TeamOwner) user;
        Team team = FootballSystem.getInstance().getTeamDB().getAllTeams().get(teamName);
        Season season = FootballSystem.getInstance().getSeasonDB().getAllSeasons().get(seasonYear);
        try {
            if (training == null && role == null) {
                teamOwner.editCoachDetails(team, season, userName, firstName,
                        lastName, null, null);
            } else if (training == null) {
                teamOwner.editCoachDetails(team, season, userName, firstName,
                        lastName, null, ECoachRole.valueOf(role));
            } else if (role == null) {
                teamOwner.editCoachDetails(team, season, userName, firstName,
                        lastName, ETraining.valueOf(training), null);
            } else {
                teamOwner.editCoachDetails(team, season, userName, firstName,
                        lastName, ETraining.valueOf(training), ECoachRole.valueOf(role));
            }
        } catch (Exception e) {
            String cause = e.getMessage();
            throw new RecordException(cause);
        }
        JDBCConnector jdbcConnector = new JDBCConnector();
        jdbcConnector.connectDBSaveData();
        jdbcConnector.connectDBUploadData();
    }

    public void editPlayerDetails(String teamName, String seasonYear, String userName, String firstName, String lastName, String role) throws RecordException {
        if (!(user instanceof TeamOwner)) {
            throw new RecordException("You dont have permission to edit team property details");
        }
        TeamOwner teamOwner = (TeamOwner) user;
        Team team = FootballSystem.getInstance().getTeamDB().getAllTeams().get(teamName);
        Season season = FootballSystem.getInstance().getSeasonDB().getAllSeasons().get(seasonYear);
        try {
            if (role == null) {
                teamOwner.editPlayerDetails(team, season, userName, firstName,
                        lastName, null);
            } else {
                teamOwner.editPlayerDetails(team, season, userName, firstName,
                        lastName, EPlayerRole.valueOf(role));
            }
        } catch (Exception e) {
            String cause = e.getMessage();
            throw new RecordException(cause);
        }
        JDBCConnector jdbcConnector = new JDBCConnector();
        jdbcConnector.connectDBSaveData();
        jdbcConnector.connectDBUploadData();
    }

    public void editFieldDetails(String teamName, String seasonYear, String fieldName, String city, String capacity) throws RecordException {
        if (!(user instanceof TeamOwner)) {
            throw new RecordException("You dont have permission to edit team property details");
        }
        TeamOwner teamOwner = (TeamOwner) user;
        Team team = FootballSystem.getInstance().getTeamDB().getAllTeams().get(teamName);
        Season season = FootballSystem.getInstance().getSeasonDB().getAllSeasons().get(seasonYear);
        try {
            if (!capacity.isEmpty()) {
                int capa = Integer.parseInt(capacity);
                if (capa < 0) {
                    throw new RecordException("please insert valid capacity. only integer greater then 0");
                }
            }
        } catch (Exception e) {
            throw new RecordException("please insert valid capacity. only integer greater then 0");
        }
        try {
            teamOwner.editFieldDetails(team, season, fieldName, city,
                    capacity);
        } catch (Exception e) {
            String cause = e.getMessage();
            throw new RecordException(cause);
        }
        JDBCConnector jdbcConnector = new JDBCConnector();
        jdbcConnector.connectDBSaveData();
        jdbcConnector.connectDBUploadData();
    }

    /**
     * rreturn true if this team name already exist
     *
     * @param name
     * @return
     */
    private boolean validateDuplicateTeamName(String name) {
        if (footballSystem.getInstance().getTeamDB().getAllTeams().containsKey(name)) return true;
        return false;

    }

    /**
     * Closes a team.
     * Assumes input was already validated for null and empty inputs.
     *
     * @param teamName - team's name
     * @return true or false for success / failure
     */
    @Override
    public boolean closeTeam(String teamName) {

        // Only System Manager is allowed to close a team.
        if (!(user instanceof SystemManager))
            return false;
        SystemManager systemManagerUser = (SystemManager) user;

        // Return false if team does not exist
        Team toClose = footballSystem.getTeamDB().getAllTeams().get(teamName);
        if (toClose == null)
            return false;

        // Close team
        systemManagerUser.closeTeam(teamName);

        // Make sure team is not active
        return toClose.getIsActive() == ETeamStatus.INACTIVE;
    }

    /**
     * Adds a player into an existing team. Assumes input was already validated for null and empty inputs.
     * Assumes input was already validated for null and empty inputs.
     *
     * @param teamName   - team's name
     * @param SeasonYear - team's name
     * @param Username   - username
     * @param Password   - password
     * @param firstName  - first name
     * @param lastName   - last name
     * @param bDate      - format: dd-mm-yyyy
     * @param role       - role
     * @return true or false for success / failure
     * @throws RecordException -  in case there is no such team or season, or in case the user is already present in the system.
     */
    @Override
    public boolean addPlayer(String teamName, String SeasonYear, String Username, String Password, String firstName, String lastName, String bDate, String role) throws RecordException {
        return false;
    }

    /**
     * Removes a player from the team.
     * Assumes input was already validated for null and empty inputs.
     *
     * @param teamName   - team's name
     * @param seasonYear - team's name
     * @param username   - username
     * @return true or false for success / failure
     */
    @Override
    public boolean removePlayer(String teamName, String seasonYear, String username) {
        return false;
    }

    /**
     * Adds a coach into an existing team. Assumes input was already validated for null and empty inputs.
     * Assumes input was already validated for null and empty inputs.
     *
     * @param teamName   - team's name
     * @param SeasonYear - Requested year in which the team plays
     * @param Username   - username
     * @param Password   - password
     * @param firstName  - first name
     * @param lastName   - last name
     * @param bDate      - format: dd-mm-yyyy
     * @param role       - role
     * @param training   - Coach's trining
     * @return true or false for success / failure
     * @throws RecordException - in case there is no such team or season, or in case the user is already present in the system.
     */
    @Override
    public boolean addCoach(String teamName, String SeasonYear, String Username, String Password, String firstName, String lastName, String bDate, String role, String training) throws RecordException {
        return false;
    }

    /**
     * Removes a coach from the team.
     * Assumes input was already validated for null and empty inputs.
     *
     * @param teamName   - team's name
     * @param seasonYear - team's name
     * @param username   - username
     * @return true or false for success / failure
     */
    @Override
    public boolean removeCoach(String teamName, String seasonYear, String username) {
        return false;
    }

    /**
     * Adds a Team Manager into an existing team. Assumes input was already validated for null and empty inputs.
     * Assumes input was already validated for null and empty inputs.
     *
     * @param teamName   - team's name
     * @param SeasonYear - Requested year in which the team plays
     * @param Username   - username
     * @param Password   - password
     * @param firstName  - first name
     * @param lastName   - last name
     * @return true or false for success / failure
     * @throws RecordException - in case there is no such team or season, or in case the user is already present in the system.
     */
    @Override
    public boolean addTeamManager(String teamName, String SeasonYear, String Username, String Password, String firstName, String lastName) throws RecordException {
        return false;
    }

    /**
     * Removes a Team Manager from the team.
     * Assumes input was already validated for null and empty inputs.
     *
     * @param teamName   - team's name
     * @param seasonYear - team's name
     * @param username   - username
     * @return true or false for success / failure
     */
    @Override
    public boolean removeTeamManager(String teamName, String seasonYear, String username) {
        return false;
    }

    /**
     * Adds a field into an existing team
     * Assumes input was already validated for null and empty inputs.
     *
     * @param teamName   - team's name
     * @param seasonYear - team's name
     * @param fieldName  - Field's name
     * @param city       - city
     * @return true or false for success / failure
     * @throws RecordException - in case team/season doesn't exist or a field name is already present in the system.
     */
    @Override
    public boolean addField(String teamName, String seasonYear, String fieldName, String city) throws RecordException {
        return false;
    }

    /**
     * Removes a Field from the team.
     * Assumes input was already validated for null and empty inputs.
     *
     * @param teamName   - team's name
     * @param seasonYear - team's name
     * @param fieldName  - Field's name
     * @return true or false for success / failure
     */
    @Override
    public boolean removeField(String teamName, String seasonYear, String fieldName) {
        return false;
    }

    /**
     * Nominates a team Owner.
     * Assumes input was already validated for null and empty inputs.
     * Throws Exception if there is no such a user, team or season.
     *
     * @param teamName   - the requested team
     * @param SeasonYear - Requested year in which the team plays
     * @param Username   - Team Owner's user
     * @return true or false for success / failure
     * @throws RecordException- in case team/season/username doesn't exist .
     */
    @Override
    public boolean nominateTeamOwner(String teamName, String SeasonYear, String Username) throws RecordException {
        return false;
    }

    /**
     * Removes a nomination of a team owner.
     * Assumes input was already validated for null and empty inputs.
     *
     * @param teamName   - the requested team
     * @param SeasonYear - Requested year in which the team plays
     * @param Username   - Team Owner's user
     * @return true or false for success / failure
     */
    @Override
    public boolean discardNominationForTeamOwner(String teamName, String SeasonYear, String Username) {
        return false;
    }

    /**
     * Nominates a team Manager.
     * Assumes input was already validated for null and empty inputs.
     * TODO: think how to specify authorities
     *
     * @param teamName   - the requested team
     * @param SeasonYear - Requested year in which the team plays
     * @param Username   - Team Owner's user
     * @return true or false for success / failure
     * @throws RecordException - if there is no such a user, team or season.
     */
    @Override
    public boolean nominateTeamManager(String teamName, String SeasonYear, String Username) throws RecordException {
        return false;
    }


    //endregion

    //region Policy Management

    /**
     * Receives a policy by its name for a specific season & league and sets it.
     *
     * @param policy     - "Simple Policy" or "Heuristic Policy"
     * @param leagueName -
     * @param seasonYear -
     * @return true for success, false for failure
     */
    @Override
    public boolean defineGameSchedulingPolicy(String policy, String leagueName, String seasonYear) throws RecordException {

        // Only Representative is allowed to define a policy.
        if (!(user instanceof RepresentativeFootballAssociation))
            throw new RecordException("You don't have permission to define a game scheduling policy");
        RepresentativeFootballAssociation repUser = (RepresentativeFootballAssociation) user;

        // Validate season & league
        Season season = ValidateObject.getValidatedSeason(leagueName, seasonYear);
        League league = ValidateObject.getValidatedLeague(leagueName);

        // Set requested policy
        switch (policy) {
            case "Regular Schedule Policy":
                repUser.SetGamesAssigningPolicy(new SimpleGamesAssigningPolicy(), league, season);
                runGameSchedulingAlgorithm(leagueName, seasonYear);
                break;
            case "One Round Schedule Policy":
                repUser.SetGamesAssigningPolicy(new OneRoundGamesAssigningPolicy(), league, season);
                break;
            default:
                throw new RecordException("You have to choose policy");
        }
        return true;
    }

    /**
     * Receives a policy by its name for a specific season & league and sets it.
     *
     * @param policy     - "Policy 1" "Policy 2"
     * @param leagueName -
     * @param seasonYear -
     * @return true for success, false for failure
     */
    @Override
    public boolean defineScoreTablePolicy(String policy, String leagueName, String seasonYear) throws RecordException {

        // Only Representative is allowed to define a policy.
        if (!(user instanceof RepresentativeFootballAssociation))
            throw new RecordException("You don't have permission to define a score policy");
        RepresentativeFootballAssociation repUser = (RepresentativeFootballAssociation) user;

        // Validate season & league
        Season season = ValidateObject.getValidatedSeason(leagueName, seasonYear);
        League league = ValidateObject.getValidatedLeague(leagueName);

        // Set requested policy
        switch (policy) {
            case "Classic Score Policy":
                repUser.SetScoreTablePolicy(new RegularScorePolicy(), league, season);
                break;

            case "Draw equals Lose Score Policy":
                repUser.SetScoreTablePolicy(new ScoreTablePolicy2(), league, season);
                break;

            default:
                throw new RecordException("You must choose a policy");

        }
        return true;
    }

    /**
     * Activates Game Scheduling Algorithm for a specific season & league and sets it.
     *
     * @param leagueName -
     * @param seasonYear -
     * @return true for success, false for failure
     */
    @Override
    public boolean runGameSchedulingAlgorithm(String leagueName, String seasonYear) throws RecordException {

        // Only Representative is allowed to define a policy.
        if (!(user instanceof RepresentativeFootballAssociation))
            return false;
        RepresentativeFootballAssociation repUser = (RepresentativeFootballAssociation) user;
        Season season = FootballSystem.getInstance().getSeasonDB().getAllSeasons().get(seasonYear);
        League league = FootballSystem.getInstance().getLeagueDB().getAllLeagues().get(leagueName);

        repUser.activateGamesAssigning(league, season);
        repUser.activateGamesAssigning(league, season);


        return true;
    }
    //endregion

    //region Game Management

    /**
     * Adds an event into a existing game item
     *
     * @param gameID      - should use "getGames" method first in order to receive the correct gameID
     * @param eventType   - out of event type enum
     * @param description Freestyle Description
     * @return
     */
    @Override
    public boolean addEvent(int gameID, String eventType, String description) throws RecordException {
        // Only Referee is allowed to add an event.
        if (!(user instanceof Referee)) {
            throw new RecordException("You dont have have permission to add event");
        }
        ValidateObject.getValidatedGame(gameID);
        Referee referee = (Referee) user;
        referee.addEventToAssignedGame(gameID, EEventType.valueOf(eventType), description);
        JDBCConnector jdbcConnector = new JDBCConnector();
        jdbcConnector.connectDBSaveData();
        jdbcConnector.connectDBUploadData();
        return true;
    }


    /**
     * Updates an event of an existing game item
     *
     * @param gameID      - should use "getGames" method first in order to receive the correct gameID
     * @param eventIndex  - should use "getEvents" method first
     * @param eventType   - out of event type enum
     * @param description Freestyle Description
     * @return
     */
    @Override
    public boolean updateEvent(int gameID, int eventIndex, String eventType, String description) throws RecordException {
        // Only Referee is allowed to update an event.
        if (!(user instanceof Referee))
            return false;

        ValidateObject.getValidatedGame(gameID);

        Referee referee = (Referee) user;
        try {
            referee.updateEventToAssignedGame(gameID, eventIndex, EEventType.valueOf(eventType), description);
        } catch (Exception e) {
            String cause = e.getMessage();
            throw new RecordException(cause);
        }

        return true;
    }

    /**
     * Removes an event from an existing game item
     *
     * @param gameID     - should use "getGames" method first in order to receive the correct gameID
     * @param eventIndex - should use "getEvents" method first
     * @return
     */
    @Override
    public boolean removeEvent(int gameID, int eventIndex) throws RecordException {
        // Only Referee is allowed to remove an event.
        if (!(user instanceof Referee))
            return false;

        ValidateObject.getValidatedGame(gameID);
        Referee referee = (Referee) user;
        try {
            referee.removeEventFromAssignedGame(gameID, eventIndex);
        } catch (Exception e) {
            String cause = e.getMessage();
            throw new RecordException(cause);
        }

        return true;
    }

    /**
     * Updates an event of an existing game item, until 5 hours after game is finished.
     * Can be preformed only by main referee.
     *
     * @param gameID      - should use "getGames" method first in order to receive the correct gameID
     * @param eventIndex  - should use "getEvents" method first
     * @param eventType   - out of event type enum
     * @param description Freestyle Description
     * @return
     */
    @Override
    public boolean updateEventAfterGameOver(int gameID, int eventIndex, String eventType, String description) throws RecordException {
        // Only Referee is allowed to remove an event.
        if (!(user instanceof Referee))
            return false;

        ValidateObject.getValidatedGame(gameID);
        Referee referee = (Referee) user;
        try {
            referee.editEventsAfterGameOver(gameID, eventIndex, EEventType.valueOf(eventType), description);
        } catch (Exception e) {
            String cause = e.getMessage();
            throw new RecordException(cause);
        }

        return true;
    }

    /**
     * Updates an event of an existing game item, until 5 hours after game is finished.
     * Can be preformed only by main referee.
     *
     * @param gameID     - should use "getGames" method first in order to receive the correct gameID
     * @param eventIndex - should use "getEvents" method first
     * @return
     */
    @Override
    public boolean removeEventAfterGameOver(int gameID, int eventIndex) throws RecordException {
        // Only Referee is allowed to remove an event.
        if (!(user instanceof Referee))
            return false;

        ValidateObject.getValidatedGame(gameID);
        Referee referee = (Referee) user;
        try {
            referee.removeEventsAfterGameOver(gameID, eventIndex);
        } catch (Exception e) {
            String cause = e.getMessage();
            throw new RecordException(cause);
        }
        return true;
    }

    /**
     * Export an Excel report holds all events in the required game
     *
     * @param gameID     -
     * @param pathToSave -
     * @param reportName -
     * @return
     */
    @Override
    public boolean exportGameReport(int gameID, String pathToSave, String reportName) throws RecordException {
        // Only Referee is allowed to add an event.
        if (!(user instanceof Referee)) {
            throw new RecordException("You dont have permission to create report");
        }

        ValidateObject.getValidatedGame(gameID);

        Referee referee = (Referee) user;
        try {
            referee.exportReport(gameID, pathToSave);
        } catch (Exception e) {
            String cause = e.getMessage();
            throw new RecordException(cause);
        }
        return true;
    }

    /**
     * Change a game's location. Should send a notification to all related referees.
     *
     * @param gameID       -
     * @param newFieldName -
     * @return
     */
    @Override
    public boolean changeGameLocation(int gameID, String newFieldName) {
        return false;
    }

    /**
     * Change a game's date. Should send a notification to all related referees.
     *
     * @param gameID  -
     * @param newDate -
     * @return
     */
    @Override
    public boolean changeGameDate(int gameID, String newDate) {
        return false;
    }
    //endregion

    // server - client - tair *******************************************************

    public StringBuilder getAllTeams() throws RecordException {
        StringBuilder answer = new StringBuilder();
        if (user instanceof TeamOwner) {
            TeamOwner teamOwner = (TeamOwner) user;
            List<AdditionalInfo> additionalInfos = teamOwner.getAdditionalInfo();
            Set<String> teamsByOwner = new HashSet<>();
            for (AdditionalInfo a :
                    additionalInfos) {
                teamsByOwner.add(a.getTeam().getName());
            }
            if (teamsByOwner.size() == 0) {
                throw new RecordException("You are not owing any team!");
            }
            fillAnswer(answer, teamsByOwner);
        }
        return answer;
    }

    public StringBuilder getAllSeasons() throws RecordException {
        StringBuilder answer = new StringBuilder();
        SeasonDB seasonDB = FootballSystem.getInstance().getSeasonDB();
        if (seasonDB != null) {
            HashMap<String, Season> seasonHashMap = seasonDB.getAllSeasons();
            if (seasonHashMap != null && seasonHashMap.size() > 0) {
                fillAnswer(answer, seasonHashMap.keySet());
            } else {
                throw new RecordException("There is no seasons at the DB");
            }
        } else {
            throw new RecordException("Season DB does not exits");
        }
        return answer;
    }

    private void fillAnswer(StringBuilder answer, Set<String> strings) {
        Set<String> seasonSet = strings;
        answer.append("Ok,");
        for (String season : seasonSet) {
            answer.append(season);
            answer.append(",");
        }
    }

    public StringBuilder availableSeasonsForTeam(String teamName) {
        StringBuilder answer = new StringBuilder();
        Team team = FootballSystem.getInstance().getTeamDB().getAllTeams().get(teamName);
        if (team != null) {
            Set<String> seasonSet = team.getAdditionalInfoWithSeasons().keySet();
            fillAnswer(answer, seasonSet);
        }
        return answer;
    }

    public StringBuilder getAllLeagues() throws RecordException {
        StringBuilder answer = new StringBuilder();
        LeagueDB leagueDB = FootballSystem.getInstance().getLeagueDB();
        if (leagueDB != null) {
            HashMap<String, League> leagueHashMap = leagueDB.getAllLeagues();
            if (leagueHashMap != null && leagueHashMap.size() > 0) {
                fillAnswer(answer, leagueHashMap.keySet());
            } else {
                throw new RecordException("There is no seasons at the DB");
            }
        } else {
            throw new RecordException("Season DB does not exits");
        }
        return answer;
    }

    public StringBuilder getAllFields() throws RecordException {
        StringBuilder answer = new StringBuilder();
        FieldDB fieldDB = FootballSystem.getInstance().getFieldDB();
        if (fieldDB != null) {
            HashMap<String, Field> fieldsHashMap = fieldDB.getAllFields();
            if (fieldsHashMap != null && fieldsHashMap.size() > 0) {
                fillAnswer(answer, fieldsHashMap.keySet());
            } else {
                throw new RecordException("There is no seasons at the DB");
            }
        } else {
            throw new RecordException("Season DB does not exits");
        }
        return answer;
    }

    public StringBuilder getCoachesForTeamAtSeason(String teamName, String seasonYear) throws RecordException {
        StringBuilder answer = new StringBuilder();
        Team team = FootballSystem.getInstance().getTeamDB().getAllTeams().get(teamName);
        AdditionalInfo additionalInfo = team.getAdditionalInfoWithSeasons().get(seasonYear);
        if (additionalInfo != null) {
            Set<String> coachSet = additionalInfo.getCoaches();
            if (coachSet != null) {
                fillAnswer(answer, coachSet);
            } else {
                throw new RecordException("There is no coaches at the team " + teamName + " at season " + seasonYear);
            }
        }
        return answer;
    }

    public StringBuilder getPlayersForTeamAtSeason(String teamName, String seasonYear) throws RecordException {
        StringBuilder answer = new StringBuilder();
        Team team = FootballSystem.getInstance().getTeamDB().getAllTeams().get(teamName);
        AdditionalInfo additionalInfo = team.getAdditionalInfoWithSeasons().get(seasonYear);
        if (additionalInfo != null) {
            Set<String> playerSet = additionalInfo.getPlayers();
            if (playerSet != null) {
                fillAnswer(answer, playerSet);
            } else {
                throw new RecordException("There is no players at the team " + teamName + " at season " + seasonYear);
            }
        }
        return answer;
    }

    public StringBuilder getTeamManagersForTeamAtSeason(String teamName, String seasonYear) throws RecordException {
        StringBuilder answer = new StringBuilder();
        Team team = FootballSystem.getInstance().getTeamDB().getAllTeams().get(teamName);
        AdditionalInfo additionalInfo = team.getAdditionalInfoWithSeasons().get(seasonYear);
        if (additionalInfo != null) {
            Set<String> teamManagerSet = additionalInfo.getTeamManagersHashSet();
            if (teamManagerSet != null) {
                fillAnswer(answer, teamManagerSet);
            } else {
                throw new RecordException("There is no Team Managers at the team " + teamName + " at season " + seasonYear);
            }
        }
        return answer;
    }

    public StringBuilder getFieldsForTeamAtSeason(String teamName, String seasonYear) throws RecordException {
        StringBuilder answer = new StringBuilder();
        Team team = FootballSystem.getInstance().getTeamDB().getAllTeams().get(teamName);
        if (team != null) {
            Set<String> fieldSet = team.getFields().keySet();
            if (fieldSet != null) {
                fillAnswer(answer, fieldSet);
            } else {
                throw new RecordException("There is no Fields at the team " + teamName + " at season " + seasonYear);
            }
        }
        return answer;
    }

    public StringBuilder getGameIds() throws RecordException {
        StringBuilder answer = new StringBuilder();
        if (user instanceof Referee) {
            Referee rep = (Referee) user;
            List<Game> games = rep.getMyGames();
            Set<String> gameSet = new HashSet<>();
            for (Game g :
                    games) {
                gameSet.add(String.valueOf(g.getGID()));
            }
            if (gameSet.size() == 0) {
                throw new RecordException("You are not judging any game!");
            }
            fillAnswer(answer, gameSet);
        }
        return answer;

    }

    public StringBuilder checkNotification() throws RecordException {
        HashMap<Integer, String[]> notification = user.getPendingNotifications();
        if (notification.size() == 0) {
            throw new RecordException("None");
        }
        StringBuilder answer = new StringBuilder();
        Collection<String[]> strings = notification.values();
        HashSet notifi = new HashSet();
        for (String[] array : strings) {
            for (int i = 0; i < array.length; i++) {
                notifi.add(array[i]);
            }
        }
        fillAnswer(answer, notifi);
        user.clearNotification();
        return answer;
    }

    public StringBuilder availableSeasonsForLeague(String league) throws RecordException{
        StringBuilder answer = new StringBuilder();
        League league1 = FootballSystem.getInstance().getLeagueDB().getAllLeagues().get(league);
        if (league1 != null) {
            Map<String, SeasonLeagueBinder> seasonLeagueBinderMap = league1.getSeasonBinders();
            if (seasonLeagueBinderMap != null) {
                Set<String> seasonSet = seasonLeagueBinderMap.keySet();
                fillAnswer(answer, seasonSet);
            }
            else{
                throw new RecordException("There isn't any season at the league");
            }

        } else {
            throw new RecordException("The league is not exist");
        }
        return answer;
    }
}