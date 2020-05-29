package Model;

import AssociationAssets.*;
import System.Search;


public class ValidateObject {

    static Search search = new Search();

    /**
     * Validates a presence of a league in the system
     * @param leagueName-
     * @return League object if valid
     * @throws RecordException - No such league name in the system
     */
    public static League getValidatedLeague(String leagueName) throws RecordException {
        League league= search.getLeagueByLeagueName(leagueName);
        if (league == null) {
            throw new RecordException("League name " + leagueName + " does not exist.");
        }
        return league;
    }

    /**
     * Validates a presence of a specific season in the system
     * @param leagueName-
     * @param seasonYear-
     * @return Season object if valid
     * @throws RecordException - No such Season in the required league
     */
    public static Season getValidatedSeason(String leagueName, String seasonYear) throws RecordException {
        Season season = search.getSeasonByYear(leagueName, seasonYear);
        if (season == null) {
            throw new RecordException("Season " + seasonYear + " does not exist in the requested league.");
        }
        return season;
    }

    /**
     * Returns a team after validating it's presence and activeness.
     * @param leagueName-e
     * @param teamName-
     * @return A specific team for the required season & league
     * @throws RecordException - in case team does not exist or team is inactive.
     */
    public static Team getValidatedTeam(String leagueName, String seasonYear,String teamName) throws RecordException {
        Team team;
        Season season= getValidatedSeason(leagueName,seasonYear);
        team= season.getTeamAdditionalInfo().get(teamName).getTeam();
        if(team==null){
            throw new RecordException("Team " + teamName + " does not exist in the requested league and season.");
        }
        if(team.getIsActive()== ETeamStatus.INACTIVE){
            throw new RecordException("Team " + teamName + " is not active anymore.");
        }
        return team;
    }

    /**
     * Returnes a game after validating it's presence.
     * @param gameID-
     * @return a game by its ID
     * @throws RecordException - No such a game!
     */
    public static Game getValidatedGame(int gameID) throws RecordException {
        Game game= search.getGameByGameID(gameID);
        if(game==null){
            throw new RecordException("No such a game!");
        }
        else
        return game;
    }
}
