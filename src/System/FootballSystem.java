package System;

import AssociationAssets.*;
import DB.*;
import Model.RecordException;
import OutSourceSystems.ProxyAccountingSystem;
import OutSourceSystems.ProxyTaxRegulationSystem;
import PoliciesAndAlgorithms.GamesAssigningPolicy;
import Security.SecuritySystem;
import Users.*;

import javax.security.auth.login.FailedLoginException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * this class is Singleton for the Football System.
 * Aouthors: Tair Cohen
 */
public class FootballSystem {

    SecuritySystem securitySystem = new SecuritySystem();
    List<Guest> guestList = new LinkedList<>();
    Map<String, Fan> fansHashMap = new HashMap<>();
    TeamDB teamDB = new TeamDB();
    GameDB gameDB = new GameDB();
    FieldDB fieldDB = new FieldDB();
    SeasonDB seasonDB = new SeasonDB();
    LeagueDB leagueDB = new LeagueDB();
    ProxyAccountingSystem proxyAccountingSystem = new ProxyAccountingSystem();
    ProxyTaxRegulationSystem proxyTaxRegulationSystem = new ProxyTaxRegulationSystem();
    Map<String, Referee> refereeMap = new HashMap<>();
    Map<String, Player> playerMap = new HashMap<>();
    Map<String, Coach> coachMap = new HashMap<>();
    Map<String, SystemManager> systemManagerMap = new HashMap<>();
    Map<String, TeamManager> teamManagerMap = new HashMap<>();
    Map<String, TeamOwner> teamOwnerMap = new HashMap<>();
    Map<String, RepresentativeFootballAssociation> representativeFootballAssociationMap = new HashMap<>();
    ScheduledExecutorService ses = Executors.newScheduledThreadPool(5);



    /**
     * Create an instance of the class at the time of class loading
     */
    private static final FootballSystem instance = new FootballSystem();

    /**
     * private constructor to prevent others from instantiating this class
     */
    private FootballSystem() {
        initialize();
    }

    /**
     * this function initialize the system ,creating admin system manager and connecting to the out source systems.
     */
    private void initialize() {
        //creating system manager - admin
        signIn("admin", "admin", "admin", "admin");
        creatingSystemManager("admin", "admin", "admin");
        //connect with accounting system
        proxyAccountingSystem.connect();
        //connect with tax regulation system
        proxyTaxRegulationSystem.connect();
    }

    public SecuritySystem getSecuritySystem() {
        return securitySystem;
    }

    public List<Guest> getGuestList() {
        return guestList;
    }

    public Map<String, Fan> getFansHashMap() {
        return fansHashMap;
    }

    public TeamDB getTeamDB() {
        return teamDB;
    }

    public GameDB getGameDB() {
        return gameDB;
    }

    public FieldDB getFieldDB() {
        return fieldDB;
    }

    public SeasonDB getSeasonDB() {
        return seasonDB;
    }

    public LeagueDB getLeagueDB() {
        return leagueDB;
    }

    public Map<String, Referee> getRefereeMap() {
        return refereeMap;
    }

    public Map<String, Player> getPlayerMap() {
        return playerMap;
    }

    public Map<String, Coach> getCoachMap() {
        return coachMap;
    }

    public Map<String, SystemManager> getSystemManagerMap() {
        return systemManagerMap;
    }

    public Map<String, TeamManager> getTeamManagerMap() {
        return teamManagerMap;
    }

    public Map<String, TeamOwner> getTeamOwnerMap() {
        return teamOwnerMap;
    }

    public Map<String, RepresentativeFootballAssociation> getRepresentativeFootballAssociationMap() {
        return representativeFootballAssociationMap;
    }

    /**
     * Provide a global point of access to the instance
     */
    public static FootballSystem getInstance() {
        return instance;
    }

    /**
     * this function sign in a user to the system
     * @param userName
     * @param password
     * @param firstName
     * @param lastName
     * @return Fan that the function created
     */
    public Fan signIn(String userName, String password, String firstName,
                      String lastName) {
        // check if this user name already exits
        if (fansHashMap.containsKey(userName)) {
            System.out.println("This user name is already exist.");
            return null;
        }
        // input check
        else if (userName == null || userName.isEmpty() ||
                password == null || password.isEmpty() ||
                firstName == null || firstName.isEmpty() ||
                lastName == null || lastName.isEmpty()) {
            System.out.println("please insert valid inputs");
            return null;
        }
        securitySystem.addNewUser(userName, password);
        Fan fan = new Fan(userName, firstName, lastName);
        this.fansHashMap.put(userName, fan);
        return fan;
    } // useCase 2.2

