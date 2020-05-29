package Users;

import AssociationAssets.*;
import Model.RecordException;
import System.FootballSystem;
import System.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is the Team Owner Class.
 * Aouthors: Tair Cohen
 */
public class TeamOwner extends Fan {

    private List<AdditionalInfo> additionalInfoList;

    /**
     * The constructor for TeamOwner class.
     *
     * @param userName
     * @param firstName
     * @param lastName
     */
    public TeamOwner(String userName, String firstName, String lastName) {
        super(userName, firstName, lastName);
        this.additionalInfoList = new ArrayList<>();
    }

    //region Getters & Setters
    public List<AdditionalInfo> getAdditionalInfo() {
        return additionalInfoList;
    }

    public void setAdditionalInfo(List<AdditionalInfo> additionalInfo) {
        this.additionalInfoList = additionalInfo;
    }
    // end region

    // use case 6.1 region

    /**
     * This function adds player to a team in a season.
     * the function checks if the player has already username and password,which means
     * he is sign up already in the system, if he does then
     * the function only creating new player, otherwise the function sign in the player with his details
     * and as well creating new player.
     * the function checks if the player is playing already in another team -
     * and if so, is not been added to the team.
     *
     * @param team
     * @param season
     * @param userName
     * @param password
     * @param firstName
     * @param lastName
     * @param birthday
     * @param ePlayerRole
     * @return true if the player created successfully.
     */
    public boolean addPlayer(Team team, Season season, String userName, String password, String firstName, String lastName, Date birthday, EPlayerRole ePlayerRole) {
        if (checkInputsOfTeamAndSeason(team, season)) return false;
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        // if the user exist
        if (FootballSystem.getInstance().existFanByUserName(userName)) {
            // check if this user name is playing in another team
            if (FootballSystem.getInstance().findPlayerAtTeamByUserName(userName)) {
                Logger.getInstance().addErrorToLogger("Player (" + userName + ") adding to team" + team.getName() + "was failed");
                //pop up
                return false;
            }
            // check if the additional info has this player already
            if (!setPlayerToAdditionalInfo(team, season, userName)) {
                Logger.getInstance().addErrorToLogger("Player (" + userName + ") adding to team" + team.getName() + "was failed");
                //pop up
                return false;
            }
            Player player = (Player) FootballSystem.getInstance().creatingPlayer(userName, firstName, lastName, birthday, ePlayerRole);
            if (player == null) {
                player = (Player) FootballSystem.getInstance().getFanByUserName(userName);
            }
            player.addAdditionalInfo(additionalInfoToSearch);
        }
        // if the user doesnt exist - sign in & creation
        else {
            if (!setPlayerToAdditionalInfo(team, season, userName)) {
                Logger.getInstance().addErrorToLogger("Player (" + userName + ") adding to team" + team.getName() + "was failed");
                //pop up
                return false;
            }
            if (!signIn(userName, password, firstName, lastName)) {
                Logger.getInstance().addErrorToLogger("Player (" + userName + ") adding to team" + team.getName() + "was failed");
                //pop up
                return false;
            }
            Player player = (Player) FootballSystem.getInstance().creatingPlayer(userName, firstName, lastName, birthday, ePlayerRole);
            player.addAdditionalInfo(additionalInfoToSearch);

        }
        Logger.getInstance().addActionToLogger("Player (" + userName + ") adding to team" + team.getName() + ".");

        return true;
    }

    /**
     * this function set the player to the correct additional info of a specific team in a season.
     *
     * @param team
     * @param season
     * @param userName
     * @return true if the player was adding successfully
     */
    private boolean setPlayerToAdditionalInfo(Team team, Season season, String userName) {
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        if (additionalInfoToSearch == null) {
            return false;
        }
        if (!additionalInfoToSearch.addPlayer(userName)) {
            System.out.println("The player" + userName + "is already exist in the team" +
                    team + "in season" + season);
            return false;
        }
        return true;
    }

