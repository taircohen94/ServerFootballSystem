package Users;

import AssociationAssets.League;
import AssociationAssets.Season;
import AssociationAssets.Team;
import System.*;

import javax.security.auth.login.FailedLoginException;
import java.util.LinkedList;
import java.util.Map;

public class Guest {
    Search search;

    /**
     * constructor
     */
    public Guest() {
        search = new Search();
    }

    /**
     * UC 2.2 -  Guest's sign in
     *
     * @param userName
     * @param password
     * @param firstName
     * @param lastName
     */
    public Fan signInGuest(String userName, String password, String firstName, String lastName) throws FailedLoginException {
        Fan newFan = FootballSystem.getInstance().signIn(userName, password, firstName, lastName);
        logInGuest(userName, password);
        return newFan;
    }

    /**
     * useCase 2.3 - Guest's login
     * @param userName
     * @param password
     */
    public Fan logInGuest(String userName, String password) throws FailedLoginException {
        Fan user = FootballSystem.getInstance().login(userName, password);
        if(user != null) user.setStatus(EStatus.ONLINE);
        return user;
    }

    /**
     * useCase 2.4- Guest viewing information about the given coach
     * @param coachUserName
     */
    public String viewInformationCoach(String coachUserName) {
        if (coachUserName != null) {
            Coach coach = (Coach) search.getUserByUserName(coachUserName);
            if (coach != null) {
                return coach.viewProfile();
            }
        }
        return null;
    }

    /**
     * useCase 2.4- Guest viewing information about the given player
     * @param playerUserName
     */
    public String viewInformationPlayer(String playerUserName) {
        if (playerUserName != null) {
            Player player = (Player) search.getUserByUserName(playerUserName);
            if (player != null) {
                return player.viewProfile();
            }
        }
        return null;
    }

    /**
     * useCase 2.4 - Guest viewing information about the given team
     * @param teamName
     */
    public String viewInformationTeam(String teamName) {
        if (teamName != null) {
            Team team = search.getTeamByTeamName(teamName);
            if (team != null) {
                return team.viewProfile();
            }
        }
        return null;
    }

    /**
     * useCase 2.4 - Guest viewing information about the given league
     * @param leagueName
     */
    public String viewInformationLeague(String leagueName) {
        if (leagueName != null) {
            League league = search.getLeagueByLeagueName(leagueName);
            if (league != null) {
                return league.viewProfile();
            }
        }
        return null;
    }

    /**
     * useCase 2.4 - Guest viewing information about the given season
     * @param year
     */
    public String viewInformationSeason(String year) {
        if (year != null) {
            Season season = search.getSeasonByYear(year);
            if (season != null) {
                return season.viewProfile();
            }
        }
        return null;
    }

    /**
     * useCase 2.5 - search by user-name, league name and team name.
     * if the search finds results, their profile will be printed accordingly
     * @param name
     */
    public String searchByName(String name) {
        if (name != null) {
            Object user = search.getUserByUserName(name);
            if (user instanceof Referee) {
                if (user != null) {
                    return ((Referee) user).viewProfile();
                }
            } else if (user instanceof Coach)
                if (user != null) {
                    return ((Coach) user).viewProfile();
                } else if (user instanceof Player)
                    if (user != null) {
                        return ((Player) user).viewProfile();
                    }
            League league = search.getLeagueByLeagueName(name);
            if (league != null) {
                return league.viewProfile();
            }
            Team team = search.getTeamByTeamName(name);
            if (team != null) {
                return team.viewProfile();
            }
        }
        return null;
    }

    /**
     * useCase 2.5 - search by league category, team category, Referee category, player category or coach category
     * if the search finds results, their profile will be printed accordingly
     *
     * @param categoryName
     * @return
     */
    public LinkedList<String> searchByCategory(String categoryName) {
        LinkedList<String> results = null;
        if (categoryName != null) {
           results = new LinkedList<>();
            if (categoryName.contains("League")) {
                for (Map.Entry<String, String> entry : search.getAllLeaguesProfile().entrySet()) {
                    results.add(entry.getKey() + " " + "League" + ":" + "\n" + entry.getValue());
                }
            } else if (categoryName.contains("Team")) {
                for (Map.Entry<String, String> entry : search.getAllTeamsProfile().entrySet()) {
                    results.add (entry.getKey() + ":" + "\n" + entry.getValue());
                }
            } else if (categoryName.contains("Referee")) {
                for (Map.Entry<String, String> entry : search.getAllRefereesProfile().entrySet()) {
                    results.add( entry.getKey() + ":" + "\n" + entry.getValue());
                }
            } else if (categoryName.contains("Player")) {
                for (Map.Entry<String, String> entry : search.getAllPlayersProfile().entrySet()) {
                    results.add( entry.getKey() + ":" + "\n" + entry.getValue());
                }
            } else if (categoryName.contains("Coach")) {
                for (Map.Entry<String, String> entry : search.getAllCoachesProfile().entrySet()) {
                    results.add( entry.getKey() + ":" + "\n" + entry.getValue());
                }
            }
        }
        return results;
    }

    public Search getSearch() {
        return search;
    }
}
//    /**
//     * useCase 2.5 - search by key word : search by name and search by category
//     *
//     * @param keyWord
//     */
//    public String searchByKeyWord(String keyWord) {
//        String result = searchByName(keyWord);
//        if (result == null) {
//            searchByCategory(keyWord);
//        }
//        return result;
//    }