    /**
     * this function creating new RepresentativeFootballAssociation and save it the hash maps of the class.
     * @param userName
     * @param firstName
     * @param lastName
     * @param gamesAssigningPolicy
     * @return the fan that was created
     */
    public Fan creatingRepresentativeFootballAssociation(String userName, String firstName, String lastName, GamesAssigningPolicy gamesAssigningPolicy) {
        if (this.getFansHashMap().containsKey(userName)) {
            if (this.getFansHashMap().get(userName) instanceof RepresentativeFootballAssociation) {
                System.out.println("The user name: " + userName + " is already a RepresentativeFootballAssociation");
                return null;
            }
        }
        Fan fan = new RepresentativeFootballAssociation(userName, firstName, lastName);
        this.fansHashMap.put(userName, fan);
        this.representativeFootballAssociationMap.put(userName, (RepresentativeFootballAssociation) fan);
        return fan;
    }

    /**
     *
     * @param userName
     * @param firstName
     * @param lastName
     * @param training
     * @return the fan that was created
     */
    public Fan creatingReferee(String userName, String firstName, String lastName, EReferee training) {
        if (this.getFansHashMap().containsKey(userName)) {
            if (this.getFansHashMap().get(userName) instanceof Referee) {
                System.out.println("The user name: " + userName + " is already a Referee");
                return null;
            }
        }
        Fan fan = new Referee(userName, firstName, lastName, training);
        this.fansHashMap.put(userName, fan);
        this.refereeMap.put(userName, (Referee) fan);
        return fan;
    }

    /**
     *
     * @param userName
     * @param firstName
     * @param lastName
     * @param training
     * @param eCoachRole
     * @return the fan that was created
     */
    public Fan creatingCoach(String userName, String firstName, String lastName, ETraining training,
                             ECoachRole eCoachRole) {
        if (this.getFansHashMap().containsKey(userName)) {
            if (this.getFansHashMap().get(userName) instanceof Coach) {
                System.out.println("The user name: " + userName + " is already a Coach");
                return null;
            }
        }
        Fan fan = new Coach(userName, firstName, lastName, training, eCoachRole);
        this.fansHashMap.put(userName, fan);
        this.coachMap.put(userName, (Coach) fan);
        return fan;
    }

    /**
     *
     * @param userName
     * @param firstName
     * @param lastName
     * @return the fan that was created
     */
    public Fan creatingTeamOwner(String userName, String firstName, String lastName) {
        if (this.getFansHashMap().containsKey(userName)) {
            if (this.getFansHashMap().get(userName) instanceof TeamOwner) {
                System.out.println("The user name: " + userName + " is already a Team Owner");
                return null;
            }
        }
        Fan fan = new TeamOwner(userName, firstName, lastName);
        this.fansHashMap.put(userName, fan);
        this.teamOwnerMap.put(userName, (TeamOwner) fan);
        return fan;
    }

    /**
     *
     * @param userName
     * @param firstName
     * @param lastName
     * @return the fan that was created
     */
    public Fan creatingTeamManager(String userName, String firstName, String lastName) {
        if (this.getFansHashMap().containsKey(userName)) {
            if (this.getFansHashMap().get(userName) instanceof TeamManager) {
                System.out.println("The user name: " + userName + " is already a Team Manager");
                return null;
            }
        }
        Fan fan = new TeamManager(userName, firstName, lastName);
        this.fansHashMap.put(userName, fan);
        this.teamManagerMap.put(userName, (TeamManager) fan);
        return fan;
    }

    /**
     *
     * @param userName
     * @param firstName
     * @param lastName
     * @param bDate
     * @param playerRole
     * @return the fan that was created
     */
    public Fan creatingPlayer(String userName, String firstName, String lastName
            , Date bDate, EPlayerRole playerRole) {
        if (this.getFansHashMap().containsKey(userName)) {
            if (this.getFansHashMap().get(userName) instanceof Player) {
                System.out.println("The user name: " + userName + " is already a Player");
                return null;
            }
        }
        Fan fan = new Player(userName, firstName, lastName, bDate, playerRole);
        this.fansHashMap.put(userName, fan);
        this.playerMap.put(userName, (Player) fan);
        return fan;
    }