    /**
     * This function adds coach to a team in a season.
     * the function checks if the coach has already username and password,which means
     * he is sign up already in the system, if he does then
     * the function only creating new coach, otherwise the function sign in the coach with his details
     * and as well creating new coach.
     * the function checks if the coach is coaching already in another team -
     * and if so, is not been added to the team.
     *
     * @param team
     * @param season
     * @param userName
     * @param password
     * @param firstName
     * @param lastName
     * @param training
     * @param eCoachRole
     * @return true if the coach was added successfully to the team.
     */
    public boolean addCoach(Team team, Season season, String userName, String password, String firstName, String lastName, ETraining training, ECoachRole eCoachRole) {
        if (checkInputsOfTeamAndSeason(team, season)) return false;
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        // if the user exist
        if (FootballSystem.getInstance().existFanByUserName(userName)) {
            // check if this user name is coaching in another team
            if (FootballSystem.getInstance().findCoachAtTeamByUserName(userName)) {
                // System.out.println("this coach is coaching another team, user name is: " + userName);
                Logger.getInstance().addErrorToLogger("Coach adding was failed: " + userName + " unsuccessful add of coach by the team owner userName: " + getUserName());
                return false;
            }
            // check if the additional info has this coach already
            if (!setCoachToAdditionalInfo(team, season, userName)) {
                Logger.getInstance().addErrorToLogger("Coach adding was failed: " + userName + " unsuccessful add of coach by the team owner userName: " + getUserName());
                return false;
            }
            Coach coach = (Coach) FootballSystem.getInstance().creatingCoach(userName, firstName, lastName, training, eCoachRole);
            if (coach == null) {
                coach = (Coach) FootballSystem.getInstance().getFanByUserName(userName);
            }
            coach.addAdditionalInfo(additionalInfoToSearch);
        }
        // if the user doesnt exist
        else {
            if (!setCoachToAdditionalInfo(team, season, userName)) {
                Logger.getInstance().addErrorToLogger("Coach adding was failed: " + userName + " unsuccessful add of coach by the team owner userName: " + getUserName());
                return false;

            }
            if (!signIn(userName, password, firstName, lastName)) {
                Logger.getInstance().addErrorToLogger("Coach adding was failed: " + userName + " unsuccessful add of coach by the team owner userName: " + getUserName());
                return false;
            }
            Coach coach = (Coach) FootballSystem.getInstance().creatingCoach(userName, firstName, lastName, training, eCoachRole);
            coach.addAdditionalInfo(additionalInfoToSearch);
        }
        Logger.getInstance().addActionToLogger("Coach added: " + userName + "  add of coach by the team owner userName: " + getUserName());
        return true;
    }

    /**
     * this function set the coach to the correct additional info of a specific team in a season.
     *
     * @param team
     * @param season
     * @param userName
     * @return true if the coach was adding successfully to the team.
     */
    private boolean setCoachToAdditionalInfo(Team team, Season season, String userName) {

        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        if (additionalInfoToSearch == null) {
            return false;
        }
        if (!additionalInfoToSearch.addCoach(userName)) {
            System.out.println("The coach" + userName + "is already exist in the team" +
                    team + "in season" + season);
            return false;
        }
        return true;
    }

    /**
     * This function adds teamManager to a team in a season.
     * the function checks if the teamManager has already username and password,which means
     * he is sign up already in the system, if he does then
     * the function only creating new teamManager, otherwise the function sign in the teamManager with his details
     * and as well creating new teamManager.
     * the function checks if the teamManager is manage already in another team -
     * and if so, is not been added to the team.
     *
     * @param team
     * @param season
     * @param uName
     * @param password
     * @param firstName
     * @param lastName
     * @return true if the teamManager was added successfully to the team.
     */
    public boolean addTeamManager(Team team, Season season, String uName, String password, String firstName, String lastName) {
        if (checkInputsOfTeamAndSeason(team, season)) return false;
        // checking first if the user name is not team manger/owner in this team & season already.
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        if (additionalInfoToSearch == null) {
            Logger.getInstance().addErrorToLogger("Team manager adding failed");
            return false;
        }
        if (additionalInfoToSearch.findManager(uName) == null &&
                additionalInfoToSearch.findTeamOwner(uName) == null) {
            // if the user exist
            if (FootballSystem.getInstance().existFanByUserName(uName)) {
                if (FootballSystem.getInstance().findTeamManagerAtTeamByUserName(uName)) {
                    Logger.getInstance().addErrorToLogger("Team manager adding failed");
                    return false;
                }
                // check if the additional info has this  already
                if (!setTeamManagerToAdditionalInfo(team, season, uName)) {
                    Logger.getInstance().addErrorToLogger("Team manager adding failed");
                    return false;
                }
                TeamManager teamManager = (TeamManager) FootballSystem.getInstance().creatingTeamManager(uName, firstName, lastName);
                if (teamManager == null) {
                    teamManager = (TeamManager) FootballSystem.getInstance().getFanByUserName(getUserName());
                }
                teamManager.addAdditionalInfo(additionalInfoToSearch);
            }
            // if the user doesnt exist - need to sign in & create manager.
            else {
                if (!setTeamManagerToAdditionalInfo(team, season, uName)) {
                    return false;
                }
                if (!signIn(uName, password, firstName, lastName)) {
                    return false;
                }

                TeamManager teamManager = (TeamManager) FootballSystem.getInstance().creatingTeamManager(uName, firstName, lastName);
                teamManager.addAdditionalInfo(additionalInfoToSearch);
            }
            Logger.getInstance().addActionToLogger("Team manager was added");
            return true;

        } else {
//            System.out.println("The user " + uName + " is already team owner/manager on the team " +
//                    team + " on the season " + season);
            Logger.getInstance().addErrorToLogger("Team manager adding failed");
            return false;
        }
        // TODO: 4/12/2020 permissions of manager!
    }

