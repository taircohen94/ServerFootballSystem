package BL.PoliciesAndAlgorithms;


import BL.AssociationAssets.Game;
import BL.AssociationAssets.League;
import BL.AssociationAssets.Season;
import BL.AssociationAssets.Team;
import BL.Users.Referee;
import SL.Model.RecordException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public abstract class GamesAssigningPolicy {
    boolean gamesAssigned;
    int counterMainRef;
    int counterSideRef;

    public abstract HashMap<Integer, Game> executePolicy(HashMap<String, Team> teams, Map<String, Referee> refs, LocalDate date, Season season, League league) throws RecordException;



}
