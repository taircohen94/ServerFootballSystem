package DB;

import AssociationAssets.Season;

import java.util.HashMap;
/**
 * this class is the SeasonDB class.
 * Aouthors: Tair Cohen
 */
public class SeasonDB {
    HashMap<String/*season year*/, Season> allSeasons;

    public SeasonDB() {
        this.allSeasons = new HashMap<>();
    }
    public void addSeason(Season newSeason,String seasonYear){
        allSeasons.put(seasonYear,newSeason);
    }
    public void removeSeason(String seasonYear){
        allSeasons.remove(seasonYear);
    }

    public HashMap<String, Season> getAllSeasons() {
        return allSeasons;
    }
}
