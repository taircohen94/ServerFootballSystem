package AssociationAssets;
import System.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class Score represents a game's score, including the total points for each group.
 */
public class Score {

    //region Fields
    int goalsHost;
    int goalsGuest;
    //endregion


    public Score() {
        /*LocalDate date = LocalDate.now();
        LocalTime now = LocalTime.now();
        Logger.getInstance().addActionToLogger(date + " " + now + " : Score was created.");
        */
        this.goalsHost = 0;
        this.goalsGuest = 0;
    }

    public Score(int goalsHost, int goalsGuest) {
        /*
        LocalDate date = LocalDate.now();
        LocalTime now = LocalTime.now();
        Logger.getInstance().addActionToLogger(date + " " + now + " : Score "+goalsHost + ":" + goalsGuest + " was created.");
        */
        this.goalsHost = goalsHost;
        this.goalsGuest = goalsGuest;

    }

    //region Setters & Getters
    public int getGoalsHost() {
        return goalsHost;
    }

    public void setGoalsHost(int goalsHost) {
        this.goalsHost = goalsHost;
    }

    public int getGoalsGuest() {
        return goalsGuest;
    }

    public void setGoalsGuest(int goalsGuest) {
        this.goalsGuest = goalsGuest;
    }
    //endregion


    @Override
    public String toString() {
        return "Score= {" +
                "Host=" + goalsHost +
                ", Guest=" + goalsGuest +
                '}';
    }
}

