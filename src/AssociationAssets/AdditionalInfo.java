package AssociationAssets;

import System.FootballSystem;
import Users.Coach;
import Users.Player;
import Users.TeamManager;
import Users.TeamOwner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import System.*;
/**
 * This class is association class for Season and Team.
 * Aouthors: Amit Shakarchy, Alon Gutman, Tair Cohen
 */
public class AdditionalInfo {

    //region Fields
    Team team;
    Season season;
    HashMap<String/*username of the teamOwner who nominate*/
            , ArrayList<String> /*user names list of the team Owners*/> owners;
    HashSet<String> coaches;
    HashMap<String/*username of the teamOwner who nominate*/
            , ArrayList<String> /*user names list of the team mangers*/> managers;
    HashSet<String> players;
    HashSet<String> teamManagersHashSet;
    HashSet<String> teamOwnersHashSet;
    //endregion


    /**
     * constructor of AdditionalInfo class
     *
     * @param team   - the team in the season the info is about
     * @param season - the season that the info is about
     */
    public AdditionalInfo(Team team, Season season) {
        this.team = team;
        this.season = season;
        this.teamManagersHashSet = new HashSet<>();
        this.teamOwnersHashSet = new HashSet<>();
        this.coaches = new HashSet<>();
        this.managers = new HashMap<>();
        this.owners = new HashMap<>();
        this.players = new HashSet<>();
    }

    //region Getters & Setters
    public Team getTeam() {

        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
        team.setCurrentSeason(season);
    }

    public void setOwners(HashMap<String, ArrayList<String>> owners) {
        this.owners = owners;
    }

    public void setCoaches(HashSet<String> coaches) {
        this.coaches = coaches;
    }

    public void setManagers(HashMap<String, ArrayList<String>> managers) {
        this.managers = managers;
    }

    public void setPlayers(HashSet<String> players) {
        this.players = players;
    }

    public void setTeamManagersHashSet(HashSet<String> teamManagersHashSet) {
        this.teamManagersHashSet = teamManagersHashSet;
    }

    public void setTeamOwnersHashSet(HashSet<String> teamOwnersHashSet) {
        this.teamOwnersHashSet = teamOwnersHashSet;
    }

    public HashMap<String, ArrayList<String>> getOwners() {
        return owners;
    }

    public HashSet<String> getCoaches() {
        return coaches;
    }

    public HashMap<String, ArrayList<String>> getManagers() {
        return managers;
    }

    public HashSet<String> getPlayers() {
        return players;
    }

    public HashSet<String> getTeamManagersHashSet() {
        return teamManagersHashSet;
    }

    public HashSet<String> getTeamOwnersHashSet() {
        return teamOwnersHashSet;
    }

    //endregion

    /**
     * Adds a player to the team, if he is not exist.
     *
     * @param player , the user name of the player
     * @return true if the player was adding to the team successfully.
     */
    public boolean addPlayer(String player) {
        LocalDate dateLog = LocalDate.now();
        LocalTime now = LocalTime.now();
        if(player == null || player.isEmpty()){
            Logger.getInstance().addErrorToLogger(dateLog + " " + now + ":Adding Player "+player+ "was failed to the team: "+team.getName()+".");
            return false;
        }
        if (!players.contains(player)) {
            players.add(player);
            // Write to the log
            Logger.getInstance().addActionToLogger(dateLog + " " + now + ": Player "+player+ "was added to the team: "+team.getName()+".");
            return true;
        }
        Logger.getInstance().addErrorToLogger(dateLog + " " + now + ":Adding Player "+player+ "was failed to the team: "+team.getName()+".");
        return false;
    }

    /**
     * Adds a coach to the team, if he is not exist.
     *
     * @param coach , the user name of the coach
     * @return true if the coach was adding to the team successfully.
     */
    public boolean addCoach(String coach) {
        LocalDate dateLog = LocalDate.now();
        LocalTime now = LocalTime.now();
        if(coach == null || coach.isEmpty()){
            Logger.getInstance().addErrorToLogger(dateLog + " " + now + ": Coach adding was failed: "+coach+ "was failed to add to the team: "+team.getName()+".");
            return false;
        }
        if (!coaches.contains(coach)) {
            coaches.add(coach);
            // Write to the log

            Logger.getInstance().addActionToLogger(dateLog + " " + now + ": Coach "+coach+ "was added to the team: "+team.getName()+".");
            return true;
        }
        Logger.getInstance().addErrorToLogger(dateLog + " " + now + ": Coach adding was failed: "+coach+ "was failed to add to the team: "+team.getName()+".");
        return false;
    }

