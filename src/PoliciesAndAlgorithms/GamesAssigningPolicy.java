package PoliciesAndAlgorithms;


import AssociationAssets.Game;
import AssociationAssets.League;
import AssociationAssets.Season;
import AssociationAssets.Team;
import Model.RecordException;
import Users.Referee;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public abstract class GamesAssigningPolicy {
    boolean gamesAssigned;
    int counterMainRef;
    int counterSideRef;

    public abstract HashMap<Integer, Game> executePolicy(HashMap<String, Team> teams, Map<String, Referee> refs, LocalDate date, Season season, League league) throws RecordException, Exception;



}
