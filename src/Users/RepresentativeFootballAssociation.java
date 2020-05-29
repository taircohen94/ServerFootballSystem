package Users;

import AssociationAssets.*;
import Budget.AssociationBudget;
import Budget.TeamBudget;
import Model.RecordException;
import PoliciesAndAlgorithms.GamesAssigningPolicy;
import PoliciesAndAlgorithms.ScoreTablePolicy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import System.*;

import javax.naming.OperationNotSupportedException;
import javax.security.auth.login.FailedLoginException;

public class RepresentativeFootballAssociation extends Fan  {
    private GamesAssigningPolicy gamePolicy;
    private AssociationBudget associationBudget;
    private   ArrayList<String> notificationTeams = new ArrayList<>();
    //add notifications
    private HashMap<String/*teamName*/, Boolean> notificationTeamsExceedBudget;


    /**
     * Constructor
     * @param userName - Unique football association representative username
     * @param fName - The first name of the football association representative
     * @param lName -The last name of the football association representative
     */
    public RepresentativeFootballAssociation(String userName, String fName, String lName,GamesAssigningPolicy gamePolicy) {
        super(userName, fName, lName);
        this.gamePolicy = gamePolicy; // can be changed by the user later
        this.associationBudget = new AssociationBudget();
        this.notificationTeamsExceedBudget = new HashMap<>();
        // Write to the log
        Logger.getInstance().addActionToLogger("Representative Football Association was created. representative user name: "+userName);
    }

    /**
     * second constructor
     * @param userName-
     * @param fName-
     * @param lName-
     */
    public RepresentativeFootballAssociation(String userName, String fName, String lName){
    super(userName, fName, lName);
    this.associationBudget = new AssociationBudget();
    this.notificationTeamsExceedBudget = new HashMap<>();
    LocalDate date = LocalDate.now();
    LocalTime now = LocalTime.now();
    // Write to the log
    Logger.getInstance().addActionToLogger(date + " " + now + ": Representative Football Association was created. representative user name: "+userName);
}

    public void removeNotifications() {
        this.notificationTeams.clear();
    }



    /**
     * useCase #9.1 - Define new League
     * @param leagueName  - The name of the new league
     * @param games - The games in the league season
     * @param teams - The teams in the league season
     * @param scorePolicy - League score policy
     * @param gamePolicy  - League Game policy
     * @param year - The year's league season
     */
    public void addNewLeague(String leagueName, HashMap<Integer, Game> games, HashMap<String, Team> teams, ScoreTablePolicy scorePolicy, GamesAssigningPolicy gamePolicy, String year,
                             Season season) {
        if(season == null || leagueName == null || games==null || teams==null || scorePolicy==null ||gamePolicy==null || year==null){
            return;
        }
        League newLeague = new League(leagueName);
        newLeague.setScoreTablePolicy(year, scorePolicy);
        newLeague.setAssigningPolicy(year, gamePolicy);
        newLeague.addSeasonToLeague(season);
        setSeasonToLeague(newLeague, year, games, teams);
        FootballSystem.getInstance().addLeagueToDB(newLeague);
    }

    /**
     * useCase #9.2 - Define season to League by year
     * @param league-
     * @param year - the year of the season
     * @param games- the games in league season
     * @param teams - the teams in league season
     */
    public void setSeasonToLeague(League league, String year, HashMap<Integer, Game> games, HashMap<String, Team> teams) {
        if(league== null || year==null || games==null || teams==null ){
            return;
        }
        Season newSeason = new Season(year);
        league.addSeasonToLeague(newSeason);
        league.addTeamsToLeague(year, teams);
        league.addGamesToLeague(year, games);
    }
    /**
     * useCase #9.3 - nominate referee by sign in and send him an invitation to Login
     * @param fName - referee's first name
     * @param lName - referee's last name
     * @param training -  training's referee ID
     */
    public Referee nominateReferee(String fName, String lName, EReferee training) throws FailedLoginException {
        if(fName == null || lName == null || training == null){
            return null;
        }
        String password = String.valueOf(new Random().nextInt(1000000000));
        String userName = signInReferee(fName,lName,password);
        Referee referee =  (Referee) FootballSystem.getInstance().creatingReferee(userName,fName, lName,training);
        // Write to the log
        Logger.getInstance().addActionToLogger("Referee "+referee.getUserName()+ "was created"+".");
        referee.LoginInvitation(userName,password);
        return referee;
    }

    /**
     * @param fName-
     * @param lName-
     * @param password-
     * @return a unique username
     */
    public String signInReferee(String fName,String lName ,String password){
        String userName ="";
        Fan user = null;
        int counter = 1;
        while (user == null) {
            userName = fName + lName + counter;
            user = FootballSystem.getInstance().signIn(userName,password,fName,lName);
            counter++;
        }
        return userName;
    }

