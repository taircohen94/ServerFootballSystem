
package DB;

import AssociationAssets.League;

import java.util.HashMap;

/**
 * this class is the LeagueDB class
 * Aouthors: Tair Cohen
 */
public class LeagueDB {
    HashMap<String, League> allLeagues;

    public LeagueDB() {
        this.allLeagues = new HashMap<>();
    }

    public void addLeague(League newLeague, String leagueName) {
        allLeagues.put(leagueName, newLeague);
    }

    public void removeLeague(String leagueName) {
        this.allLeagues.remove(leagueName);
    }

    public HashMap<String, League> getAllLeagues() {
        return allLeagues;
    }
}