    /**
     * this function set the Team Manager to the correct additional info of a specific team in a season.
     *
     * @param team
     * @param season
     * @param userName
     * @return true true if the team Manager was added successfully
     */
    private boolean setTeamManagerToAdditionalInfo(Team team, Season season, String userName) {
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        if (additionalInfoToSearch == null) {
            return false;
        }
        if (!additionalInfoToSearch.addManager(userName, getUserName())) {
            System.out.println("The manager" + userName + "is already exist in the team" +
                    team + "in season" + season);
            return false;
        }
        return true;
    }

    /**
     * this function adding field to team in specif season
     *
     * @param team
     * @param season
     * @param name
     * @param city
     * @param capacity
     * @return true if the field was adding successfully to the team
     */
    public boolean addField(Team team, Season season, String name, String city, int capacity) {
        if (checkInputsOfTeamAndSeason(team, season)) return false;
        if (name == null || city == null || capacity < 0) {
            System.out.println("incorrect inputs for field creation");
            return false;
        }
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        if (additionalInfoToSearch == null) {
            return false;
        }
        if (additionalInfoToSearch.getTeam().getFields().containsKey(name)) {
            System.out.println("The field " + name + " already exits at team fields, team: " +
                    team.getName() + " , season: " + season.getYear());
            return false;
        }
        Field field = FootballSystem.getInstance().createField(name, city, capacity);
        additionalInfoToSearch.getTeam().addField(field);
        return true;
    }

    /**
     * this function removes player from a team in specif season
     *
     * @param team
     * @param season
     * @param userName
     */
    public void removePlayer(Team team, Season season, String userName) {
        if (TeamIsInActive(team)) return;
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        additionalInfoToSearch.removePlayer(userName);
    }

    /**
     * this function removes coach from a team in specif season
     *
     * @param team
     * @param season
     * @param userName
     */
    public void removeCoach(Team team, Season season, String userName) {
        if (TeamIsInActive(team)) return;
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        additionalInfoToSearch.removeCoach(userName);
    }

    /**
     * this function removes team manager from a team in specif season
     *
     * @param team
     * @param season
     * @param userNameToRemove
     */
    public void removeTeamManager(Team team, Season season, String userNameToRemove) {
        if (TeamIsInActive(team)) return;
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        // checking if this team owner was the one that nominated the team manager
        if (additionalInfoToSearch.whoNominateTeamManager(userNameToRemove, getUserName())) {
            additionalInfoToSearch.removeManager(userNameToRemove, getUserName());
        } else {
            System.out.println(getUserName() + " you are not allow to remove " + userNameToRemove + "! " +
                    "you wasn't the one that nominated him");
        }
    }

    /**
     * * this function removes field from a team in specif season
     *
     * @param team
     * @param season
     * @param fieldName
     */
    public void removeField(Team team, Season season, String fieldName) {
        if (TeamIsInActive(team)) return;
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        additionalInfoToSearch.getTeam().removeField(fieldName);
    }
    // use case 6.1 end region

    //use case 6.2 region