    /**
     * useCase #9.3 - remove referee
     * @param refereeToRemove - the referee we remove from the system
     */
    public void removeReferee(Referee refereeToRemove) {
        if(refereeToRemove == null){
           /*next Iteration- need to add hash map to additionalInfo that holds referee and list of games the referee judge.
             Check if he is a referee in any game, if so the remove will not be allowed */
                    return;
         }
        FootballSystem.getInstance().removeUser(refereeToRemove.getUserName());
        //Write to log
        Logger.getInstance().addActionToLogger("Referee "+refereeToRemove.getUserName()+ "was removed from the System");
    }

    /**
     * useCase #9.4 - assign 3 referees (1 main and 2 sides) to judge the given game in the given league and season
     * @param mainRef-
     * @param sideRef1-
     * @param sideRef2-
     * @param game-
     */
    public void assignReferees(Referee mainRef, Referee sideRef1, Referee sideRef2, Game game) throws Exception {
        if(mainRef== null || sideRef1==null || sideRef2==null || game == null)
          {
            return;
        }
        game.setMain(mainRef);
        game.setSide1(sideRef1);
        game.setSide2(sideRef2);
        /*next Iteration- need to add this referees to their hash map in additional info*/
    }

    /**
     * useCase #9.5 - define Score Table policy
     */
    public void SetScoreTablePolicy(ScoreTablePolicy policy, League league, Season season) throws UnsupportedOperationException {
        if(policy!=null && league!=null && season!=null){
            FootballSystem.getInstance().getLeagueDB().getAllLeagues().get(league.getLeagueName()).getSeasonBinders().get(season.getYear()).setScoreTablePolicy(policy);
        }
    }
    public ArrayList<String> getNotificationTeams() {
        return notificationTeams;
    }

    /**
     * useCase #9.6 - define assign game policy
     * can only set assigning policy if the current season hasn't started yet.
     */
    public void SetGamesAssigningPolicy(GamesAssigningPolicy policy, League league, Season season) throws OperationNotSupportedException {
        if(policy!=null && league!=null && season!=null){
            SeasonLeagueBinder binder = FootballSystem.getInstance().getLeagueDB().getAllLeagues().get(league.getLeagueName()).getSeasonBinders().get(season.getYear());
            if(binder == null) throw new OperationNotSupportedException("This combination of league and season doesn't exists");
            binder.setAssigningPolicy(policy);
        }
        else throw new OperationNotSupportedException("Incorrect season or league provided");

    }

    /**
     * useCase #9.7 - activate the Games Assigning
     */
    public void activateGamesAssigning(League league, Season season) throws Exception {
        if(league!=null && season!=null){
            //get the teams from the season binder
            HashMap<String,Team> teams = FootballSystem.getInstance().getLeagueDB().getAllLeagues().get(league.getLeagueName()).getSeasonBinders().get(season.getYear()).getTeams();
            if(teams.size() < 2) throw new RecordException("Less than 2 teams in this league, can't activate policy");
            //getting all refs in the system
            Map<String,Referee> refs = FootballSystem.getInstance().getRefereeMap();
            if(refs.size() == 0) throw new RecordException("No referees in the system, can't activate policy");
            SeasonLeagueBinder binder = FootballSystem.getInstance().getLeagueDB().getAllLeagues().get(league.getLeagueName()).getSeasonBinders().get(season.getYear());
            //calling for the assigning function
            HashMap<Integer, Game> games = binder.getAssigningPolicy().executePolicy(teams,refs,LocalDate.now(), season, league);
            //adding the games to the league
            binder.addGamesToLeague(games);
        }
    }

    /**
     * useCase #9.8
     * set rule for budget control by define threshHold for budget control rule
     */
    public void setTeamBudgetControlRules(Team team, Season season,double threshHold, TeamBudget teamBudget) {
        if(team == null || season == null || threshHold < 0){
            return;
        }
        teamBudget.setThreshHold(threshHold,this);
    }
//
//    /**
//     * This function called when the team exceeds its budget
//     * @param o - TeamBudget Observable
//     * @param teamName-
//     */
//    @Override
//    public void update(Observable o, Object teamName) {
//        notificationTeams.add((String) teamName);
//    }

    /**
     * useCase #9.9 - define Tutu intakes
     * @param TutuIntakes-
     */
    public void setAssociationBudgetTutuIntakes(double TutuIntakes ) {
        if(TutuIntakes < 0){
            return;
        }
        associationBudget.setTutuIntakes(TutuIntakes);
    }

    /**
     * useCase #9.9 - define registration fee
     * @param registrationFee-
     */
    public void setAssociationBudgetRegistrationFee(double registrationFee) {
        if(registrationFee < 0 ){
            return;
        }
        associationBudget.setRegistrationFee(registrationFee);
    }

    public void addNotification(String notification){
        this.notificationTeams.add(notification);
    }
    /**
     * useCase #9.9 - set salary User
     * @param user-
     * @param salaryUser-
     */
    public void setAssociationBudgetSalaries(Fan user, Double salaryUser){
        if(user == null || salaryUser < 0){
            return;
        }
        associationBudget.addSalary(user, salaryUser);
    }

    public HashMap<String, Boolean> getTeamsExceedBudget() {
        return notificationTeamsExceedBudget;
    }


    public GamesAssigningPolicy getAssigningPolicy() {
        return gamePolicy;
    }
}
