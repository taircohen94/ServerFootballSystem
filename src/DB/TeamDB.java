package DB;

import AssociationAssets.Team;

import java.util.HashMap;

/**
 * this class is the TeamDB class
 * Aouthors: Tair Cohen
 */
public class TeamDB {

    HashMap<String, Team> allTeams;

    public TeamDB() {
        this.allTeams = new HashMap<>();
    }

    public void addTeam(Team newTeam, String team) {
        allTeams.put(team, newTeam);
    }

    public void removeTeam(String team) {
        this.allTeams.remove(team);
    }

    public HashMap<String, Team> getAllTeams() {
        return allTeams;
    }
}