package Users;
import AssociationAssets.*;
import RecommendationSystem.*;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import System.*;

/**
 * An administrator is responsible for responding to the various user inquiries
 * and for proper system operation.
 * @ Written by Yuval Ben Eliezer
 */
public class SystemManager extends Fan {

    /**
     * @param userName - Unique user name
     * @param fName - First name of the nager.
     * @param lName - Last name of the nager.
     */
    public SystemManager(String userName, String fName, String lName) {
        super(userName, fName, lName);
        LocalDate date = LocalDate.now();
        LocalTime now = LocalTime.now();
        Logger.getInstance().addActionToLogger(date + " " + now + ": System Manager created, user name: "+ userName);

    }

    /**
     *  An administrator may permanently close a group
     * If there were future games for the team, they were canceled
     * # use case 8.1
     *
     * @param teamName -team name - to close
     */
    public void closeTeam(String teamName){
        Team team = FootballSystem.getInstance().getTeamDB().getAllTeams().get(teamName);
        if(team != null){
            List <Game> gamesList = new ArrayList<>();
            gamesList.addAll(team.getAwayGames().values());
            gamesList.addAll(team.getHomeGames().values());
            for (Game game:
                 gamesList) {
                if(game.getDate().after(new Date())){
                    game.setStatus(EGameStatus.CANCELED);
                }
            }
            team.setIsActive(ETeamStatus.INACTIVE);
            Logger.getInstance().addActionToLogger("Team "+teamName+" closed by the nager userName: "+getUserName());
        }
    }

    /**
     * This function deletes a coach, before deleting it makes sure that the
     * coach is not used as a coach in the future season
     *
     * @param userName - coach's userName to delete
     *
     * # use case 8.2
     */
    public void removeCoach(String userName){
        Coach coach = FootballSystem.getInstance().getCoachByUserName(userName);
        if(coach != null){
            //Checks whether he coaches a particular team in a future season
            for (Season season:
                    FootballSystem.getInstance().getSeasonDB().getAllSeasons().values()) {
              if(Integer.parseInt(season.getYear()) > Calendar.getInstance().get(Calendar.YEAR)){
                  for (AdditionalInfo additional:
                       season.getTeamAdditionalInfo().values()) {
                      for (String coachToCompare:
                           additional.getCoaches()) {
                          if(coachToCompare.equals(userName)){
                              return;
                          }
                      }
                  }
                }
            }
            FootballSystem.getInstance().removeUser(userName);
            Logger.getInstance().addActionToLogger( "Coach "+userName+" deleted by the System Manager userName: "+getUserName());
        }
    }

    /**
     * This function deletes a player, before deleting it makes sure the
     * player is not used as a player in a future season
     *
     * @param userName - player's userName to delete
     *
     *  # use case 8.2
     */
    public void removePlayer(String userName){
        Player player = FootballSystem.getInstance().getPlayerByUserName(userName);
        if(player != null){
            //Checks whether he coaches a particular team in a future season
            for (Season season:
                    FootballSystem.getInstance().getSeasonDB().getAllSeasons().values()) {
                if(Integer.parseInt(season.getYear()) > Calendar.getInstance().get(Calendar.YEAR)){
                    for (AdditionalInfo additionalInfo:
                            season.getTeamAdditionalInfo().values()) {
                        for (String playerToCompare:
                                additionalInfo.getPlayers()) {
                            if(playerToCompare.equals(userName)){
                                Logger.getInstance().addErrorToLogger("Player delete Failed: "+userName+" unsuccessful delete of player by the system Manager userName: "+getUserName());
                                return;
                            }
                        }
                    }
                }
            }

            FootballSystem.getInstance().removeUser(userName);
            Logger.getInstance().addActionToLogger("Player "+userName+" deleted by the system Manager userName: "+getUserName());
        }
    }

