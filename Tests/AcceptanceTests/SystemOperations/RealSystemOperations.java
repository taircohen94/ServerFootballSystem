package AcceptanceTests.SystemOperations;

import AcceptanceTests.DataObjects.*;
import AssociationAssets.*;
import PoliciesAndAlgorithms.GamesAssigningPolicy;
import PoliciesAndAlgorithms.OneRoundGamesAssigningPolicy;
import PoliciesAndAlgorithms.RegularScorePolicy;
import PoliciesAndAlgorithms.ScoreTablePolicy2;
import System.FootballSystem;
import Model.*;
import Users.*;

import javax.security.auth.login.FailedLoginException;
import java.nio.file.Path;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

public class RealSystemOperations implements ISystemOperationsBridge{
    private final Model model;
    private static int randomNumberForUser = 0;
    private static int getRandomNumberForTeam = 0;

    public RealSystemOperations() {
        this.model = new Model();
        initDBAcordingToMain();
    }

    private void initDBAcordingToMain() {
        //Season
        Season season = new Season("2020");
        Season season1 = new Season("2021");
        FootballSystem.getInstance().addSeasonToDB(season);
        FootballSystem.getInstance().addSeasonToDB(season1);

        //League
        League league = new League("La Liga");
        League league1 = new League("liga");
        FootballSystem.getInstance().addLeagueToDB(league);
        FootballSystem.getInstance().addLeagueToDB(league1);
        FootballSystem.getInstance().getLeagueDB().getAllLeagues().get(league.getLeagueName()).addSeasonToLeague(season);
        FootballSystem.getInstance().getLeagueDB().getAllLeagues().get(league1.getLeagueName()).addSeasonToLeague(season1);

        //Field
        Field field = new Field("Blomfield","teal aviv",1000);
        Field field1 = new Field("Tedi","teal aviv",1000);
        FootballSystem.getInstance().addFieldToDB(field);
        FootballSystem.getInstance().addFieldToDB(field1);

        //TeamOwner
        FootballSystem.getInstance().signIn("4","4","tair","cohen");
        FootballSystem.getInstance().creatingTeamOwner("4","tair","cohen");
        TeamOwner tairTO = FootballSystem.getInstance().getTeamOwnerByUserName("4");

        //Referee
        FootballSystem.getInstance().signIn("1","1","lala","la");
        FootballSystem.getInstance().creatingReferee("1","la","laala", EReferee.MAIN);
        FootballSystem.getInstance().signIn("2","2","lala","la");
        FootballSystem.getInstance().creatingReferee("2","la","laala", EReferee.ASSISTANT);
        FootballSystem.getInstance().signIn("3","3","lala","la");
        FootballSystem.getInstance().creatingReferee("3","la","laala", EReferee.ASSISTANT);
        FootballSystem.getInstance().signIn("10","10","lala","la");
        FootballSystem.getInstance().creatingReferee("10","la","laala", EReferee.MAIN);

        //Team
        Team team1 = new Team(45,"team1",season,field,null,(TeamOwner)FootballSystem.getInstance().getFanByUserName("4"));
        Team team2 = new Team(55,"team2",season,field,null,(TeamOwner)FootballSystem.getInstance().getFanByUserName("4"));
        team1.addSeasonToTeam(season);
        team1.addSeasonToTeam(season1);
        team2.addSeasonToTeam(season);
        try {
            tairTO.addCoach(team1,season,"coach-2020","123","c","c", ETraining.CDiploma,ECoachRole.AssistantCoach);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        tairTO.addField(team1,season,"Camp-No1","tel-aviv",12222);
        tairTO.addTeamManager(team1,season,"TM-2020","123","la","la");
        tairTO.addPlayer(team1,season,"player-2020","123","la","la",new Date(17/10/1995),EPlayerRole.GoalKeeper);

        try {
            FootballSystem.getInstance().addTeamToDB(team1);
            FootballSystem.getInstance().addTeamToDB(team2);

        } catch (Exception e) {

        }
        // Date date = new Date(2020-1900,4,12);
        //default time zone
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //creating the instance of LocalDate using the day, month, year info
        LocalDate localDate = LocalDate.now();

        //local date + atStartOfDay() + default time zone + toInstant() = Date
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

        Time time =Time.valueOf(LocalTime.of(LocalTime.now().getHour()-1,LocalTime.now().getMinute()));
        Referee main = FootballSystem.getInstance().getRefereeByUseName("1");
        Referee side1 = FootballSystem.getInstance().getRefereeByUseName("2");
        Referee side2 = FootballSystem.getInstance().getRefereeByUseName("3");
        Game game = null;

        try {
            game = new Game(date,time,field,team1,team2,main,side1,side2,season,league);
            game.addEvent(EEventType.GOALHOST,"descriprion");
        } catch (Exception e) {
            e.printStackTrace();
        }
        main.addGame(game);
        side1.addGame(game);
        side2.addGame(game);
        FootballSystem.getInstance().addGameToDB(game);
        FootballSystem.getInstance().signIn("r","r","r","r");
        FootballSystem.getInstance().creatingRepresentativeFootballAssociation("r","r","r",null);

        HashMap<Integer,Game> games = new HashMap<>();
        games.put(123,game);
        HashMap <String,Team> teams = new HashMap<>();
        teams.put(team1.getName(),team1);
        teams.put(team2.getName(),team2);
        league.addSeasonToLeague(season); //creating Binder in league
        league.addSeasonToLeague(season1);
        league.addTeamsToLeague("2020",teams);
        league.addGamesToLeague("2020",games);
        SeasonLeagueBinder seasonLeagueBinder = season.getLeagueBinders().get("La Liga");
        FootballSystem.getInstance().addLeagueToDB(league);
        FootballSystem.getInstance().addLeagueToDB(league1);
        FootballSystem.getInstance().getLeagueDB().getAllLeagues().get(league.getLeagueName()).addSeasonToLeague(season);
        FootballSystem.getInstance().getGameDB().getAllGames().get(game.getGID()).getLeague().setAssigningPolicy("2021", new OneRoundGamesAssigningPolicy());
        FootballSystem.getInstance().getGameDB().getAllGames().get(game.getGID()).getLeague().setScoreTablePolicy("2021", new ScoreTablePolicy2());

    }
    private void initDB() {




    }

    @Override
    public UserDetails getNewRegisteredUserForTest() {
        UserDetails newRandomUser = new UserDetails("UserName"+randomNumberForUser+"","UserPassword"+randomNumberForUser,"UserFirstName"+randomNumberForUser,"UserLastName"+randomNumberForUser);
        randomNumberForUser++;
        model.signIn(newRandomUser.userName,newRandomUser.password,newRandomUser.firstName,newRandomUser.LastName);
        return newRandomUser;
    }

    @Override
    public boolean login(String username, String pw) {
        try {
            return model.login(username,pw);
        } catch (FailedLoginException e) {
            return false;
        }
    }

    @Override
    public TeamDetails getRegisteredTeamForTest() {
        TeamDetails teamDetails = new TeamDetails("team1","La Liga" , "2020", "Blomfield");
        return teamDetails;
    }

    @Override
    public CoachDetails getRegisteredCoachForTest() {
        CoachDetails coach = new CoachDetails("coach-2020","123","c","c", ETraining.CDiploma,ECoachRole.AssistantCoach);
        return coach;
    }

    @Override
    public FieldDetails getExistFieldForTest() {
        FieldDetails fieldDetails = new FieldDetails("Camp-No1","tel-aviv","12222");
        return fieldDetails;
    }

    @Override
    public PlayerDetails getRegisteredPlayerForTest() {
        PlayerDetails player = new PlayerDetails("player-2020","123","la","la",EPlayerRole.GoalKeeper);
        return player;
    }

    @Override
    public UserDetails getRegisteredTeamOwnerForTest() {
        UserDetails teamOwner = new UserDetails("4","4","tair","cohen");
        return teamOwner;
    }

    @Override
    public UserDetails getUnPrivilegeUserForTest() {
        UserDetails unPrivilege =new UserDetails("unPrivilege","unPrivilege");
        FootballSystem.getInstance().signIn(unPrivilege.userName,unPrivilege.password,"lala","la");
        return unPrivilege;
    }


    @Override
    public boolean register(String userName, String password, String firstName, String lastName) {
        return model.signIn(userName,password,firstName,lastName);
    }

    @Override
    public TeamDetails getNewRegisteredTeamForTest() {
        getRandomNumberForTeam++;
        String teamName = "RegisteredTeam"+ getRandomNumberForTeam;
        if(createNewTeam(teamName,"La Liga","2020","Blomfield")){
            return new TeamDetails(teamName,"La Liga","2020","Blomfield");
        }
        return null;
    }


    @Override
    public boolean createNewTeam(String name, String leagueName, String seasonYear, String fieldName) {
        //login first
        if(!login("4","4")){
            return false;
        }
        //TeamOwner create new team
        try {
            return model.createTeam(name,leagueName,seasonYear,fieldName);
        } catch (RecordException e) {
            return false;
        }
    }

    @Override
    public boolean createNewTeamWithotTeamOwner(String name, String leagueName, String seasonYear, String fieldName) {
        //login first
        if(!login("admin","admin")){
            return false;
        }
        //TeamOwner create new team
        try {
            return model.createTeam(name,leagueName,seasonYear,fieldName);
        } catch (RecordException e) {
            return false;
        }
    }

    @Override
    public boolean addEvent(String userName,String password, int gameID, EEventType eventType, int eventIndex, String description) {
        if(!login(userName,password)){
            return false;
        }

        try {
            return model.addEvent(gameID,eventType.toString(),description);
        } catch (RecordException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean updateEvent(String userName,String password, int gameID, EEventType eventType, int eventIndex, String description) {
        if(!login(userName,password)){
            return false;
        }
        try {
            return model.updateEvent(gameID,eventIndex,eventType.toString(),description);
        } catch (RecordException e) {
            return false;
        }
    }

    @Override
    public boolean editTeamManagerDetails(String userName, String password, String teamName, String seasonYear, String teamManagerUserName, String firstName, String lastName) {
        if(!login(userName,password)){
            return false;
        }
        try {
            model.editTeamManagerDetails(teamName,seasonYear,teamManagerUserName,firstName,lastName);
        } catch (Exception e) {
            return false;
        }
        System.out.println(FootballSystem.getInstance().getFanByUserName(teamManagerUserName).viewProfile());
        return true;
    }

    @Override
    public boolean editCoachDetails(String userName, String password, String teamName, String seasonYear, String userNamePlayer, String firstName, String lastName, String training, String role) {
        if(!login(userName,password)){
            return false;
        }
        try {
            model.editCoachDetails(teamName,seasonYear,userNamePlayer,firstName,lastName,training,role);
        } catch (Exception e) {
            return false;
        }
        System.out.println(FootballSystem.getInstance().getFanByUserName(userNamePlayer).viewProfile());
        return true;
    }

    @Override
    public boolean editFieldDetails(String userName, String password, String name, String seasonYear, String fieldName, String city, String capacity) {
        if(!login(userName,password)){
            return false;
        }
        try {
            model.editFieldDetails(name,seasonYear,fieldName,city,capacity);
        } catch (Exception e) {
            return false;
        }
        System.out.println(FootballSystem.getInstance().getFieldDB().getAllFields().get(fieldName).toString());
        return true;
    }

    @Override
    public boolean editPlayerDetails(String userName, String password, String teamName, String seasonYear, String playerUserName, String firstName, String lastName,String role) {
        if(!login(userName,password)){
            return false;
        }
        try {
            model.editPlayerDetails(teamName,seasonYear,playerUserName,firstName,lastName,role);
        } catch (Exception e) {
            return false;
        }
        System.out.println(FootballSystem.getInstance().getFanByUserName(playerUserName).viewProfile());
        return true;
    }

    @Override
    public boolean deleteEvent(String userName,String password, int gameID, EEventType eventType, int eventIndex, String description) {
        if(!login(userName,password)){
            return false;
        }
        try {
            FootballSystem.getInstance().getGameDB().getAllGames().get(gameID).addEvent(EEventType.GOALHOST,"descriprion");
            return model.removeEvent(gameID,eventIndex);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteEventAfterGameOver(String userName, String password, int gameID, EEventType eventType, int eventIndex, String description,int hoursBefore) {
        if(!login(userName,password)){
            return false;
        }
        try {
            FootballSystem.getInstance().getGameDB().getAllGames().get(gameID).addEvent(EEventType.GOALHOST,"descriprion");
            Time time =Time.valueOf(LocalTime.of(LocalTime.now().getHour()-hoursBefore,LocalTime.now().getMinute()));
            FootballSystem.getInstance().getGameDB().getAllGames().get(gameID).setTime(time);
            return model.removeEventAfterGameOver(gameID,eventIndex);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean editEventAfterGameOver(String userName, String password, int gameID, EEventType eventType, int eventIndex, String description,int hoursBefore) {
        if(!login(userName,password)){
            return false;
        }
        boolean bool = false;
        try {
            FootballSystem.getInstance().getGameDB().getAllGames().get(gameID).addEvent(EEventType.GOALHOST,"descriprion");
            Time time =Time.valueOf(LocalTime.of(LocalTime.now().getHour()-hoursBefore,LocalTime.now().getMinute()));
            FootballSystem.getInstance().getGameDB().getAllGames().get(gameID).setTime(time);
            bool = model.updateEventAfterGameOver(gameID,eventIndex,eventType.toString(),description);
        } catch (RecordException e) {
            return false;
        }
        return bool;
    }

    @Override
    public boolean exportReport(String userName, String password, int gameID, int hoursBefore, String path) {
        if(!login(userName,password)){
            return false;
        }
        try {
            FootballSystem.getInstance().getGameDB().getAllGames().get(gameID).addEvent(EEventType.GOALHOST,"descriprion");
            Time time =Time.valueOf(LocalTime.of(LocalTime.now().getHour()-hoursBefore,LocalTime.now().getMinute()));
            FootballSystem.getInstance().getGameDB().getAllGames().get(gameID).setTime(time);
            FootballSystem.getInstance().getGameDB().getAllGames().get(gameID).getLeague().setAssigningPolicy("2021", new OneRoundGamesAssigningPolicy());
            FootballSystem.getInstance().getGameDB().getAllGames().get(gameID).getLeague().setScoreTablePolicy("2021", new ScoreTablePolicy2());

            return model.exportGameReport(gameID,path+"\\"+gameID+".csv",""+gameID);
        } catch (Exception e) {
            return false;
        }
    }
}