    /**
     * this function nominate exits user in a team in specif season
     * if the user name is not exits in the system, the nomination discard.
     *
     * @param team
     * @param season
     * @param userName
     * @return true if the nomination successed
     */
    public boolean nominateTeamOwner(Team team, Season season, String userName) throws UnsupportedOperationException {
        if (checkInputsOfTeamAndSeason(team, season)) return false;
        // checking first if the user name is not team owner in this team and season already.
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        if (additionalInfoToSearch == null) {
            Logger.getInstance().addErrorToLogger("Nomination of team owner " + userName + " was failed.");
            throw new UnsupportedOperationException("The team and season combination is wrong" +
                    ".");
        }
        if (additionalInfoToSearch.findTeamOwner(userName) != null) {
            System.out.println("The user " + userName + " is already team owner on the team " +
                    team + " on the season " + season);
            Logger.getInstance().addErrorToLogger("Nomination of team owner " + userName + " was failed.");
            throw new UnsupportedOperationException("This user is already a team owner in this team.");
        }
        // checking if the user is in the system.
        if (FootballSystem.getInstance().existFanByUserName(userName)) {
            // checking if the user is owner another team.
            if (FootballSystem.getInstance().findTeamOwnerAtTeamByUserName(userName)) {
                Logger.getInstance().addErrorToLogger("Nomination of team owner " + userName + " was failed.");
                System.out.println("this team owner is owner another team, user name is: " + userName);
                throw new UnsupportedOperationException("This user is already a team owner in other team.");
            }
            Fan fan = FootballSystem.getInstance().getFanByUserName(userName);
            // check if the additional info has this teamOwner already
            if (!setTeamOwnerToAdditionalInfo(team, season, userName)) {
                throw new UnsupportedOperationException("This user is already a team owner in this team.");
            }
            TeamOwner teamOwner = (TeamOwner) FootballSystem.getInstance().creatingTeamOwner(userName, fan.getfName(), fan.getlName());
            if (teamOwner == null) {
                teamOwner = FootballSystem.getInstance().getTeamOwnerByUserName(userName);
            }
            teamOwner.addAdditionalInfo(additionalInfoToSearch);
        } else {
            Logger.getInstance().addErrorToLogger("Nomination of team owner " + userName + " was failed due to incorrect user name.");
            throw new UnsupportedOperationException("This user name isn't exists.");
        }
        Logger.getInstance().addActionToLogger("Nomination of team owner " + userName + " in team " + team.getName());
        return true;
    }

    /**
     * check null inputs
     *
     * @param team
     * @param season
     * @return true if the inputs are ok
     */
    private boolean checkInputsOfTeamAndSeason(Team team, Season season) {
        if (team == null || season == null) {
            return true;
        }
        if (TeamIsInActive(team)) return true;
        return false;
    }

    /**
     * this function set the Team owner to the correct additional info of a specific team in a season.
     *
     * @param team
     * @param season
     * @param userName
     * @return true if the team owner was adding successfully
     */
    private boolean setTeamOwnerToAdditionalInfo(Team team, Season season, String userName) {
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        if (additionalInfoToSearch == null) {
            return false;
        }
        if (!additionalInfoToSearch.addTeamOwner(userName, getUserName())) {
            System.out.println("The team owner" + userName + "is already exist in the team" +
                    team + "in season" + season);
            return false;
        }
        return true;
    }
    // use case 6.2 end region

    // use case 6.3 region

    /**
     * this function discards nomination for team owner
     *
     * @param team
     * @param season
     * @param userNameToRemove
     */
    public void discardNominationForTeamOwner(Team team, Season season, String userNameToRemove) {
        if (TeamIsInActive(team)) {
            Logger.getInstance().addErrorToLogger("Discard nomination  of team owner " + userNameToRemove + " was failed.");
            return;
        }
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        if (additionalInfoToSearch == null) {
            Logger.getInstance().addErrorToLogger("Discard nomination  of team owner " + userNameToRemove + " was failed.");
            return;
        }
        // checking if this team owner was the one that nominated the team manager
        if (additionalInfoToSearch.whoNominateTeamOwner(userNameToRemove, getUserName())) {
            additionalInfoToSearch.removeTeamOwner(userNameToRemove, getUserName());
            additionalInfoToSearch.removeAllNominations(userNameToRemove);
            Logger.getInstance().addActionToLogger("Discard nomination of team owner " + userNameToRemove);

        } else {
            Logger.getInstance().addErrorToLogger("Discard nomination  of team owner " + userNameToRemove + " was failed.");
            System.out.println(getUserName() + " you are not allow to remove " + userNameToRemove + "! " +
                    "you wasn't the one that nominated him");
        }
        // removing all nominations that the team owner to be removed had nominated
    }
    // use case 6.3 end region

