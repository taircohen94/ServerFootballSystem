package AcceptanceTests;

import AssociationAssets.*;
import Budget.TeamBudget;
import PoliciesAndAlgorithms.RegularScorePolicy;
import PoliciesAndAlgorithms.SimpleGamesAssigningPolicy;
import Users.*;
import System.Logger;
import System.FootballSystem;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Acceptance {
    Fan fan;
    Fan fan2;
    Fan fan3;
    Fan fan4;
    Fan fanGuest;
    Guest guest;
    Player player;
    Player player2;
    Coach coach;
    Coach coach2;
    TeamManager teamManager;
    TeamManager teamManager2;
    TeamOwner teamOwner;
    TeamOwner teamOwner2;
    TeamOwner teamOwner3;
    Logger logger;
    SystemManager systemManager;
    FootballSystem footballSystem;
    RepresentativeFootballAssociation rep;
    Team team1;
    Team team2;
    Team team3;
    Field field;
    Field field2;
    TeamBudget teamBudget;
    TeamBudget teamBudget2;
    League league1;
    League league2;
    Season season;
    RegularScorePolicy regularScorePolicy;
    SimpleGamesAssigningPolicy simpleGamesAssigningPolicy;
    Referee mainRef;
    Referee sideRef1;
    Referee sideRef2;
    Game game;
    Game game2;
    Time time;
    Time time2;
    HashMap<Integer,Game> games;
    HashMap<String,Team> teams;







    public static void main(String[] args) throws Exception {
        System.out.println("d");
        Acceptance main =  new Acceptance();
        main.accptanceTesting();
    }



    public void accptanceTesting() throws Exception {
        logger = Logger.getInstance();
        footballSystem = FootballSystem.getInstance();
        systemManager = new SystemManager("manager1","Avi","Ron");
        footballSystem.addSystemManager(systemManager);
        simpleGamesAssigningPolicy = new SimpleGamesAssigningPolicy();
        footballSystem.creatingRepresentativeFootballAssociation("king_of_football","Simha","Rif",simpleGamesAssigningPolicy);
        rep = footballSystem.getRepresentativeFootballAssociationByUseName("king_of_football");
        fan = footballSystem.signIn("moshe37","1234567","Moshe", "Zak");
        fan2 = footballSystem.signIn("messi10","12345678","Leo", "Messi");
        fan3 = footballSystem.signIn("vered8","888888","Idan","Vered");
        fan4 = footballSystem.signIn("zangooo","888888","Shimi","Zangi");
        footballSystem.creatingReferee("moshe37","Moshe","Zak",EReferee.MAIN);
        guest = new Guest();
        regularScorePolicy = new RegularScorePolicy();
        Date date = new Date(1987,12,4);
        field = footballSystem.createField("Teddy","Jerusalem",29000);
        field2= footballSystem.createField("Terner","Beer Sheva",19000);
        player = (Player) footballSystem.creatingPlayer("messi10","Leo","Messi",date,EPlayerRole.Forward);
        season = new Season("2020");
        league1 = new League("Israeli League");
        coach = (Coach) footballSystem.creatingCoach("bus123","Jose","Morinuo",ETraining.UEFAPro,ECoachRole.HeadCoach);
        teamOwner = (TeamOwner) footballSystem.creatingTeamOwner("ETgoHome","Eli","Tabib");
        teamOwner2 = (TeamOwner) footballSystem.creatingTeamOwner("hogeg$$","Moshe","Hogeg");
        teamOwner3 = (TeamOwner) footballSystem.creatingTeamOwner("ohana11","Eli","Ohana");
        teamManager = (TeamManager)footballSystem.creatingTeamManager("Menimo","Eli","Gutman");
        mainRef = (Referee) footballSystem.creatingReferee("yefet77","Alon","Yefet",EReferee.MAIN);
        sideRef1 = (Referee) footballSystem.creatingReferee("refet77","Mosh","Ben Ari",EReferee.ASSISTANT);
        sideRef2 = (Referee) footballSystem.creatingReferee("lefet77","Liran","Liani",EReferee.MAIN);
        games = new HashMap<>();
        teams = new HashMap<>();
        teamBudget = new TeamBudget(team1,season);
        teamBudget2 = new TeamBudget(team2,season);
        team1= new Team(1,"Barcelona", season, field2,teamBudget,teamOwner);
        team2= new Team(2,"Beitar Jerusalem", season, field,teamBudget,teamOwner2);
        teams.put(team1.getName(),team1);
        teams.put(team2.getName(),team2);
        team1.addSeasonToTeam(season);
        team2.addSeasonToTeam(season);
        time = new Time(13,30,0);
        game = new Game(new Date(2020,4,14),time,field2,team1,team2,mainRef,sideRef1,sideRef2,season,league1);
        game2 = new Game(new Date(2020,10,24),time,field,team2,team1,mainRef,sideRef2,sideRef1,season,league1);
        games.put(1,game);
        games.put(2,game2);
        regularScorePolicy = new RegularScorePolicy();
        simpleGamesAssigningPolicy = new SimpleGamesAssigningPolicy();
        // TODO: 18/04/2020  in the next line we want the function to connect the created league to the correct season (which is inside the DB). need to use the functions inside league to do so.
        rep.addNewLeague("Israeli League",games,teams, regularScorePolicy,simpleGamesAssigningPolicy,season.getYear(),season);
        league1 =  footballSystem.getLeagueDB().getAllLeagues().get("Israeli League");
        boolean temp;

        System.out.println("\n* * * * * * *Use case 2.2 of registration of a guest * * * * * * *");
        fanGuest= guest.signInGuest("alon67","87654321","Alon","G");
        if(fanGuest != null) System.out.println("Sign in succeeded");
        else System.out.println("###Test Failed! \nSign in failed");

        System.out.println("\n* * * * * * *Use case 2.4 of viewing information * * * * * * *");
        // TODO: 18/04/2020 search is by user name rather than name
        String info = fan2.searchByName("refet77");
        if(info != null) System.out.println("information found: " + info);
        info = fan2.searchByName("amit");
        if(info != null) System.out.println("###Test Failed! \n information found although not exist");
        else System.out.println("no information found (Test Passed)");
        System.out.println("\n* * * * * * *Use case 3.1 of logout by fan and login by fan * * * * * * *");
        fan.logout();
        if(fan.getStatus() == EStatus.OFFLINE)System.out.println("log out succeeded");
        else System.out.println("###Test Failed! \nlog out failed");
        fan.logInGuest("moshe37","wrong");
        if(fan.getStatus() == EStatus.ONLINE)System.out.println("###Test Failed! \nlog in succeeded with wrong password");
        else System.out.println("log in failed as expected (wrong password)\n");
        fan.logInGuest("moshe37","1234567");
        if(fan.getStatus() == EStatus.ONLINE)System.out.println("log in succeeded ");
        else System.out.println("###Test Failed! \nlog in failed,user is still offline after log in");

        System.out.println("\n* * * * * * *Use case 3.2 Fan subscribes to a personal page* * * * * * *");
        fan.subscribePersonalPage(player.getMyPage());
        System.out.println("The last logger event is: " +logger.getActionLog().get(logger.getActionLog().size() -1));

        System.out.println("\n* * * * * * *Use case 3.3 Fan subscribes to a game* * * * * * *\n not done yet");
        fan3.subscribeGames(game);

        System.out.println("\n* * * * * * *Use case 3.4 Fan reports wrong information* * * * * * *");
        fan.submitComplain("I hate this app so much! losers! Messi didn't born in that day!!");
        System.out.println("complaint submitted");

        System.out.println("\n* * * * * * *Use case 3.5 Fan watch his history of search* * * * * * *\nHistory found:");

        List<String> history = fan2.getSearchHistory();
        for (int i = 0; i < history.size(); i++) {
            System.out.println(history.get(i));
        }


        System.out.println("\n* * * * * * *Use case 6.1 of adding a player by team owner. first add when player already exist* * * * * * *");
        //this should print that user messi10 is already a player
        teamOwner.addPlayer(team1,season,"messi10","12345678","Leo","Messi",date,EPlayerRole.Forward);
        System.out.println("\n* * * * * * *Use case 6.1 of adding a player by team owner . now player does not exist as a player* * * * * * *");
        //should'nt print nothing
        teamOwner2.addPlayer(team2,season,"vered8","888888","Idan","Vered",date,EPlayerRole.Forward);
        //print idan vered username if he added
        System.out.println("Player List: " +team2.getAdditionalInfoWithSeasons().get(season.getYear()).getPlayers().toString());

        System.out.println("\n* * * * * * *Use case 6.2 of team owner nominating other team owner * * * * * * *");
        teamOwner2.nominateTeamOwner(team2,season,"ohana11");
        teamOwner3.addPlayer(team2,season,"atar9","9999999","Eliran","Atar",date,EPlayerRole.Forward);
        System.out.println("Player List(after new owner's player adding): " +team2.getAdditionalInfoWithSeasons().get(season.getYear()).getPlayers().toString());
        temp = teamOwner3.nominateTeamOwner(team2,season,"ETgoHome");
        if(temp)System.out.println("successful nomination");
        else System.out.println("###Test Failed! \nunsuccessful nomination");
        temp = teamOwner3.nominateTeamOwner(team2,season,"none");
        if(temp) System.out.println("###Test Failed! \nnomination with incorrect information did not fail as expected");

        System.out.println("\n* * * * * * *Use case 6.3 of team owner firing other team owner, this should cancel his nominations too * * * * * * *");
        teamOwner2.discardNominationForTeamOwner(team2,season,"ohana11");
        TeamOwner tmp =  team2.getAdditionalInfoWithSeasons().get(season.getYear()).findTeamOwner("ETgoHome");
        if(tmp == null)
            System.out.println("Team owner and his nominations removed!");
        else System.out.println("###Test Failed! \nTeam owner's nominations didn't remove." );

        System.out.println("\n* * * * * * *Use case 6.4 of team owner nominates team manager * * * * * * *");
        try{
            //problem in this function, maybe because the password sent is null
            temp = teamOwner2.nominateTeamManager(team2,season,teamManager.getUserName());
            if(temp)System.out.println("successful nomination");
            else System.out.println("###Test Failed! \nunsuccessful nomination");
        }
        catch (Exception e){
            System.out.println("###Test Failed! " + e.toString() );
        }
        System.out.println("\n* * * * * * *Use case 6.6 of team owner closes his team * * * * * * *");
        teamOwner2.closeTeam(team2,season);
        ETeamStatus eTeamStatus = team2.getIsActive();
        if(eTeamStatus == ETeamStatus.INACTIVE){
            System.out.println("Team closed");
            teamOwner2.removePlayer(team2,season,"vered8");
            if (team2.getAdditionalInfoWithSeasons().get(season.getYear()).getPlayers().size() == 2)
                System.out.println("removing player failed - which is a good thing because the team is closed.");
            // TODO: 18/04/2020  check somehow if managers got message about the closing
        }

        System.out.println("\n* * * * * * *Use case 6.6 of team owner reopens his team * * * * * * *\n not done yet");

        System.out.println("\n* * * * * * *Use case 6.6 of team owner add income or outcome * * * * * * *\n not done yet");


        System.out.println("\n* * * * * * *Use case 9.3 of Representative of the FA nominates a referre * * * * * * * ");
        mainRef = rep.nominateReferee(mainRef.getfName(),mainRef.getlName(),mainRef.getTraining());
        sideRef1 = rep.nominateReferee(sideRef1.getfName(),sideRef1.getlName(),sideRef1.getTraining());
        sideRef2 = rep.nominateReferee(sideRef2.getfName(),sideRef2.getlName(),sideRef2.getTraining());
        if(mainRef != null)System.out.println("Referees nominated");
        System.out.println("The last logger event is: " +logger.getActionLog().get(logger.getActionLog().size() -1));

        System.out.println("\n* * * * * * *Use case 9.4 of Representative of the FA nominates referees to specific league and season * * * * * * * ");
        // TODO: 18/04/2020 I think they meant to nominate to league and season and not to specific game
        rep.assignReferees(mainRef,sideRef1,sideRef2,game);


        if(game.getSide1() != null)System.out.println("Referees nominated to a specific game!");
        else System.out.println("###Test Failed! \nunsuccessful nomination");



        System.out.println("\n* * * * * * *Use case 10.2 Referee watches his game schdule * * * * * * * ");
        for (Game game:mainRef.viewAssignedGames()) {
            System.out.println(game.toString());
        }
        if(mainRef.viewAssignedGames().size() == 0) System.out.println("###Test Failed!\nno games to view!");

        System.out.println("\n* * * * * * *Use case 10.3 Referee updates event in the middle of a game * * * * * * * ");
        //need to create game that is still happening in the time this test is running.
        mainRef.addEventToAssignedGame(game.getGID(),EEventType.GOALHOST,"own goal by amit lol");
        System.out.println("\n* * * * * * *Use case 10.4 Referee updates til 5 hours after the game ended * * * * * * * ");
        //need to create game that ended less then 5 hours ago.

        System.out.println("\n* * * * * * *Use case 8.1 System manager closes team for ever * * * * * * *\nnot done yet ");
        systemManager.closeTeam(team1.getName());

        System.out.println("\n* * * * * * *Use case 8.2 System manager removes a user from the site  * * * * * * *\nnot done yet ");
        systemManager.removeFan("ddd");
        System.out.println("\n* * * * * * *Use case 8.3 System manager can read and respond to users complaints.  * * * * * * *\nnot done yet ");
        System.out.println("System manager is reading the first complaint: " + systemManager.getComplains().get(0));
        System.out.println("response option not done yet");
       /* Pair <String,Fan> complaint = new Pair<>(systemManager.getComplains().get(0),)
        systemManager.responseOnComplain("we are so sorry! asshole",new Pair<>());*/


    }

}
