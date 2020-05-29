package AssociationAssets;

import PoliciesAndAlgorithms.GamesAssigningPolicy;
import PoliciesAndAlgorithms.ScoreTablePolicy;
import Users.Referee;
import com.sun.xml.internal.bind.v2.TODO;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;

import javax.naming.OperationNotSupportedException;
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
        if(!hasStarted())
            this.assigningPolicy = assigningPolicy;
        else
            throw new UnsupportedOperationException("Can't change Assigning policy after league already began");

    }

    public boolean setScoreTablePolicy(ScoreTablePolicy scoreTablePolicy) throws UnsupportedOperationException {
        if(!hasStarted())
            this.scoreTablePolicy = scoreTablePolicy;
        else
            throw new UnsupportedOperationException("Can't change score policy after league already began");
        return true;
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
            if(entry.getValue().isFinished())
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