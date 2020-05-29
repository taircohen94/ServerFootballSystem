package DAL;

import AssociationAssets.*;
import PoliciesAndAlgorithms.RegularScorePolicy;
import PoliciesAndAlgorithms.SimpleGamesAssigningPolicy;
import Security.AESEncryption;
import Security.SecuritySystem;
import Users.*;
import System.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataSave {

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


    public DataSave(DatabaseManager databaseManager) {
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

    public void saveAllData() {
        saveUsers();
        //System.out.println("saved users");
        saveFields();
        //System.out.println("saved fields");
        saveLeagues();
        //System.out.println("saved Leagues");
        saveSeasons();
        //System.out.println("saved seasons");
        saveTeams();
        //System.out.println("saved teams");
        saveGames();
        //System.out.println("saved games");
        saveAdditionalInfo();
        //System.out.println("saved additionalInfo");
        saveSeasonLeagueBinders();
    }

    private void saveUsers() {
        saveFans();
        savePlayers();
        saveCoaches();
        saveTeamManagers();
        saveTeamOwners();
        saveSystemManagers();
        saveRFAs();
        saveReferees();
        savePasswordsUsers();
    }

    private void saveSeasonLeagueBinders() {
        PreparedStatement ps=null;
        for (Season s : allSeasons.values()) {
            for (SeasonLeagueBinder binder : s.getLeagueBinders().values()) {
                int scorePolicy = (binder.getScoreTablePolicy() instanceof RegularScorePolicy) ? 1 : 2;
                int gamePolicy = (binder.getAssigningPolicy() instanceof SimpleGamesAssigningPolicy) ? 1 : 2;
                String query=
                        "INSERT INTO \n" +
                        "\tseasons_has_leagues(Seasons_Year, Leagues_Name, ScorePolicy, SchedulePolicy)\n" +
                        "VALUES(?,?,?,?) " +
                        "ON DUPLICATE KEY UPDATE " +
                        "Seasons_Year=?," +
                        "Leagues_Name=?," +
                        "ScorePolicy=?," +
                        "SchedulePolicy=?;";
                try {
                    ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                    ps.setInt(1, Integer.parseInt(s.getYear()));
                    ps.setString(2, binder.getLeague().getLeagueName());
                    ps.setInt(3, scorePolicy);
                    ps.setInt(4, gamePolicy);

                    ps.setInt(5, Integer.parseInt(s.getYear()));
                    ps.setString(6, binder.getLeague().getLeagueName());
                    ps.setInt(7, scorePolicy);
                    ps.setInt(8, gamePolicy);

                    //System.out.println(ps.toString());
                    ps.executeUpdate();
                    databaseManager.conn.commit();
                }
                catch(SQLException e){
                try {
                    databaseManager.conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            } finally{
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }

        }

    }

}

    public void saveAdditionalInfo() {
        PreparedStatement ps = null;
        for (Season season : allSeasons.values()) {
            for (AdditionalInfo additionalInfo : season.getTeamAdditionalInfo().values()) {
                // additionalinfo table
                String query =
                        "INSERT INTO \n" +
                                "\tadditionalinfo(Teams_name,Seasons_Year)\n" +
                                "VALUES(?,?) " +
                                "ON DUPLICATE KEY UPDATE " +
                                "Teams_name=?," +
                                "Seasons_Year?;";
                try {
                    ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                    ps.setString(1, additionalInfo.getTeam().getName());
                    ps.setInt(2, Integer.parseInt(additionalInfo.getSeason().getYear()));

                    ps.setString(3, additionalInfo.getTeam().getName());
                    ps.setInt(4, Integer.parseInt(additionalInfo.getSeason().getYear()));
                    //System.out.println(ps.toString());
                    ps.executeUpdate();
                    databaseManager.conn.commit();


                    // additionalinfo_has_teamowner table
                    Iterator iter = additionalInfo.getOwners().values().iterator();
                    while (iter.hasNext()) {
                        String owner = (String) iter.next();
                        query =
                                "INSERT  INTO \n" +
                                        "\tadditionalinfo_has_teamowner(AdditionalInfo_Teams_name,AdditionalInfo_Seasons_Year,TeamOwner_Username)\n" +
                                        "VALUES(?,?,?) " +
                                        "ON DUPLICATE KEY UPDATE " +
                                        "AdditionalInfo_Teams_name=?," +
                                        "AdditionalInfo_Seasons_Year=?," +
                                        "TeamOwner_Username=?;";
                        ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                        ps.setString(1, additionalInfo.getTeam().getName());
                        ps.setInt(2, Integer.parseInt(additionalInfo.getSeason().getYear()));
                        ps.setString(3, owner);

                        ps.setString(4, additionalInfo.getTeam().getName());
                        ps.setInt(5, Integer.parseInt(additionalInfo.getSeason().getYear()));
                        ps.setString(6, owner);
                        //System.out.println(ps.toString());
                        ps.executeUpdate();
                        databaseManager.conn.commit();
                    }

                    // coach_has_additionalinfo table
                    for (String coach : additionalInfo.getCoaches()) {
                        query =
                                "INSERT  INTO \n" +
                                        "\tcoach_has_additionalinfo(Coach_Username,AdditionalInfo_Teams_name,AdditionalInfo_Seasons_Year)\n" +
                                        "VALUES(?,?,?) " +
                                        "ON DUPLICATE KEY UPDATE " +
                                        "Coach_Username=?," +
                                        "AdditionalInfo_Teams_name=?," +
                                        "AdditionalInfo_Seasons_Year=?;";
                        ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                        ps.setString(1, coach);
                        ps.setString(2, additionalInfo.getTeam().getName());
                        ps.setInt(3, Integer.parseInt(additionalInfo.getSeason().getYear()));

                        ps.setString(4, coach);
                        ps.setString(5, additionalInfo.getTeam().getName());
                        ps.setInt(6, Integer.parseInt(additionalInfo.getSeason().getYear()));

                        //System.out.println(ps.toString());
                        ps.executeUpdate();
                        databaseManager.conn.commit();
                    }

                    // teammanager_has_additionalinfo table
                    iter = additionalInfo.getManagers().values().iterator();
                    while (iter.hasNext()) {
                        String manager = (String) iter.next();
                        query =
                                "INSERT  INTO \n" +
                                        "\tteammanager_has_additionalinfo(TeamManager_Username,AdditionalInfo_Teams_name,AdditionalInfo_Seasons_Year)\n" +
                                        "VALUES(?,?,?) " +
                                        "ON DUPLICATE KEY UPDATE " +
                                        "TeamManager_Username=?," +
                                        "AdditionalInfo_Teams_name=?," +
                                        "AdditionalInfo_Seasons_Year=?;";
                        ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                        ps.setString(1, manager);
                        ps.setString(2, additionalInfo.getTeam().getName());
                        ps.setInt(3, Integer.parseInt(additionalInfo.getSeason().getYear()));

                        ps.setString(4, manager);
                        ps.setString(5, additionalInfo.getTeam().getName());
                        ps.setInt(6, Integer.parseInt(additionalInfo.getSeason().getYear()));

                        //System.out.println(ps.toString());
                        ps.executeUpdate();
                        databaseManager.conn.commit();
                    }

                    // player_has_additionalinfo table
                    for (String player : additionalInfo.getPlayers()) {
                        query =
                                "INSERT  INTO \n" +
                                        "\tplayer_has_additionalinfo(Player_Username,AdditionalInfo_Teams_name,AdditionalInfo_Seasons_Year)\n" +
                                        "VALUES(?,?,?) " +
                                        "ON DUPLICATE KEY UPDATE " +
                                        "Player_Username=?," +
                                        "AdditionalInfo_Teams_name=?," +
                                        "AdditionalInfo_Seasons_Year=?;";
                        ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                        ps.setString(1, player);
                        ps.setString(2, additionalInfo.getTeam().getName());
                        ps.setInt(3, Integer.parseInt(additionalInfo.getSeason().getYear()));

                        ps.setString(4, player);
                        ps.setString(5, additionalInfo.getTeam().getName());
                        ps.setInt(6, Integer.parseInt(additionalInfo.getSeason().getYear()));

                        //System.out.println(ps.toString());
                        ps.executeUpdate();
                        databaseManager.conn.commit();
                    }

                } catch (SQLException e) {
                    try {
                        databaseManager.conn.rollback();
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    }
                    e.printStackTrace();
                } finally {
                    try {
                        if (ps != null) {
                            ps.close();
                        }
                    } catch (SQLException e3) {
                        e3.printStackTrace();
                    }
                }
            }
        }
    }


    public void saveGames() {
        for (Game game : allGames.values()) {

            // change date formatting
            java.util.Date dt = game.getDate();
            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(dt);

            // save game:
            PreparedStatement ps = null;
            String query = "INSERT INTO games(idGames, DateTime, GoalHost, GoalGuest, Fields_Name," +
                    "Teams_Host_Name, Teams_Seasons_Year_Host," +
                    "    Teams_Guest_Name, Teams_Seasons_Year_Guest," +
                    "    Seasons_has_Leagues_Seasons_Year, Seasons_has_Leagues_Leagues_Name)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "idGames=?," +
                    "DateTime=?," +
                    "GoalHost=?," +
                    "GoalGuest=?," +
                    "Fields_Name=?," +
                    "Teams_Host_Name=?," +
                    "Teams_Seasons_Year_Host=?," +
                    "Teams_Guest_Name=?," +
                    "Teams_Seasons_Year_Guest=?," +
                    "Seasons_has_Leagues_Seasons_Year=?," +
                    "Seasons_has_Leagues_Leagues_Name=?;"; //query
            try {
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setInt(1, game.getGID());
                ps.setString(2, dateStr);
                ps.setInt(3, game.getScore().getGoalsHost());
                ps.setInt(4, game.getScore().getGoalsGuest());
                ps.setString(5, game.getField().getName());
                ps.setString(6, game.getHost().getName());
                ps.setInt(7, Integer.parseInt(game.getSeason().getYear()));
                ps.setString(8, game.getGuest().getName());
                ps.setInt(9, Integer.parseInt(game.getSeason().getYear()));
                ps.setInt(10, Integer.parseInt(game.getSeason().getYear()));
                ps.setString(11, game.getLeague().getLeagueName());

                ps.setInt(12, game.getGID());
                ps.setString(13, dateStr);
                ps.setInt(14, game.getScore().getGoalsHost());
                ps.setInt(15, game.getScore().getGoalsGuest());
                ps.setString(16, game.getField().getName());
                ps.setString(17, game.getHost().getName());
                ps.setInt(18, Integer.parseInt(game.getSeason().getYear()));
                ps.setString(19, game.getGuest().getName());
                ps.setInt(20, Integer.parseInt(game.getSeason().getYear()));
                ps.setInt(21, Integer.parseInt(game.getSeason().getYear()));
                ps.setString(22, game.getLeague().getLeagueName());
                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();
                ps = null;


                // games_has_referee table:

                // save main referee
                //region main referee query
                query = "INSERT  INTO \n" +
                        "\tgames_has_referee(Games_idGames, Referee_Username, Game_Role)\n" +
                        "\tVALUES(?,?,?) " +
                        "ON DUPLICATE KEY UPDATE " +
                        "Games_idGames=?, " +
                        "Referee_Username=?, " +
                        "Game_Role=?; ";
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setInt(1, game.getGID());
                ps.setString(2, game.getMain().getUserName());
                ps.setString(3, "main");

                ps.setInt(4, game.getGID());
                ps.setString(5, game.getMain().getUserName());
                ps.setString(6, "main");
                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();
                //endregion

                // save side1 referee
                //region side 1 query
                query = "INSERT  INTO \n" +
                        "\tgames_has_referee(Games_idGames, Referee_Username, Game_Role)\n" +
                        "\tVALUES(?,?,?) " +
                        "ON DUPLICATE KEY UPDATE  " +
                        "Games_idGames=?," +
                        "Referee_Username=?," +
                        "Game_Role=?;";
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setInt(1, game.getGID());
                ps.setString(2, game.getSide1().getUserName());
                ps.setString(3, "side1");

                ps.setInt(4, game.getGID());
                ps.setString(5, game.getSide1().getUserName());
                ps.setString(6, "side1");
                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();
                //endregion

                // save side2 referee
                //region side2 query
                query = "INSERT  INTO \n" +
                        "\tgames_has_referee(Games_idGames, Referee_Username, Game_Role)\n" +
                        "\tVALUES(?,?,?) " +
                        "ON DUPLICATE KEY UPDATE  " +
                        "Games_idGames=?," +
                        "Referee_Username=?," +
                        "Game_Role=?;";
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setInt(1, game.getGID());
                ps.setString(2, game.getSide2().getUserName());
                ps.setString(3, "side2");

                ps.setInt(4, game.getGID());
                ps.setString(5, game.getSide2().getUserName());
                ps.setString(6, "side2");
                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();
                //endregion

            } catch (SQLException e) {
                try {
                    databaseManager.conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }


    public void saveTeams() {
        for (Team team : allTeams.values()) {
            PreparedStatement ps = null;
            int active = 0;
            if (team.getIsActive() == ETeamStatus.ACTIVE) active = 1;
            String query = "INSERT INTO \n" +
                    "\tteams(name, Fields_Name_Main, teamStatus, Seasons_has_Leagues_Seasons_Year, Seasons_has_Leagues_Leagues_Name) " +
                    "VALUES(?,?,?,?,?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "name=?," +
                    "Fields_Name_Main=?," +
                    "teamStatus=?," +
                    "Seasons_has_Leagues_Seasons_Year=?," +
                    "Seasons_has_Leagues_Leagues_Name=?;";
            try {
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setString(1, team.getName());
                ps.setString(2, team.getMainField().getName());
                ps.setInt(3, active);
                ps.setInt(4, Integer.parseInt(team.getCurrentSeason().getYear()));
                ps.setString(5, team.getCurrentLeague().getLeagueName());

                ps.setString(6, team.getName());
                ps.setString(7, team.getMainField().getName());
                ps.setInt(8, active);
                ps.setInt(9, Integer.parseInt(team.getCurrentSeason().getYear()));
                ps.setString(10, team.getCurrentLeague().getLeagueName());
                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();

                for (Field f : team.getFields().values()) {
                    query =
                            "INSERT  INTO \n" +
                                    "\tteams_has_fields(Team_Name, Fields_Name)\n" +
                                    "VALUES (?,?) " +
                                    "ON DUPLICATE KEY UPDATE " +
                                    "Team_Name=?," +
                                    "Fields_Name=?;";

                    ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                    ps.setString(1, team.getName());
                    ps.setString(2, f.getName());

                    ps.setString(3, team.getName());
                    ps.setString(4, f.getName());
                    //System.out.println(ps.toString());
                    ps.executeUpdate();
                    databaseManager.conn.commit();
                }
            } catch (SQLException e) {
                try {
                    databaseManager.conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }


    public void saveSeasons() {
        int id = 1;
        for (Season season : allSeasons.values()) {
            String query =
                    "INSERT  INTO \n" +
                            "\tseasons(ID, Year)\n" +
                            "VALUES(?,?) " +
                            "ON DUPLICATE KEY UPDATE " +
                            "ID=?," +
                            "Year=?;";

            PreparedStatement ps = null;
            try {
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setInt(1, id);
                ps.setString(2, season.getYear());

                ps.setInt(3, id);
                ps.setString(4, season.getYear());
                id += 1;
                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();
            } catch (SQLException e) {
                try {
                    databaseManager.conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public void saveLeagues() {
        for (League league : allLeagues.values()) {
            String query =
                    "INSERT INTO \n" +
                            "\tleagues(Name)\n" +
                            "VALUES(?) " +
                            "ON DUPLICATE KEY UPDATE " +
                            "Name=?;";
            PreparedStatement ps = null;
            try {
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setString(1, league.getLeagueName());

                ps.setString(2, league.getLeagueName());
                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();
            } catch (SQLException e) {
                try {
                    databaseManager.conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public void saveFields() {
        int id = 1;
        for (Field field : allFields.values()) {
            String query =
                    "INSERT  INTO \n" +
                            "\tfields(ID, Name,City, Capacity)\n" +
                            "VALUES(?,?,?,?) " +
                            "ON DUPLICATE KEY UPDATE " +
                            "ID=?," +
                            "Name=?," +
                            "City=?," +
                            "Capacity=?;";
            PreparedStatement ps = null;
            try {
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setInt(1, id);
                ps.setString(2, field.getName());
                ps.setString(3, field.getCity());
                ps.setInt(4, field.getCapacity());

                ps.setInt(5, id);
                ps.setString(6, field.getName());
                ps.setString(7, field.getCity());
                ps.setInt(8, field.getCapacity());
                id += 1;

                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();
            } catch (SQLException e) {
                try {
                    databaseManager.conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }


        }
    }


    public void savePasswordsUsers() {
        SecuritySystem sec = FootballSystem.getInstance().getSecuritySystem();
        for (Map.Entry<String, String> entry : sec.getUsersHashMap("iseFab5").entrySet()) {
            String query =
                    "INSERT  INTO \n" +
                            "\tuserpasswords(Username,Password_user)\n" +
                            "VALUES(?,?) " +
                            "ON DUPLICATE KEY UPDATE " +
                            "Username=?," +
                            "Password_user=?;";

            PreparedStatement ps = null;

            try {
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setString(1, entry.getKey());
                ps.setString(2, entry.getValue());

                ps.setString(3, entry.getKey());
                ps.setString(4, entry.getValue());

                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();
            } catch (SQLException e) {
                try {
                    databaseManager.conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }


        }
    }

    public void saveFans() {
        for (Fan fan : allFans.values()) {
            int offlineStatus = 0;
            String query =
                    "INSERT INTO   \n" +
                            "\tfans(Username,FirstName, LastName, AccountStatus)\n" +
                            "VALUES(?,?,?,?)" +
                            "ON DUPLICATE KEY UPDATE \n" +
                            "Username = ?,\n" +
                            "FirstName= ?,\n" +
                            "LastName= ?,\n" +
                            "AccountStatus=?;";

            PreparedStatement ps = null;
            try {
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setString(1, fan.getUserName());
                ps.setString(2, fan.getfName());
                ps.setString(3, fan.getlName());
                ps.setInt(4, offlineStatus);

                ps.setString(5, fan.getUserName());
                ps.setString(6, fan.getfName());
                ps.setString(7, fan.getlName());
                ps.setInt(8, offlineStatus);
                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();
            } catch (SQLException e) {
                try {
                    databaseManager.conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }


        }
    }

    public void saveReferees() {
        for (Referee referee : allReferees.values()) {
            String query =
                    "INSERT INTO  \n" +
                            "\treferee(Username,Training)\n" +
                            "VALUES(?,?)" +
                            "ON DUPLICATE KEY UPDATE " +
                            "Username=?," +
                            "Training=?;";

            PreparedStatement ps = null;
            try {
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setString(1, referee.getUserName());
                ps.setString(2, referee.getTraining().toString());
                ps.setString(3, referee.getUserName());
                ps.setString(4, referee.getTraining().toString());
                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();
            } catch (SQLException e) {
                try {
                    databaseManager.conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }

        }
    }

    public void saveRFAs() {
        for (RepresentativeFootballAssociation rfa : allRFAs.values()) {
            String query =
                    "INSERT INTO\n" +
                            "\trfa(Username)\n" +
                            "VALUES(?)" +
                            "ON DUPLICATE KEY UPDATE " +
                            "Username=?;";

            PreparedStatement ps = null;
            try {
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setString(1, rfa.getUserName());
                ps.setString(2, rfa.getUserName());
                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();
            } catch (SQLException e) {
                try {
                    databaseManager.conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public void saveSystemManagers() {
        for (SystemManager systemManager : allSystemManagers.values()) {
            String query =
                    "INSERT INTO\n" +
                            "\tsystemmanager(Username)\n" +
                            "VALUES(?) " +
                            "ON DUPLICATE KEY UPDATE " +
                            "Username=?;";

            PreparedStatement ps = null;
            try {
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setString(1, systemManager.getUserName());
                ps.setString(2, systemManager.getUserName());
                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();
            } catch (SQLException e) {
                try {
                    databaseManager.conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }


    public void saveTeamOwners() {
        for (TeamOwner owner : allTeamOwners.values()) {
            String query =
                    "INSERT INTO\n" +
                            "\tteamowner(Username)\n" +
                            "VALUES(?) " +
                            "ON DUPLICATE KEY UPDATE " +
                            "Username=?;";

            PreparedStatement ps = null;
            try {
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setString(1, owner.getUserName());
                ps.setString(2, owner.getUserName());
                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();
            } catch (SQLException e) {
                try {
                    databaseManager.conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }


        }
    }

    public void saveTeamManagers() {
        for (TeamManager manager : allTeamManagers.values()) {
            String query =
                    "INSERT INTO\n" +
                            "\tteammanager(Username)\n" +
                            "VALUES(?) " +
                            "ON DUPLICATE KEY UPDATE " +
                            "Username=?;";

            PreparedStatement ps = null;
            try {
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setString(1, manager.getUserName());
                ps.setString(2, manager.getUserName());
                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();
            } catch (SQLException e) {
                try {
                    databaseManager.conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public void saveCoaches() {
        for (Coach c : allCoaches.values()) {
            String query =
                    "INSERT INTO\n" +
                            "\tcoach(Username, Training, CoachRole)\n" +
                            "VALUES(?,?,?) " +
                            "ON DUPLICATE KEY UPDATE " +
                            "Username=?," +
                            "Training=?," +
                            "CoachRole=?;";

            PreparedStatement ps = null;
            try {
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setString(1, c.getUserName());
                ps.setString(2, c.getTraining().toString());
                ps.setString(3, c.getRole().toString());

                ps.setString(4, c.getUserName());
                ps.setString(5, c.getTraining().toString());
                ps.setString(6, c.getRole().toString());
                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();
            } catch (SQLException e) {
                try {
                    databaseManager.conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public void savePlayers() {
        for (Player p : allPlayers.values()) {

            // change date formatting
            java.util.Date dt = p.getbDate();
            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(dt);

            String query =
                    "INSERT INTO\n" +
                            "\tplayer(Username, BirthDate, PlayerRole)\n" +
                            "VALUES(?,?,?) " +
                            "ON DUPLICATE KEY UPDATE " +
                            "Username=?," +
                            "BirthDate=?," +
                            "PlayerRole=?;";

            PreparedStatement ps = null;
            try {
                ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
                ps.setString(1, p.getUserName());
                ps.setString(2, dateStr);
                ps.setString(3, p.getRole().toString());

                ps.setString(4, p.getUserName());
                ps.setString(5, dateStr);
                ps.setString(6, p.getRole().toString());
                //System.out.println(ps.toString());
                ps.executeUpdate();
                databaseManager.conn.commit();
            } catch (SQLException e) {
                try {
                    databaseManager.conn.rollback();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }


        }
    }

}



