package DAL;

import AssociationAssets.Field;
import AssociationAssets.League;
import AssociationAssets.Season;
import AssociationAssets.Team;
import Security.SecuritySystem;
import Users.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

import AssociationAssets.*;
import PoliciesAndAlgorithms.*;
import System.*;

/**
 * Data Uploader is in charge of fetching data from the DB and setting it in the system's Hashmaps and data sets.
 * Author: Amit Shakarchy
 */

public class DataUploader {

    //region Fields
    private HashMap<Integer, Game> allGames;
    DatabaseManager databaseManager;

    FootballSystem system;
    HashMap<String, Season> allSeasons;
    HashMap<String, League> allLeagues;
    HashMap<String, Team> allTeams;
    HashMap<String, Field> allFields;
    Map<String, Coach> allCoaches;

    // Users:
    Map<String, Fan> allFans;
    Map<String, TeamOwner> allTeamOwners;
    Map<String, Player> allPlayers;
    private Map<String, TeamManager> allTeamManagers;
    private Map<String, SystemManager> allSystemManagers;
    private Map<String, RepresentativeFootballAssociation> allRFAs;
    private Map<String, Referee> allReferees;
    //endregion


    public DataUploader(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;

        system = FootballSystem.getInstance();
        allLeagues = system.getLeagueDB().getAllLeagues();
        allSeasons = system.getSeasonDB().getAllSeasons();
        allTeams = system.getTeamDB().getAllTeams();
        allFields = system.getFieldDB().getAllFields();
        allFans = system.getFansHashMap();
        allTeamOwners = system.getTeamOwnerMap();
        allPlayers = system.getPlayerMap();
        allCoaches = system.getCoachMap();
        allTeamManagers = system.getTeamManagerMap();
        allSystemManagers = system.getSystemManagerMap();
        allRFAs = system.getRepresentativeFootballAssociationMap();
        allReferees = system.getRefereeMap();
        allGames = system.getGameDB().getAllGames();
    }

    /**
     * Conducts all needed data uploading
     */
    public void uploadData() {
        //System.out.println("start user uploading");
        uploadUsers();
        uploadSystemAssets();
    }

    /**
     * Conducts all System Assets data uploading
     */
    public void uploadSystemAssets() {
        //System.out.println("start upload system assets");
        uploadFields();
        //System.out.println("uploaded Fields");
        uploadLeagues();
        //System.out.println("uploaded Leagues");
        uploadSeasons();
        //System.out.println("uploaded Seasons");
        uploadTeams();
        //System.out.println("uploaded Teams");
        uploadGames();
        //System.out.println("uploaded Games");
        attachTeamsGames();
        //System.out.println("uploaded TeamsGames");

        uploadAdditionalInfo();
        uploadSeasonLeagueBinders();
    }

    //region Users upload

    /**
     * Conducts all users uploading
     */
    public void uploadUsers() {
        uploadPlayers();
        uploadCoaches();
        uploadTeamManagers();
        uploadTeamOwners();
        //System.out.println("uploaded players, coaches team owners and teamManagers");
        uploadSystemManagers();
        //System.out.println("uploaded system Managers");
        uploadRFAs();
        //System.out.println("uploaded RFAs");
        uploadReferees();
        //System.out.println("uploaded referees");
        uploadFans();
        //System.out.println("uploaded fans");
        uploadPasswordsUsers();
    }