    /**
     *
     * @param userName
     * @param firstName
     * @param lastName
     * @return the fan that was created
     */
    public Fan creatingSystemManager(String userName, String firstName, String lastName) {
        if (this.getFansHashMap().containsKey(userName)) {
            if (this.getFansHashMap().get(userName) instanceof SystemManager) {
                System.out.println("The user name: " + userName + " is already a SystemManager");
                return null;
            }
        }
        Fan fan = new SystemManager(userName, firstName, lastName);
        this.fansHashMap.put(userName, fan);
        this.systemManagerMap.put(userName, (SystemManager) fan);
        return fan;
    }

    /**
     * this function create field and adds it to the DB
     * @param name
     * @param city
     * @param capacity
     * @return the field that was created
     */
    public Field createField(String name, String city, int capacity) {
        if (fieldDB.getAllFields().containsKey(name)) {
            return fieldDB.getAllFields().get(name);
        }
        Field field = new Field(name, city, capacity);
        this.addFieldToDB(field);
        return field;
    }

    /**
     * this function log in a user to the system
     * @param userName
     * @param password
     * @return the fan if the login succeed or null if the username or password are incorrect
     */
    public Fan login(String userName, String password)  throws FailedLoginException {
        if (securitySystem.checkPasswordForLogIn(userName, password)) {
            if (this.fansHashMap.containsKey(userName)) {
                return fansHashMap.get(userName);
            }
        }
        //System.out.println("user name or password incorrect");
        return null;
    }  // useCase 2.3

    /**
     * this function removes user from the system by his user name
     * @param userName
     */
    public void removeUser(String userName) {
        if (this.fansHashMap.get(userName) instanceof Player) {
            this.playerMap.remove(userName);
        } else if (this.fansHashMap.get(userName) instanceof RepresentativeFootballAssociation) {
            this.representativeFootballAssociationMap.remove(userName);
        } else if (this.fansHashMap.get(userName) instanceof Referee) {
            this.refereeMap.remove(userName);
        } else if (this.fansHashMap.get(userName) instanceof Coach) {
            this.coachMap.remove(userName);
        } else if (this.fansHashMap.get(userName) instanceof TeamManager) {
            this.teamManagerMap.remove(userName);
        } else if (this.fansHashMap.get(userName) instanceof TeamOwner) {
            this.teamOwnerMap.remove(userName);
        } else if (this.fansHashMap.get(userName) instanceof SystemManager) {
            this.systemManagerMap.remove(userName);
        }
        if (this.fansHashMap.containsKey(userName)) {
            this.fansHashMap.remove(userName);
            System.out.println("The user name: " + userName + " was removed successfully");
        }
        this.securitySystem.removeUser(userName);
    }

    /**
     * this function gets a fan by his user name
     * @param userName
     * @return fan
     */
    public Fan getFanByUserName(String userName) {
        if (this.fansHashMap.containsKey(userName)) {
            return this.fansHashMap.get(userName);
        }
        return null;
    }

    /**
     *
     * @param userName
     * @return true if fan is exist in the system
     */
    public boolean existFanByUserName(String userName) {
        return this.fansHashMap.containsKey(userName);
    }

    /**
     * this function adds team to DB
     * @param team
     */
    public void addTeamToDB(Team team) throws RecordException {
        if (team != null) {
            if (this.teamDB.getAllTeams().containsKey(team.getName())) {
                throw new RecordException("This team name is already exists in the system");
            }
            this.teamDB.addTeam(team, team.getName());
        }
    }

    /**
     * this function removes team from DB
     * @param teamName
     */
    public void removeTeamFromDB(String teamName) {
        this.teamDB.removeTeam(teamName);
    }

    /**
     * this function adds league to DB
     * @param league
     */
    public void addLeagueToDB(League league) {
        if (league != null) {
            this.leagueDB.addLeague(league, league.getLeagueName());
        }
    }

    /**
     * this function removes league from DB
     * @param league
     */
    public void removeLeagueFromDB(String league) {
        this.leagueDB.removeLeague(league);
    }

    /**
     * this function adds field to DB
     * @param field
     */
    public void addFieldToDB(Field field) {
        if (field != null) {
            this.fieldDB.addField(field, field.getName());
        }
    }