    // use case 6.4 region

    /**
     * this function nominate exits user in a team in specif season
     * if the user name is not exits in the system, the nomination discard.
     *
     * @param team
     * @param season
     * @param userName
     * @return true if the nomination had been successes
     */
    public boolean nominateTeamManager(Team team, Season season, String userName) {
        Fan fan = FootballSystem.getInstance().getFanByUserName(userName);
        if (fan != null) {
            boolean added = addTeamManager(team, season, userName, null, fan.getfName(), fan.getlName());
            if (added) {
                Logger.getInstance().addActionToLogger("Discard nomination of team owner " + userName);

            } else Logger.getInstance().addErrorToLogger("Nomination  of team owner " + userName + " was failed.");
        } else {
            System.out.println("Nominate " + userName + "is not possible! " +
                    "This user name is not exit in the system, user name is: " + userName);
            Logger.getInstance().addErrorToLogger("Nomination  of team owner " + userName + " was failed.");
        }
        return false;
    }
    // use case 6.4 end region

    // use case 6.6 region

    /**
     * this function closes the team in a specif season
     *
     * @param team
     * @param season
     */
    public void closeTeam(Team team, Season season) {
        AdditionalInfo additionalInfo = getAdditionalInfo(team, season);
        if (additionalInfo == null) {
            Logger.getInstance().addErrorToLogger("Closing team failed.");
            return;
        }
        Team tempTeam = additionalInfo.getTeam();
        if (tempTeam == null) {
            return;
        }
        additionalInfo.getTeam().setIsActive(ETeamStatus.INACTIVE);
        Logger.getInstance().addActionToLogger("Team \"" + tempTeam.getName() + "\" closed");
    }
    // use case 6.6 end region

    // use case 6.7 region
    public void finnacelReport(Team team) {
        if (TeamIsInActive(team)) return;
    }
    // use case 6.7 end region

    /**
     * @param team
     * @param season
     * @return additional info for specif team in a season
     */
    private AdditionalInfo getAdditionalInfo(Team team, Season season) {

        return team.getAdditionalInfoWithSeasons().get(season.getYear());
//        AdditionalInfo additionalInfoToSearch = null;
//        for (AdditionalInfo additionalInfo : this.additionalInfoList) {
//            if (additionalInfo.getTeam().equals(team) && additionalInfo.getSeason().equals(season)) {
//                additionalInfoToSearch = additionalInfo;
//            }
//        }
//        return additionalInfoToSearch;
    }

    /**
     * @param userName
     * @param password
     * @param firstName
     * @param lastName
     * @return true if the sign in was succeed
     */
    private boolean signIn(String userName, String password, String firstName, String lastName) {
        Fan fan = FootballSystem.getInstance().signIn(userName, password, firstName, lastName);
        // invalid inputs or username already exist.
        if (fan == null) {
            return false;
        }
        return true;
    }

    public void addAdditionalInfo(AdditionalInfo additionalInfo) {
        additionalInfoList.add(additionalInfo);
    }

    /**
     * @param team
     * @return true if the team is active
     */
    private boolean TeamIsInActive(Team team) {
        if (team != null && team.getIsActive().equals(ETeamStatus.INACTIVE)) {
            //pop up
            System.out.println("The team is close, you are not allow to make any operation.");
            return true;
        }
        return false;
    }


