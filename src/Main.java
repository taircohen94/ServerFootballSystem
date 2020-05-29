
import AssociationAssets.*;
import Model.Model;
import Server.Server;
import Server.Strategies.ServerSender;
import Users.*;
import javafx.application.Application;
import javafx.stage.Stage;
import System.FootballSystem;
import java.sql.Time;
import java.util.Date;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Model myModel = new Model();
    }

    private static void initDB() {
        Season season = new Season("2020");
        Season season1 = new Season("2021");
        FootballSystem.getInstance().addSeasonToDB(season);
        FootballSystem.getInstance().addSeasonToDB(season1);
        League league = new League("La Liga");
        League league1 = new League("liga");
        FootballSystem.getInstance().addLeagueToDB(league);
        FootballSystem.getInstance().addLeagueToDB(league1);
        league.addSeasonToLeague(season);
        Field field = new Field("Blomfield","teal aviv",1000);
        Field field1 = new Field("Tedi","teal aviv",1000);
        FootballSystem.getInstance().addFieldToDB(field);
        FootballSystem.getInstance().addFieldToDB(field1);
        FootballSystem.getInstance().signIn("4","4","tair","cohen");
        TeamOwner tairTO = (TeamOwner) FootballSystem.getInstance().creatingTeamOwner("4","tair","cohen");
        FootballSystem.getInstance().signIn("1","1","lala","la");
        FootballSystem.getInstance().creatingReferee("1","la","laala", EReferee.MAIN);
        FootballSystem.getInstance().signIn("2","2","lala","la");
        FootballSystem.getInstance().creatingReferee("2","la","laala", EReferee.ASSISTANT);
        FootballSystem.getInstance().signIn("3","3","lala","la");
        FootballSystem.getInstance().creatingReferee("3","la","laala", EReferee.ASSISTANT);

        Team team1 = new Team(45,"team1",season,field,null,(TeamOwner)FootballSystem.getInstance().getFanByUserName("4"));
        Team team2 = new Team(55,"team2",season,field,null,(TeamOwner)FootballSystem.getInstance().getFanByUserName("4"));
        team1.addSeasonToTeam(season);
        team1.addSeasonToTeam(season1);
        team2.addSeasonToTeam(season);
        tairTO.addCoach(team1,season,"coach-2020","123","c","c", ETraining.CDiploma,ECoachRole.AssistantCoach);
        tairTO.addField(team1,season,"Camp-No1","tel-aviv",12222);
        tairTO.addField(team1,season,"Camp-No2","tel-aviv",12222);
        tairTO.addTeamManager(team1,season,"TM-2020","123","la","la");
        tairTO.addPlayer(team1,season,"player-2020","123","la","la",new Date(17/10/1995),EPlayerRole.GoalKeeper);

        try {
            FootballSystem.getInstance().addTeamToDB(team1);
            FootballSystem.getInstance().addTeamToDB(team2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Date date = new Date(2020-1900,4,12);
        Time time = new Time(14,40,0);
        Referee main = FootballSystem.getInstance().getRefereeByUseName("1");
        Referee side1 = FootballSystem.getInstance().getRefereeByUseName("2");
        Referee side2 = FootballSystem.getInstance().getRefereeByUseName("3");
        Game game = null;

        try {
            game = new Game(date,time,field,team1,team2,main,side1,side2,season,league);
        } catch (Exception e) {
            e.printStackTrace();
        }
        main.addGame(game);
        FootballSystem.getInstance().addGameToDB(game);
    }

    public static void main(String[] args) {
        initDB();
        StartingServer();
        launch(args);
    }

    private static void StartingServer() {
        Server server = new Server(
                5400,
                1000,
                new ServerSender()
                /*new ServerSender()*/
        );
        server.start();
        StartCLI();
        server.stop();
    }

    private static void StartCLI(){
        System.out.println("Server started!");
        System.out.println("Enter 'exit' to close server.");
        Scanner reader = new Scanner(System.in);

        do
        {
            System.out.print(">>");
        } while (!reader.next().toLowerCase().equals("exit"));
    }

}
