package AssociationAssets;

import PoliciesAndAlgorithms.GamesAssigningPolicy;
import PoliciesAndAlgorithms.ScoreTablePolicy;
import Users.Referee;

import java.util.HashMap;
import java.util.Map;

/**
 * this class goal is to connect between pairs of league and season.
 */
public class SeasonLeagueBinder {

    Season season;
    League league;
    HashMap<String,Team> teams;
    HashMap<Integer, Game> games;
    HashMap<String, Referee> referees;
    HashMap<String /*team name*/,Integer/*points*/> leagueTable;
    ScoreTablePolicy scoreTablePolicy;// not in the constructor
    GamesAssigningPolicy assigningPolicy;// not in the constructor


    public SeasonLeagueBinder(Season season, League league) {
        this.season = season;
        this.league = league;
        teams= new HashMap<>();
        games= new HashMap<>();
        referees = new HashMap<>();
        leagueTable = new HashMap<>();

    }


    //region Getters & Setters
    public void setAssigningPolicy(GamesAssigningPolicy assigningPolicy) {
        if(assigningPolicy != null)
            this.assigningPolicy = assigningPolicy;

    }

    public void setScoreTablePolicy(ScoreTablePolicy scoreTablePolicy)  {
        if(scoreTablePolicy != null)
            this.scoreTablePolicy = scoreTablePolicy;
    }

    public void setScoreTablePolicyUploader(ScoreTablePolicy scoreTablePolicy) throws UnsupportedOperationException {
        if(scoreTablePolicy!= null)
            this.scoreTablePolicy = scoreTablePolicy;

    }
    public void setAssigningPolicyUploader(GamesAssigningPolicy assigningPolicy) {
        if(assigningPolicy != null)
            this.assigningPolicy = assigningPolicy;
    }

    public ScoreTablePolicy getScoreTablePolicy() {
        return scoreTablePolicy;
    }
    public HashMap<String, Team> getTeams() { return teams; }
    public HashMap<Integer, Game> getGames() { return games; }

    public Season getSeason() {
        return season;
    }

    public HashMap<String, Integer> getLeagueTable() {
        return leagueTable;
    }


    public League getLeague() {
        return league;
    }

    public boolean hasStarted() {
        for (Map.Entry<Integer,Game> entry:games.entrySet()) {
            if(entry.getValue().isSomeGameHasStarted())
                return true;
        }
        return false;
    }

    public GamesAssigningPolicy getAssigningPolicy() {
        return assigningPolicy;
    }

    //endregion


    //region Adders

    /**
     * adding teams to this specific combination of season and league
     * after adding teams, function call to initialize the league table.
     * @param teams
     */
    public void addTeamsToLeague(HashMap<String, Team> teams) {
        this.teams.putAll(teams);
        initializeLeagueTable();
    }

    private void initializeLeagueTable() {
        for (Map.Entry<String,Team> entry:teams.entrySet()) {
            leagueTable.put(entry.getKey(),0);
        }
    }

    /**
     * adding games to this specific combination of season and league
     * @param games
     */
    public void addGamesToLeague(HashMap<Integer, Game> games) {
        this.games.putAll(games);
    }

    //TODO remove game & remove team functions (not sure this is necessary)


    //endregion
}