    public boolean editCoachDetails(Team team, Season season, String userName, String firstName,
                                    String lastName, ETraining training, ECoachRole eCoachRole) throws RecordException {
        if (checkInputsOfTeamAndSeason(team, season)) return false;
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        // if this team owner owns this team
        if (!additionalInfoToSearch.getOwners().containsKey(this.getUserName())) {
            throw new RecordException("You are not own this team");
        }
        // if the user exist
        if (FootballSystem.getInstance().existFanByUserName(userName)) {
            // check if this user name is coaching at the team
            if (additionalInfoToSearch.getCoaches().contains(userName)) {
                Coach coach = (Coach) FootballSystem.getInstance().getFanByUserName(userName);
                if (firstName != null && !firstName.isEmpty()) {
                    coach.setfName(firstName);
                }
                if (lastName != null && !lastName.isEmpty()) {
                    coach.setlName(lastName);
                }
                if (eCoachRole != null) {
                    coach.setRole(eCoachRole);
                }
                if (training != null) {
                    coach.setTraining(training);
                }
            } else {
                throw new RecordException("This user name is not a coach at this team");
            }
        } else {
            throw new RecordException("This username doesnt exist in the system");
        }
        Logger.getInstance().addActionToLogger("Coach: " + userName + "  edit coach details by the team owner userName: " + getUserName());
        return true;
    }


    public boolean editTMDetails(Team team, Season season, String userName, String firstName,
                                    String lastName) throws RecordException {
        if (checkInputsOfTeamAndSeason(team, season)) return false;
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        // if this team owner owns this team
        if (!additionalInfoToSearch.getOwners().containsKey(this.getUserName())) {
            throw new RecordException("You are not own this team");
        }
        // if the user exist
        if (FootballSystem.getInstance().existFanByUserName(userName)) {
            // check if this user name is coaching at the team
            if (additionalInfoToSearch.getTeamManagersHashSet().contains(userName)) {
                TeamManager teamManager = (TeamManager) FootballSystem.getInstance().getFanByUserName(userName);
                if (firstName != null && !firstName.isEmpty()) {
                    teamManager.setfName(firstName);
                }
                if (lastName != null && !lastName.isEmpty()) {
                    teamManager.setlName(lastName);
                }
            } else {
                throw new RecordException("This user name is not a teamManager at this team");
            }
        } else {
            throw new RecordException("This username doesnt exist in the system");
        }
        Logger.getInstance().addActionToLogger("teamManager: " + userName + "  edit teamManager details by the team owner userName: " + getUserName());
        return true;
    }


    public boolean editPlayerDetails(Team team, Season season, String userName, String firstName,
                                    String lastName, EPlayerRole role) throws RecordException {
        if (checkInputsOfTeamAndSeason(team, season)) return false;
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        // if this team owner owns this team
        if (!additionalInfoToSearch.getOwners().containsKey(this.getUserName())) {
            throw new RecordException("You are not own this team");
        }
        // if the user exist
        if (FootballSystem.getInstance().existFanByUserName(userName)) {
            // check if this user name is coaching at the team
            if (additionalInfoToSearch.getCoaches().contains(userName)) {
                Player player = (Player) FootballSystem.getInstance().getFanByUserName(userName);
                if (firstName != null && !firstName.isEmpty()) {
                    player.setfName(firstName);
                }
                if (lastName != null && !lastName.isEmpty()) {
                    player.setlName(lastName);
                }
                if (role != null) {
                    player.setRole(role);
                }
            } else {
                throw new RecordException("This user name is not a player at this team");
            }
        } else {
            throw new RecordException("This username doesnt exist in the system");
        }
        Logger.getInstance().addActionToLogger("player: " + userName + "  edit player details by the team owner userName: " + getUserName());
        return true;
    }


    public boolean editFieldDetails(Team team, Season season, String fieldName, String city,
                                    String capacity) throws RecordException {
        if (checkInputsOfTeamAndSeason(team, season)) return false;
        AdditionalInfo additionalInfoToSearch = getAdditionalInfo(team, season);
        // if this team owner owns this team
        if (!additionalInfoToSearch.getOwners().containsKey(this.getUserName())) {
            throw new RecordException("You are not own this team");
        }
        // if the field exist
        if (FootballSystem.getInstance().existFieldByName(fieldName)) {
            if (team.getFields().containsKey(fieldName)) {
                Field field = FootballSystem.getInstance().getFieldDB().getAllFields().get(fieldName);
                if (city != null && !city.isEmpty()) {
                    field.setCity(city);
                }
                if (capacity != null && !capacity.isEmpty()) {
                    field.setCapacity(Integer.parseInt(capacity));
                }
            } else {
                throw new RecordException("This field name is not a field at this team");
            }
        } else {
            throw new RecordException("This field doesnt exist in the system");
        }
        Logger.getInstance().addActionToLogger("field: " + fieldName + "  edit field details by the team owner userName: " + getUserName());
        return true;
    }




}