    /**
     * Upload all fans from DB.
     * Fans- a user with no other role- just a signed visitor.
     */
    private void uploadFans() {
        ResultSet resultSet = databaseManager.executeQuerySelect("SELECT * FROM fans");

//        ResultSet resultSet = databaseManager.executeQuerySelect("    " +
//                "SELECT * FROM fans, teamowner, coach, player, referee, rfa, systemmanager, teammanager\n" +
//                "    WHERE fans.Username = teamowner.username\n" +
//                "    AND fans.Username<> coach.Username\n" +
//                "    AND fans.Username<> player.Username\n" +
//                "    AND fans.Username<> referee.Username\n" +
//                "    AND fans.Username<> rfa.Username\n" +
//                "    AND fans.Username<> systemmanager.Username\n" +
//                "    AND fans.Username<> teammanager.Username");
        try {
            while (resultSet.next()) {
                String username = resultSet.getString("Username");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String accountStatus = resultSet.getString("AccountStatus");

                Fan fan = new Fan(username, firstName, lastName);

                EStatus status;
                if (accountStatus.equals("0")) status = EStatus.OFFLINE;
                else status = EStatus.ONLINE;
                fan.setStatus(status);

                // add to fans map
                allFans.put(username, fan);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Upload all Referees from DB.
     */
    private void uploadReferees() {
        ResultSet resultSet = databaseManager.executeQuerySelect("" +
                "SELECT * FROM fans, referee\n" +
                "WHERE fans.Username =referee.Username;");
        try {
            while (resultSet.next()) {
                String username = resultSet.getString("Username");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String accountStatus = resultSet.getString("AccountStatus");
                String training = resultSet.getString("Training");

                Referee referee = new Referee(username, firstName, lastName, EReferee.valueOf(training));

                EStatus status;
                if (accountStatus.equals("0")) status = EStatus.OFFLINE;
                else status = EStatus.ONLINE;
                referee.setStatus(status);

                // add to players map
                allReferees.put(username, referee);

                // add to fans map
                allFans.put(username, referee);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Upload all RFAs from DB.
     */
    private void uploadRFAs() {
        ResultSet resultSet = databaseManager.executeQuerySelect("" +
                "SELECT * FROM fans, rfa\n" +
                "WHERE fans.Username =rfa.Username;");
        try {
            while (resultSet.next()) {
                String username = resultSet.getString("Username");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String accountStatus = resultSet.getString("AccountStatus");

                RepresentativeFootballAssociation rfa = new RepresentativeFootballAssociation(username, firstName, lastName);

                EStatus status;
                if (accountStatus.equals("0")) status = EStatus.OFFLINE;
                else status = EStatus.ONLINE;
                rfa.setStatus(status);

                // add to players map
                allRFAs.put(username, rfa);

                // add to fans map
                allFans.put(username, rfa);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Upload all system managers from DB.
     */
    private void uploadSystemManagers() {
        ResultSet resultSet = databaseManager.executeQuerySelect("" +
                "SELECT * FROM fans, systemmanager\n" +
                "WHERE fans.Username =systemmanager.Username;");
        try {
            while (resultSet.next()) {
                String username = resultSet.getString("Username");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String accountStatus = resultSet.getString("AccountStatus");

                SystemManager manager = new SystemManager(username, firstName, lastName);

                EStatus status;
                if (accountStatus.equals("0")) status = EStatus.OFFLINE;
                else status = EStatus.ONLINE;
                manager.setStatus(status);

                // add to players map
                allSystemManagers.put(username, manager);

                // add to fans map
                allFans.put(username, manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Upload all team owners from DB.
     */
    private void uploadTeamOwners() {
        ResultSet resultSet = databaseManager.executeQuerySelect("" +
                "SELECT * FROM fans, teamowner\n" +
                "WHERE fans.Username =teamowner.Username;");
        try {
            while (resultSet.next()) {
                String username = resultSet.getString("Username");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String accountStatus = resultSet.getString("AccountStatus");

                TeamOwner teamOwner = new TeamOwner(username, firstName, lastName);

                EStatus status;
                if (accountStatus.equals("0")) status = EStatus.OFFLINE;
                else status = EStatus.ONLINE;
                teamOwner.setStatus(status);

                // add to players map
                allTeamOwners.put(username, teamOwner);

                // add to fans map
                allFans.put(username, teamOwner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Upload all team managers from DB.
     */
    private void uploadTeamManagers() {
        ResultSet resultSet = databaseManager.executeQuerySelect("" +
                "SELECT * FROM fans, teammanager\n" +
                "WHERE fans.Username =teammanager.Username;");
        try {
            while (resultSet.next()) {
                String username = resultSet.getString("Username");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String accountStatus = resultSet.getString("AccountStatus");

                TeamManager teamManager = new TeamManager(username, firstName, lastName);

                EStatus status;
                if (accountStatus.equals("0")) status = EStatus.OFFLINE;
                else status = EStatus.ONLINE;
                teamManager.setStatus(status);

                // add to players map
                allTeamManagers.put(username, teamManager);

                // add to fans map
                allFans.put(username, teamManager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Upload all coaches from DB.
     */
    private void uploadCoaches() {
        ResultSet resultSet = databaseManager.executeQuerySelect("" +
                "SELECT * FROM fans, coach\n" +
                "WHERE fans.Username =coach.Username;");
        try {
            while (resultSet.next()) {
                String username = resultSet.getString("Username");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String accountStatus = resultSet.getString("AccountStatus");
                String training = resultSet.getString("Training");
                String coachRole = resultSet.getString("CoachRole");

                Coach coach = new Coach(username, firstName, lastName, ETraining.valueOf(training), ECoachRole.valueOf(coachRole));

                EStatus status;
                if (accountStatus.equals("0")) status = EStatus.OFFLINE;
                else status = EStatus.ONLINE;
                coach.setStatus(status);

                // add to players map
                allCoaches.put(username, coach);

                // add to fans map
                allFans.put(username, coach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Upload all players from DB.
     */
    private void uploadPlayers() {
        ResultSet resultSet = databaseManager.executeQuerySelect("" +
                "SELECT * FROM fans, player\n" +
                "WHERE fans.Username =player.Username;");
        try {
            while (resultSet.next()) {
                String username = resultSet.getString("Username");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String accountStatus = resultSet.getString("AccountStatus");
                String birthDate = resultSet.getString("BirthDate");
                String playerRole = resultSet.getString("PlayerRole");

                Player player = new Player(username, firstName, lastName, Date.valueOf(birthDate), EPlayerRole.valueOf(playerRole));

                EStatus status;
                if (accountStatus.equals("0")) status = EStatus.OFFLINE;
                else status = EStatus.ONLINE;
                player.setStatus(status);

                // add to players map
                allPlayers.put(username, player);

                // add to fans map
                allFans.put(username, player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Upload all passwords from DB.
     * connects securely into the securitySystem's data.
     */
    private void uploadPasswordsUsers() {
        Map<String, String> userPass = new HashMap<>();
        SecuritySystem sec = system.getSecuritySystem();
        ResultSet resultSet = databaseManager.executeQuerySelect("SELECT * FROM userpasswords");
        try {
            while (resultSet.next()) {
                String username = resultSet.getString("Username");
                String password = resultSet.getString("Password_user");
                userPass.put(username, password);
            }

            sec.setUsersHashMap(userPass, "iseFab5");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //endregion

    /**
     * Upload all additional info objects from DB.
     */
    private void uploadAdditionalInfo() {
        ResultSet resultSet = databaseManager.executeQuerySelect("Select * From additionalinfo");
        try {
            while (resultSet.next()) {

                String teamName = resultSet.getString("Teams_name");
                String seasonYear = resultSet.getString("Seasons_Year");

                AdditionalInfo additionalInfo = new AdditionalInfo(allTeams.get(teamName), allSeasons.get(seasonYear));

                // attach owners

                ResultSet ownersSet = databaseManager.executeQuerySelect("" +
                        "SELECT * FROM additionalinfo_has_teamowner\n" +
                        "WHERE AdditionalInfo_Teams_name= \"" + teamName + "\" \n" +
                        "AND AdditionalInfo_Seasons_Year = \"" + seasonYear + "\";");
                ArrayList<String> owners = new ArrayList<>();
                while (ownersSet.next()) {
                    String ownerUsername = ownersSet.getString("TeamOwner_Username");
                    owners.add(ownerUsername);
                }
                // select nominating team owner:
                String nominatingOwner = owners.get(0);

                HashMap<String, ArrayList<String>> ownersMap = new HashMap<>();
                ownersMap.put(nominatingOwner, owners);
                additionalInfo.setOwners(ownersMap);

                // attach managers
                ResultSet managersSet = databaseManager.executeQuerySelect("" +
                        "SELECT * FROM teammanager_has_additionalinfo\n" +
                        "WHERE AdditionalInfo_Teams_name= \"" + teamName + "\" \n" +
                        "AND AdditionalInfo_Seasons_Year = \"" + seasonYear + "\";");
                ArrayList<String> managers = new ArrayList<>();
                while (managersSet.next()) {
                    String username = managersSet.getString("TeamManager_Username");
                    managers.add(username);
                }
                HashMap<String, ArrayList<String>> managersMap = new HashMap<>();
                managersMap.put(nominatingOwner, managers);
                additionalInfo.setManagers(managersMap);

                // attach coaches
                ResultSet coachesSet = databaseManager.executeQuerySelect("" +
                        "SELECT * FROM coach_has_additionalinfo\n" +
                        "WHERE AdditionalInfo_Teams_name= \"" + teamName + "\" \n" +
                        "AND AdditionalInfo_Seasons_Year = \"" + seasonYear + "\";");
                HashSet<String> coaches = new HashSet<>();
                while (coachesSet.next()) {
                    String username = coachesSet.getString("Coach_Username");
                    coaches.add(username);
                }
                additionalInfo.setCoaches(coaches);

                // attach players
                ResultSet playersSet = databaseManager.executeQuerySelect("" +
                        "SELECT * FROM player_has_additionalinfo\n" +
                        "WHERE AdditionalInfo_Teams_name= \"" + teamName + "\" \n" +
                        "AND AdditionalInfo_Seasons_Year = \"" + seasonYear + "\";");
                HashSet<String> players = new HashSet<>();
                while (playersSet.next()) {
                    String username = playersSet.getString("Player_Username");
                    players.add(username);
                }
                additionalInfo.setPlayers(players);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Upload all teams from DB.
     */
    private void uploadTeams() {
        ResultSet resultSet = databaseManager.executeQuerySelect("SELECT * FROM teams;");
        int tid=1;
        try {
            while (resultSet.next()) {

                String name = resultSet.getString("name");
                String fieldName = resultSet.getString("Fields_Name_Main");
                String status = resultSet.getString("teamStatus");
                String seasonYear = resultSet.getString("Seasons_has_Leagues_Seasons_Year");
                String leagueName = resultSet.getString("Seasons_has_Leagues_Leagues_Name");

                ResultSet teamOwnerRes = databaseManager.executeQuerySelect("" +
                        "SELECT * FROM additionalinfo_has_teamowner " +
                        "WHERE AdditionalInfo_Teams_name = \"" + name + "\" " +
                        "AND AdditionalInfo_Seasons_Year=\"" + seasonYear + "\";");
                TeamOwner owner = null;
                if (teamOwnerRes.next()) {
                    owner = allTeamOwners.get(teamOwnerRes.getString("TeamOwner_Username"));
                }

                Field field = allFields.get(fieldName);
                if (status.equals("0")) status = "INACTIVE";
                else status = "ACTIVE";
                Season season = allSeasons.get(seasonYear);
                League league= allLeagues.get(leagueName);

                Team team = new Team(tid++, name, season, field, null, owner);
                team.setIsActive(ETeamStatus.valueOf(status));
                team.setCurrentLeague(league);
                allTeams.put(team.getName(), team);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attach a team with it's home games and away games.
     */
    private void attachTeamsGames() {
        for (Team team : allTeams.values()) {

            // attach home games
            ResultSet homeGamesSet = databaseManager.executeQuerySelect("" +
                    "Select * From games " +
                    "where Teams_Host_Name=\"" + team.getName() + "\"");
            if (homeGamesSet == null) return;
            HashMap<Integer, Game> homeGames = new HashMap<>();
            try {

                while (homeGamesSet.next()) {
                    int GID= homeGamesSet.getInt("idGames");
                    Game game = allGames.get(GID);
                    homeGames.put(game.getGID(), game);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            team.setHomeGames(homeGames);

            // attach away games
            ResultSet awayGamesSet = databaseManager.executeQuerySelect("" +
                    "Select * From games " +
                    "where Teams_Guest_Name=\"" + team.getName() + "\"");
            if (awayGamesSet == null) return;

            HashMap<Integer, Game> awayGames = new HashMap<>();
            try {
                while (awayGamesSet.next()) {
                    int GID= awayGamesSet.getInt("idGames");
                    Game game = allGames.get(GID);
                    awayGames.put(game.getGID(), game);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            team.setAwayGames(awayGames);

        }
    }

    /**
     * Upload all seasonLeagueBinder objects from DB.
     */
    private void uploadSeasonLeagueBinders() {
        Set<SeasonLeagueBinder> binders = new HashSet<>();
        ResultSet resultSet = databaseManager.executeQuerySelect("Select * From seasons_has_leagues");
        try {
            while (resultSet.next()) {

                String seasonYear = resultSet.getString("Seasons_Year");
                String leagueName = resultSet.getString("Leagues_Name");
                String scoreTablePolicy = resultSet.getString("ScorePolicy");
                String gameSchedulePolicy = resultSet.getString("SchedulePolicy");

                ScoreTablePolicy tablePolicy = new RegularScorePolicy();
                if (scoreTablePolicy.equals("2")) tablePolicy = new ScoreTablePolicy2();

                GamesAssigningPolicy gamesAssigningPolicy = new OneRoundGamesAssigningPolicy();
                if (gameSchedulePolicy.equals("1")) gamesAssigningPolicy = new SimpleGamesAssigningPolicy();

                Season season = allSeasons.get(seasonYear);
                League league = allLeagues.get(leagueName);

                // upload new binder
                SeasonLeagueBinder binder = new SeasonLeagueBinder(season, league);

                // attach teams
                ResultSet teamsSet = databaseManager.executeQuerySelect(
                        "SELECT * from teams " +
                                "WHERE Seasons_has_Leagues_Leagues_Name = \"" + leagueName + "\" " +
                                "AND Seasons_has_Leagues_Seasons_Year = " + seasonYear);

                HashMap<String, Team> teams = new HashMap<>();
                while (teamsSet.next()) {
                    String teamName = teamsSet.getString("name");
                    Team team = allTeams.get(teamName);
                    teams.put(teamName, team);
                }
                binder.addTeamsToLeague(teams);

                // attach games
                ResultSet gamesSet = databaseManager.executeQuerySelect(
                        "SELECT * from games " +
                                "WHERE Seasons_has_Leagues_Leagues_Name = \"" + leagueName + "\" " +
                                "AND Seasons_has_Leagues_Seasons_Year = " + seasonYear);
                HashMap<Integer, Game> games = new HashMap<>();
                while (gamesSet.next()) {
                    int gameID = gamesSet.getInt("idGames");
                    Game game = allGames.get(gameID);
                    games.put(game.getGID(), game);
                }
                binder.addGamesToLeague(games);

                // attach policies
                binder.setScoreTablePolicy(tablePolicy);
                binder.setAssigningPolicy(gamesAssigningPolicy);

                // attach season & league
                season.addLeagueBinder(leagueName, binder);
                league.addSeasonLeagueBinder(seasonYear, binder);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Upload all season objects from DB.
     */
    private void uploadSeasons() {
        ResultSet resultSet = databaseManager.executeQuerySelect("Select * From seasons");
        try {
            while (resultSet.next()) {
                Season season = new Season(resultSet.getString("Year"));
                allSeasons.put(season.getYear(), season);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Upload all league objects from DB.
     */
    private void uploadLeagues() {

        ResultSet resultSet = databaseManager.executeQuerySelect("Select * From leagues");
        try {
            while (resultSet.next()) {
                // Load the league
                League league = new League(resultSet.getString("Name"));
                allLeagues.put(league.getLeagueName(), league);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Upload all field objects from DB.
     */
    private void uploadFields() {
        ResultSet resultSet = databaseManager.executeQuerySelect("Select * From fields");
        try {
            while (resultSet.next()) {
                Field field = new Field(resultSet.getString("Name"),
                        resultSet.getString("City"),
                        resultSet.getInt("Capacity"));
                allFields.put(field.getName(), field);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Upload all game objects from DB.
     */
    private void uploadGames() {
        ResultSet resultSet = databaseManager.executeQuerySelect("Select * From games");
        try {
            while (resultSet.next()) {
                int gid = resultSet.getInt("idGames");
                String datestr = resultSet.getString("DateTime");
                Date date= Date.valueOf(datestr.split(" ")[0]);
                Time time= Time.valueOf(datestr.split(" ")[1]);
                SimpleDateFormat dateFormat= new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
                java.util.Date cDate= dateFormat.parse(datestr);
                int goalHost = resultSet.getInt("GoalHost");
                int goalGuest = resultSet.getInt("GoalGuest");
                String fields_name = resultSet.getString("Fields_Name");
                String host_name = resultSet.getString("Teams_Host_Name");
                String guest_name = resultSet.getString("Teams_Guest_Name");
                String seasons_year = resultSet.getString("Seasons_has_Leagues_Seasons_Year");
                String leagues_name = resultSet.getString("Seasons_has_Leagues_Leagues_Name");

                Field field = allFields.get(fields_name);
                Team host = allTeams.get(host_name);
                Team guest = allTeams.get(guest_name);
                Season season = allSeasons.get(seasons_year);
                League league = allLeagues.get(leagues_name);

                // get referees:
                Referee main = null;
                Referee side1 = null;
                Referee side2 = null;
                ResultSet refereesSet = databaseManager.executeQuerySelect(
                        "SELECT * FROM games_has_referee WHERE Games_idGames= "+gid );
                while (refereesSet.next()) {
                    String username = refereesSet.getString("Referee_Username");
                    String role = refereesSet.getString("Game_Role");
                    switch (role) {
                        case "main":
                            main = allReferees.get(username);
                            break;
                        case "side1":
                            side1 = allReferees.get(username);
                            break;
                        case "side2":
                            side2 = allReferees.get(username);
                            break;
                    }
                }

                Game game = new Game(cDate,time, field, host, guest, main, side1, side2, season, league);
                game.setScore(goalHost, goalGuest);

                // TODO: game.setGID - fix on class game

                allGames.put(game.getGID(), game);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