    /**
     * This function deletes a team owner - if not used as a team owner
     *  in the future
     *
     * @param userName- Team Owner's userName to delete
     *
     *# use case 8.2
     */
    public void removeTeamOwner(String userName){
        TeamOwner teamOwner = FootballSystem.getInstance().getTeamOwnerByUserName(userName);
        if(teamOwner != null){
            //Checks whether he coaches a particular team in a future season
            for (Season season:
                    FootballSystem.getInstance().getSeasonDB().getAllSeasons().values()) {
                if(Integer.parseInt(season.getYear()) > Calendar.getInstance().get(Calendar.YEAR)){
                    for (AdditionalInfo additionalInfo:
                            season.getTeamAdditionalInfo().values()) {
                        for (String teamOwnerToCompare:
                                additionalInfo.getTeamOwnersHashSet()) {
                            if(teamOwnerToCompare.equals(userName)){
                                Logger.getInstance().addErrorToLogger("Team Owner delete Failed: "+userName+" unsuccessful delete of owner by the system Manager userName: "+getUserName());
                                return;
                            }
                        }
                    }
                }
            }
            FootballSystem.getInstance().removeUser(userName);

            Logger.getInstance().addActionToLogger("Team Owner "+userName+" deleted by the system manager userName: "+getUserName());
        }
    }

    /**
     * This function deletes an administrator from the system if at least one exists.
     *
     * @param userName - System Manager's userName to delete
     *
     *  # use case 8.2
     */
    public void removeSystemManager(String userName){
        SystemManager systemManager = FootballSystem.getInstance().getSystemManagerByUserName(userName);
        if(systemManager != null){
            if(FootballSystem.getInstance().getSystemManagerMap().values().size() == 1){
                Logger.getInstance().addErrorToLogger("System Manager delete Failed: "+userName+" unsuccessful delete of system manager by the system Manager userName: "+getUserName());
                return;
            }
            FootballSystem.getInstance().removeUser(userName);
            Logger.getInstance().addActionToLogger("System Manager: "+userName+" deleted by the system manager userName: "+getUserName());
        }
    }

    /**
     *This function deletes a fan, before deleting it verifies that the fan
     *  is not either: Group owner or player or coach or referee or
     *  administrator or association representative or team administrator
     *
     * @param userName - fan's userName to delete
     *
     *  # use case 8.2
     */
    public void removeFan(String userName){
        Fan fan = FootballSystem.getInstance().getFanByUserName(userName);
        if(fan == null ) {
                Logger.getInstance().addErrorToLogger("Removing user: " + userName + " has failed. User name not exists.");
                return;
        }
        else if(moreThenARegularUser(userName)){
            Logger.getInstance().addErrorToLogger("Removing user: " + userName + "failed. This user has a role in the system and needs to be removed accordingly.");
            return;
        }
            FootballSystem.getInstance().removeUser(userName);
            Logger.getInstance().addActionToLogger("Fan "+userName+" deleted by the system manager. userName: "+getUserName());

    }

    private boolean moreThenARegularUser(String userName) {
        if( FootballSystem.getInstance().getRefereeByUseName(userName) != null ||
                FootballSystem.getInstance().getSystemManagerByUserName(userName) != null ||
                FootballSystem.getInstance().getRepresentativeFootballAssociationByUseName(userName) != null||
                FootballSystem.getInstance().getPlayerByUserName(userName) != null||
                FootballSystem.getInstance().getCoachByUserName(userName) != null||
                FootballSystem.getInstance().getTeamOwnerByUserName(userName) != null||
                FootballSystem.getInstance().getTeamManagerByUserName(userName) != null)
            return true;
        return false;
    }


    /**
     *  An administrator can view the complaint box
     * @return - List all complaints and who filed them
     *
     * # use case 8.3
     */
    public List<Pair<String, Fan>> getComplains(){
        return Complains.getInstance().getComplain();
    }

    /**
     * An administrator may respond to complaints received from fans
     * @param response -The answer from the administrator
     * @param compain - The complaint and the fan who filed it
     *
     * # use case 8.3
     */
    public void responseOnComplain(String response, Pair<String,Fan> compain){
        Complains.getInstance().responseToComplain(this,compain,response);
    }

    /**
     * An administrator can view system behavior information
     * @return - List all actions done on the system
     *
     * # use case 8.4
     */
    public List<String> getActionLogInformation(){
        return Logger.getInstance().getActionLog();
    }

    /**
     * An administrator can view system errors information
     * @return - List all errors done on the system
     *
     */
    public List<String> getErrorLogInformation(){
        return Logger.getInstance().getErrorLog();
    }


    /**
     * The role of the administrator is to start building the
     * recommendation system model
     * @param model - A recommendation system designed to indicate the chances of a
     *              team winning the game.
     *
     * # use case 8.5
     *
     */
    public void activateRecommendationSystemModel(ComputaionalModel model){
        model.activate();
        Logger.getInstance().addActionToLogger("System manager activate recommendation system model, user name: "+ getUserName());

    }

}