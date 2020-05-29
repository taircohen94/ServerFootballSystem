package PoliciesAndAlgorithms;


import AssociationAssets.Game;
import AssociationAssets.League;
import AssociationAssets.Season;
import AssociationAssets.Team;
import Users.EReferee;
import Users.Referee;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.*;

public class OneRoundGamesAssigningPolicy extends GamesAssigningPolicy {



    /**
     * constructor
     */
    static int notPlayingIndex;
    static int gidCounter;


    public OneRoundGamesAssigningPolicy() {
        counterMainRef = 0;
        counterSideRef = 0;
        gidCounter = 1;
        notPlayingIndex = 0;
        gamesAssigned = false;
    }

    /**
     * this function returns games for the whole season.
     * @param teams the teams playing in this league and season
     * @param refs referees to assign to this season
     * @param startDate start date to the league
     * @param season season to assign to
     * @param league league to assign to
     * @return hashmap of games, the key is the game ID
     * @throws Exception in case of not enough referees or null parameters or if games already assigned.
     */
    public HashMap<Integer, Game> executePolicy(HashMap<String, Team> teams, Map<String, Referee> refs, LocalDate startDate, Season season, League league) throws Exception {
        if(teams == null || refs == null || startDate == null || season == null || league == null) throw new Exception("At least one parameter is wrong");
        if(gamesAssigned) throw new Exception("Games already assigned for this season");
        HashMap <Integer,Game> games = new HashMap<>();
        int refereeCount = refs.size();
        int mainRefereeCount = countMainReferees(refs);
        int teamsCount = teams.size();

        if(mainRefereeCount >= teamsCount/2) throw new Exception("There is not enough main referees, can't assign games");
        else if(refereeCount-mainRefereeCount < teamsCount) throw new Exception("There is not enough side referees, can't assign games");

        Map<String, Referee> mainReferees = getMainReferees(refs);
        Map<String, Referee> otherRefs = getOtherReferees(refs);
        int numberOfGames = nChooseTwo(teamsCount);
        int mainRefWithExtraGame = (numberOfGames % mainRefereeCount);
        int otherRefWithExtraGame = (numberOfGames*2 % (refereeCount-mainRefereeCount));
        //String[] of team names
        String[] teamName = getNamesArray(teams);
        boolean evenTeamNumber = (teamsCount % 2 == 0);
        int[][] combinations = getCombinations(teamsCount,numberOfGames);
        int weeks = 0;
        if(evenTeamNumber){
            int gidHelper = 1;
            //even team count
            for (int i = 0; i < numberOfGames/4; i++) {
                //for each week of games
                int[][] teamsOfGame = getMatchParticipants(teams,combinations,teamsCount/2);
                for (int j = 0; j <teamsCount/2; j++) {
                    //create all games of this round
                    Team host = teams.get(teamName[teamsOfGame[j][0]]);
                    Team guest = teams.get(teamName[teamsOfGame[j][1]]);
                    Referee mainRef = selectMainRef(mainReferees);
                    Referee sideRef1 = selectSideRef(otherRefs);
                    Referee sideRef2 = selectSideRef(otherRefs);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(Date.valueOf(startDate));
                    calendar.add(Calendar.DAY_OF_YEAR, weeks*7);
                    java.util.Date dateNew = calendar.getTime();
                    //first game between those teams
                    Game game = new Game(dateNew,new Time(21,0,0),host.getMainField(),host,guest,mainRef,sideRef1,sideRef2,season,league);
                    int gid = gidHelper++;
                    game.setGID(gid);
                    games.put(gidCounter,game);
                }

                weeks++;
            }

        }

        else{
            //odd team count
            int gidHelper = 1;
            for (int i = 0; i < (numberOfGames/((teamsCount-1)/2))/2; i++) {
                //for each week of games
                int[][] teamsOfGame = getMatchParticipants(teams,combinations,teamsCount/2);
                for (int j = 0; j <teamsCount/2; j++) {
                    //create all games of this round
                    Team host = teams.get(teamName[teamsOfGame[j][0]]);
                    Team guest = teams.get(teamName[teamsOfGame[j][1]]);
                    Referee mainRef = selectMainRef(mainReferees);
                    Referee sideRef1 = selectSideRef(otherRefs);
                    Referee sideRef2 = selectSideRef(otherRefs);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(Date.valueOf(startDate));
                    calendar.add(Calendar.DAY_OF_YEAR, weeks*7);
                    java.util.Date dateNew = calendar.getTime();
                    //first game between those teams
                    Game game = new Game(dateNew,new Time(21,0,0),host.getMainField(),host,guest,mainRef,sideRef1,sideRef2,season,league);
                    int gid = gidHelper++;
                    game.setGID(gid);
                    games.put(gidCounter,game);
                }

                weeks++;
            }
        }

        gamesAssigned = true;
        return games;
    }