    /**
     * Adds a TeamManager to the team, if he is not exist.
     *
     * @param teamManager           , the user name of teamManager.
     * @param teamOwnerWhoNominate, the user name of the team owner who nominated this team
     *                              manager.
     * @return true if the team manger was adding to the team successfully.
     */
    public boolean addManager(String teamManager, String teamOwnerWhoNominate) {

        if( teamOwnerWhoNominate == null || teamManager == null ||
                teamOwnerWhoNominate.isEmpty() || teamManager.isEmpty()){
            Logger.getInstance().addErrorToLogger("TeamManager adding was failed: "+teamManager+ "was failed to add to the team: "+team.getName()+".");
            return false;
        }
        ArrayList temp = new ArrayList();
        if (managers.containsKey(teamOwnerWhoNominate)) {
            temp = managers.get(teamOwnerWhoNominate);
            // this team manager already exists in this team & season
            if (temp.contains(teamManager)) {
                Logger.getInstance().addErrorToLogger("TeamManager adding was failed: "+teamManager+ "was failed to add to the team: "+team.getName()+".");
                return false;
            }
            temp.add(teamManager);
            managers.put(teamOwnerWhoNominate, temp);
            this.teamManagersHashSet.add(teamManager);
        } else {
            temp.add(teamManager);
            managers.put(teamOwnerWhoNominate, temp);
            this.teamManagersHashSet.add(teamManager);
        }
        // Write to the log

        Logger.getInstance().addActionToLogger(" Manager "+teamManager+ "was added to the team: "+team.getName()+".");
        return true;
    }

    /**
     * Adds a TeamOwner to the team, if he is not exist.
     *
     * @param teamOwner,            the user name of teamOwner.
     * @param teamOwnerWhoNominate, the user name of the teamOwner who nominate this team owner.
     * @return true if the team owner was adding to the team successfully.
     */
    public boolean addTeamOwner(String teamOwner, String teamOwnerWhoNominate) {
        ArrayList temp = new ArrayList();
        if( teamOwnerWhoNominate == null || teamOwner == null ||
        teamOwnerWhoNominate.isEmpty() || teamOwner.isEmpty()){
            Logger.getInstance().addErrorToLogger("TeamOwner adding was failed: "+teamOwner+ "was failed to add to the team: "+team.getName()+".");
            return false;
        }
        if (owners.containsKey(teamOwnerWhoNominate)) {
            temp = owners.get(teamOwnerWhoNominate);
            // this team owner already exists in this team & season
            if (temp.contains(teamOwner)) {
                Logger.getInstance().addErrorToLogger("TeamOwner adding was failed: "+teamOwner+ "was failed to add to the team: "+team.getName()+".");
                return false;
            }
            temp.add(teamOwner);
            owners.put(teamOwnerWhoNominate, temp);
            this.teamOwnersHashSet.add(teamOwner);
        } else {
            temp.add(teamOwner);
            owners.put(teamOwnerWhoNominate, temp);
            this.teamOwnersHashSet.add(teamOwner);
        }
        // Write to the log
        Logger.getInstance().addActionToLogger("Team Owner  "+teamOwner+ "was added to the team: "+team.getName()+".");
        return true;
    }

    /**
     * Removes a player from the team, if he exists.
     *
     * @param playerUName - a player to remove from the team
     */
    public void removePlayer(String playerUName) {
        if (players.contains(playerUName)) {
            players.remove(playerUName);
            // Write to the log
            Logger.getInstance().addActionToLogger("Player "+playerUName+ "was removed from the team: "+team.getName());
        }
       else Logger.getInstance().addErrorToLogger("Player removal failed "+playerUName+ "not removed from the team: "+team.getName() + " because it doesnt exists.");

    }

    /**
     * Removes a coach from the team, if he exists.
     *
     * @param coachUserName - coach ID to remove from the team
     */
    public void removeCoach(String coachUserName) {
        LocalDate dateLog = LocalDate.now();
        LocalTime now = LocalTime.now();
        if (coaches.contains(coachUserName)) {
            coaches.remove(coachUserName);
            // Write to the log
            Logger.getInstance().addActionToLogger("Coach "+coachUserName+ "was removed from the team: "+team.getName());
        }
        else Logger.getInstance().addErrorToLogger("Coach removal failed "+coachUserName+ "not removed from the team: "+team.getName() + " because it doesnt exists.");

    }

