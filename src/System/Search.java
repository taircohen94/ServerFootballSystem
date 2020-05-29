package System;

import AssociationAssets.*;
import Users.*;

import java.util.HashMap;
import java.util.Map;

public class Search {
    private FootballSystem footballSystem = FootballSystem.getInstance();
    /**
     * constructor
     */
    public Search() {
    }

    //Todo: systemManager, teamManger, teamOwner, representative
    public Object getUserByUserName(String uName){
        if(uName != null) {
            Referee userRef = footballSystem.getRefereeByUseName(uName);
            if(userRef!=null){
                return userRef;
            }
            Coach userCoach = footballSystem.getCoachByUserName(uName);
            if(userCoach!=null){
                return userCoach;
            }
            Player userPlayer = footballSystem.getPlayerByUserName(uName);
            if(userPlayer!=null){
                return userPlayer;
            }
            SystemManager userSystemManager = footballSystem.getSystemManagerByUserName(uName);
            if(userSystemManager!=null){
                return userSystemManager;
            }
             TeamManager userTeamManger = footballSystem.getTeamManagerByUserName(uName);
             if(userTeamManger!=null){
                return userTeamManger;
            }
             TeamOwner userTeamOwner =footballSystem.getTeamOwnerByUserName(uName);
             if(userTeamOwner!=null){
                return userTeamOwner;
            }
           RepresentativeFootballAssociation representativeUser =footballSystem.getRepresentativeFootballAssociationByUseName(uName);
            if(representativeUser!=null){
                return representativeUser;
            }
            Fan userFan = footballSystem.getFanByUserName(uName);
            if(userFan!=null){
                return userFan;
            }
        }
        return null;
    }
    /**
     * @param teamName
     * @return Team by team name
     */
    public Team getTeamByTeamName(String teamName){
        if(teamName!=null) {
            if (footballSystem.getTeamDB().getAllTeams().containsKey(teamName)) {
                return footballSystem.getTeamDB().getAllTeams().get(teamName);
            }
        }
        return null;
    }

    /**
     * @param leagueName
     * @return League by league name
     */
    public League getLeagueByLeagueName(String leagueName){
        if(leagueName!=null){
            if (footballSystem.getLeagueDB().getAllLeagues().containsKey(leagueName)) {
                return footballSystem.getLeagueDB().getAllLeagues().get(leagueName);
            }
        }
        return null;
    }

    /**
     * @param year
     * @return Season by year
     */
    public Season getSeasonByYear(String year){
        if(year!= null) {
            if(footballSystem.getSeasonDB().getAllSeasons().containsKey(year)){
                return footballSystem.getSeasonDB().getAllSeasons().get(year);
            }
        }
        return null;
    }


    /**
     * @param leagueName -
     * @param year -
     * @return Season of a specific league, by it's year
     */
    public Season getSeasonByYear(String leagueName, String year){
        if(year!= null && leagueName!=null) {
            League league = getLeagueByLeagueName(leagueName);
            if(league==null){
                return null;
            }
            SeasonLeagueBinder yearOfSeason = league.getSeasonBinders().get(year);
            if(yearOfSeason != null){
                Season season= yearOfSeason.getSeason();
                if(season!=null){
                    return season;
                }
            }
        }
        return null;
    }



    /**
     * @param gameID
     * @return Game by game id
     */
    public Game getGameByGameID(Integer gameID){
        if(gameID!= null) {
            if(footballSystem.getGameDB().getAllGames().containsKey(gameID)){
                return footballSystem.getGameDB().getAllGames().get(gameID);
            }
        }
        return null;
    }

    /**
     * @param fieldName
     * @return Field by field name
     */
    public Field getFieldByFieldName(String fieldName){
        if(fieldName!= null) {
            if(footballSystem.getFieldDB().getAllFields().containsKey(fieldName)){
                return footballSystem.getFieldDB().getAllFields().get(fieldName);
            }
        }
        return null;
    }

    /**
     * @return Hash map that holds for each league the league's name and its profile details
     */
    public HashMap<String, String> getAllLeaguesProfile(){
        HashMap<String, String>leaguesProfile = new HashMap<>();
        for (Map.Entry<String,League> entry : footballSystem.getLeagueDB().getAllLeagues().entrySet()) {
            String profileDetails=  entry.getValue().viewProfile();
            leaguesProfile.put(entry.getKey(), profileDetails);
        }
        return leaguesProfile;
    }

    /**
     * @return Hash map that holds for each team the team's name and its profile details
     */
    public HashMap<String, String> getAllTeamsProfile(){
        HashMap<String, String> teamsProfile = new HashMap<>();
        for (Map.Entry<String,Team> entry : footballSystem.getTeamDB().getAllTeams().entrySet()) {
            String profileDetails=  entry.getValue(). viewProfile();
            teamsProfile.put(entry.getValue().getName(),profileDetails);
        }
        return teamsProfile;
    }

    /**
     * @return Hash map that holds, for each referee, the referee's user name and his profile details
     */
    public HashMap<String, String> getAllRefereesProfile(){
        HashMap<String, String> refereesProfile = new HashMap<>();
        for (Map.Entry<String, Referee> entry : footballSystem.getRefereeMap().entrySet()) {
                String profileDetails = entry.getValue().viewProfile();
                refereesProfile.put(entry.getValue().getUserName(), profileDetails);

        }
        return refereesProfile;
    }

    /**
     * @return Hash map that holds, for each coach, the coach's user name and his profile details
     */
    public HashMap<String, String> getAllCoachesProfile(){
        HashMap<String, String> coachesProfile = new HashMap<>();
       for (Map.Entry<String, Coach> entry : footballSystem.getCoachMap().entrySet()) {
           String profileDetails= entry.getValue().viewProfile();
            coachesProfile.put(entry.getValue().getUserName(),profileDetails);
        }
        return coachesProfile;
    }

    /**
     * @return Hash map that holds, for each player, the player's user name and his profile details
     */
    public HashMap<String, String> getAllPlayersProfile(){
        HashMap<String, String> playersProfile = new HashMap<>();
        for (Map.Entry<String, Player> entry : footballSystem.getPlayerMap().entrySet()) {
            String profileDetails=  entry.getValue().viewProfile();
            playersProfile.put(entry.getValue().getUserName(),profileDetails);
        }
        return playersProfile;
    }

}