    private Referee selectSideRef(Map<String, Referee> otherRefs) {
        Referee referee = null;
        int counter = 0;
        for (Map.Entry<String,Referee> entry:otherRefs.entrySet()) {
            if(counter == counterSideRef){
                counterSideRef++;
                if(counterSideRef >= otherRefs.size())
                    counterSideRef = 0;
                referee = entry.getValue();
                return referee;
            }
            counter++;
        }

        return referee;
    }

    /**
     * choosing one main ref at a time for the next game.
     * each referee is chose by his turn, referee can judge only one game in a round of games
     * @param mainReferees
     * @return
     */
    private Referee selectMainRef(Map<String, Referee> mainReferees) {
        int counter = 0;
        Referee referee = null;
        for (Map.Entry<String,Referee> entry:mainReferees.entrySet()) {
            if(counter == counterMainRef){
                counterMainRef++;
                if(counterMainRef >= mainReferees.size())
                    counterMainRef = 0;
                referee = entry.getValue();
                return referee;
            }
            counter++;
        }
        return referee;
    }


    /**
     * this function return for each round of games - the teams assigned for each game
     * the host and guest is randomly selected
     * @param teams
     * @param combinations
     * @param i
     * @return
     */
    private int[][] getMatchParticipants(HashMap<String, Team> teams, int[][] combinations, int i) {
        List<Integer> participantsList = new ArrayList<>();
        int[][] opponents = new int[teams.size()/2][];
        int games = 0;
        int host,guest;
        int index= 0;
        while(games < i){
            for (int j = 0; j < combinations.length; j++) {
                if(combinations[j] != null){
                    host = combinations[j][0];
                    guest = combinations[j][1];
                    if(!participantsList.contains(host) && !participantsList.contains(guest) && host != notPlayingIndex && guest != notPlayingIndex){
                        participantsList.add(host);
                        participantsList.add(guest);
                        int random = (int) (Math.random() * (2));
                        //random choose for host and guest order
                        if(random == 0)
                            opponents[index++] = new int[]{host,guest};
                        else
                            opponents[index++] = new int[]{guest,host};
                        combinations[j] = null;
                        games++;
                        if(games == i) break;
                    }
                }

            }
            if(games != i) notPlayingIndex++;
            if(notPlayingIndex == teams.size()) notPlayingIndex = 0;
        }
        notPlayingIndex++;
        if(notPlayingIndex >= teams.size()) notPlayingIndex = 0;
        return opponents;
    }

    /**
     * return all the combinations possible of the number, later used for team combinations for game assigning
     * @param count
     * @param numOfCombination
     * @return
     */
    private int[][] getCombinations(int count, int numOfCombination) {
        int[][] combinations = new int[numOfCombination][];
        int currIndex = 0;
        for (int i = 0; i < count; i++) {
            for (int j = i+1; j < count; j++) {
                combinations[currIndex++] = new int[]{i,j};
            }
        }
        return combinations;
    }

    /**
     * return array of team names
     * @param teams
     * @return
     */
    private String[] getNamesArray(HashMap<String, Team> teams) {
        String[] teamNames = new String[teams.size()];
        int i = 0;
        for (Map.Entry<String,Team> entry:teams.entrySet()) {
            teamNames[i++] = entry.getKey();
        }

        return teamNames;


    }

    /**
     * returns the value of teamCount chose 2, in order to calculate the number of games in the season
     * @param teamsCount
     * @return
     */
    private int nChooseTwo(int teamsCount) {
        int N = 1,tmp,nMinusTwo = 1;
        tmp = teamsCount;
        while(tmp > 1){
            N *= tmp--;
            if(teamsCount - 2 == tmp)
                nMinusTwo = tmp;
        }
        tmp = nMinusTwo;
        nMinusTwo = 1;
        while(tmp > 1){
            nMinusTwo *= tmp--;
        }
        if(N == 1) N = 0;

        return N/(2*nMinusTwo);
    }

    /**
     * return only not main referees Map
     * @param refs
     * @return
     */
    private Map<String, Referee> getOtherReferees(Map<String, Referee> refs) {
        Map<String, Referee> otherRefs = new HashMap<>();
        for (Map.Entry<String,Referee> entry:refs.entrySet()) {
            if(entry.getValue().getTraining() != EReferee.MAIN)
                otherRefs.put(entry.getKey(),entry.getValue());
        }
        return otherRefs;

    }

    /**
     * return only main referees Map
     * @param refs
     * @return
     */
    private Map<String, Referee> getMainReferees(Map<String, Referee> refs) {
        Map<String, Referee> mainRefs = new HashMap<>();
        for (Map.Entry<String,Referee> entry:refs.entrySet()) {
            if(entry.getValue().getTraining() == EReferee.MAIN)
                mainRefs.put(entry.getKey(),entry.getValue());
        }
        return mainRefs;
    }

    /**
     * count the number of main referees
     * @param refs
     * @return
     */
    private int countMainReferees(Map<String, Referee> refs) {
        int ans = 0;
        for (Map.Entry<String ,Referee> entry: refs.entrySet()) {
            if(entry.getValue().getTraining() == EReferee.MAIN){
                ans++;
            }
        }
        return ans;
    }
}