    /**
     * this function removes field from DB
     * @param fieldName
     */
    public void removeFieldFromDB(String fieldName) {
        this.fieldDB.removeField(fieldName);
    }

    /**
     * this function adds season to DB
     * @param season
     */
    public void addSeasonToDB(Season season) {
        if (season != null) {
            this.seasonDB.addSeason(season, season.getYear());
        }
    }

    /**
     * this function removes season from DB
     * @param year
     */
    public void removeSeasonFromDB(String year) {
        this.seasonDB.removeSeason(year);
    }

    /**
     * this function adds game to DB
     * @param game
     */
    public void addGameToDB(Game game) {
        if (game != null) {
            this.gameDB.addGame(game, game.getGID());
        }
    }

    /**
     * this function removes game from DB
     * @param gid
     */
    public void removeGameFromDB(int gid) {
        this.gameDB.removeGame(gid);
    }

    /**
     *
     * @param userName
     * @return true if player found playing at some team
     */
    public boolean findPlayerAtTeamByUserName(String userName) {
        for (Team team : this.teamDB.getAllTeams().values()) {
            if (team.findPlayer(userName) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param userName
     * @return true if coach found coaching at some team
     */
    public boolean findCoachAtTeamByUserName(String userName) {
        for (Team team : this.teamDB.getAllTeams().values()) {
            if (team.findCoach(userName) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param userName
     * @return true if team owner found owing at some team
     */
    public boolean findTeamOwnerAtTeamByUserName(String userName) {
        for (Team team : this.teamDB.getAllTeams().values()) {
            if (team.findTeamOwner(userName) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param userName
     * @return true if manager found manage at some team
     */
    public boolean findTeamManagerAtTeamByUserName(String userName) {
        for (Team team : this.teamDB.getAllTeams().values()) {
            if (team.findManager(userName) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param leagueName
     * @return true if a league exits it the DB
     */
    public boolean existLeagueByName(String leagueName) {
        return this.leagueDB.getAllLeagues().containsKey(leagueName);
    }

    /**
     *
     * @param fieldName
     * @return true if a field exits it the DB
     */
    public boolean existFieldByName(String fieldName) {
        return this.fieldDB.getAllFields().containsKey(fieldName);
    }

    /**
     * this function adds referee to the system
     * @param referee
     */
    public void addReferee(Referee referee) {
        if (referee == null) return;
        refereeMap.put(referee.getUserName(), referee);
    }

    /**
     * this function adds coach to the system
     * @param coach
     */
    public void addCoach(Coach coach) {
        if (coach == null) return;
        coachMap.put(coach.getUserName(), coach);
    }

    /**
     * this function adds player to the system
     * @param player
     */
    public void addPlayer(Player player) {
        if (player == null) return;
        playerMap.put(player.getUserName(), player);
    }

    /**
     * this function adds systemManager to the system
     * @param systemManager
     */
    public void addSystemManager(SystemManager systemManager) {
        if (systemManager == null) return;
        this.systemManagerMap.put(systemManager.getUserName(), systemManager);
    }

    /**
     *
     * @param userName
     * @return referee by his user name
     */
    public Referee getRefereeByUseName(String userName) {
        return this.refereeMap.get(userName);
    }

    /**
     *
     * @param userName
     * @return coach by his user name
     */
    public Coach getCoachByUserName(String userName) {
        return this.coachMap.get(userName);
    }

    /**
     *
     * @param userName
     * @return player by his user name
     */
    public Player getPlayerByUserName(String userName) {
        return this.playerMap.get(userName);
    }

    /**
     *
     * @param userName
     * @return RepresentativeFootballAssociation by his user name
     */
    public RepresentativeFootballAssociation getRepresentativeFootballAssociationByUseName(String userName) {
        return this.representativeFootballAssociationMap.get(userName);
    }

    /**
     *
     * @param userName
     * @return TeamOwner by his username
     */
    public TeamOwner getTeamOwnerByUserName(String userName) {
        return this.teamOwnerMap.get(userName);
    }

    /**
     *
     * @param userName
     * @return SystemManager by his username
     */
    public SystemManager getSystemManagerByUserName(String userName) {
        return this.systemManagerMap.get(userName);
    }

    /**
     *
     * @param userName
     * @return TeamManager by his username
     */
    public TeamManager getTeamManagerByUserName(String userName) {
        return this.teamManagerMap.get(userName);
    }
}