    /**
     * Removes a manager from the team, if he exists.
     *
     * @param managerUserName      - a manager to remove from the team
     * @param userNameWhoNominated - the user name of the team owner who nominated this team
     *                             manager.
     */
    public void removeManager(String managerUserName, String userNameWhoNominated) {
        if (managers.containsKey(userNameWhoNominated)) {
            if(managers.get(userNameWhoNominated).remove(managerUserName)) {
                // Write to the log
                this.teamManagersHashSet.remove(managerUserName);
            }
        }

    }

    /**
     * Removes an owner from the team, if he exists.
     *
     * @param ownerUserName        - an owner to remove from the team
     * @param userNameWhoNominated - the user name of the team owner who nominated this team
     *                             manager.
     */
    public void removeTeamOwner(String ownerUserName, String userNameWhoNominated) {
        LocalDate dateLog = LocalDate.now();
        LocalTime now = LocalTime.now();
        if (owners.containsKey(userNameWhoNominated)) {
            if(owners.get(userNameWhoNominated).remove(ownerUserName)){
                this.teamOwnersHashSet.remove(ownerUserName);
                // Write to the log
                Logger.getInstance().addActionToLogger(dateLog + " " + now + ": Team Owner "+ownerUserName+ "was removed from the team: "+team.getName());
            }
        }
        else Logger.getInstance().addErrorToLogger(dateLog + " " + now + ": Team Owner removal failed "+ownerUserName+ "not removed from the team: "+team.getName() + " because it doesnt exists.");

    }

    /**
     * find a player in the team, given its username.
     * If he does not exist, returns NULL
     *
     * @param username - player's username
     * @return Player - the player by his username
     */
    public Player findPlayer(String username) {
        if (players.contains(username))
            return (Player) FootballSystem.getInstance().getFanByUserName(username);
        else return null;
    }

    /**
     * find a coach in the team, given its username.
     * If he does not exist, returns NULL
     *
     * @param username - player's username
     * @return Coach by his username
     */
    public Coach findCoach(String username) {
        if (coaches.contains(username))
            return (Coach) FootballSystem.getInstance().getFanByUserName(username);
        else return null;
    }

    /**
     * find a manager in the team, given its username.
     * If he does not exist, returns NULL
     *
     * @param username - player's username
     * @return Team Manager by his username
     */
    public TeamManager findManager(String username) {
        if (teamManagersHashSet.contains(username)) {
            return (TeamManager) FootballSystem.getInstance().getFanByUserName(username);
        }
        return null;
    }

    /**
     * find an owner in the team, given its username.
     * If he does not exist, returns NULL
     *
     * @param username - player's username
     * @return TeamOwner by his user name
     */
    public TeamOwner findTeamOwner(String username) {
        if (teamOwnersHashSet.contains(username)) {
            return (TeamOwner) FootballSystem.getInstance().getFanByUserName(username);
        }
        return null;
    }

    /**
     * this function checks if a team manager was nominate by team owner.
     *
     * @param teamManager          - the username of the team manager that we check if the team owner
     *                             nominated him.
     * @param userNameWhoNominated - the username of the team owner we want to check if had nominate the team
     *                             manager.
     * @return true if the userNameWhoNominated did nominated this teamManager.
     */
    public boolean whoNominateTeamManager(String teamManager, String userNameWhoNominated) {
        if (this.managers.containsKey(userNameWhoNominated)) {
            return this.managers.get(userNameWhoNominated).contains(teamManager);
        }
        return false;
    }

    /**
     * this function checks if a team owner was nominate by team owner.
     *
     * @param teamOwner            - the username of the team owner that we check about if the team owner
     *                             nominated him.
     * @param userNameWhoNominated - the username of the team owner we want to check if had nominate the team
     *                             owner.
     * @return true if the userNameWhoNominated did nominated this teamOwner.
     */
    public boolean whoNominateTeamOwner(String teamOwner, String userNameWhoNominated) {
        if (this.owners.containsKey(userNameWhoNominated)) {
            return this.owners.get(userNameWhoNominated).contains(teamOwner);
        }
        return false;
    }

    /**
     * this function removes all the nominations that user had nominated before
     *
     * @param userNameWhoNominated
     */
    public void removeAllNominations(String userNameWhoNominated) {
        if (this.managers.containsKey(userNameWhoNominated)) {
            for (String userToRemove : this.managers.get(userNameWhoNominated)){
                this.teamManagersHashSet.remove(userToRemove);
            }
            this.managers.get(userNameWhoNominated).clear();

        }
        if (this.owners.containsKey(userNameWhoNominated)) {
            for (String userToRemove : this.owners.get(userNameWhoNominated)){
                this.teamOwnersHashSet.remove(userToRemove);
            }
            this.owners.get(userNameWhoNominated).clear();
            this.owners.remove(userNameWhoNominated);
        }
    }


}
