package AcceptanceTests.DataObjects;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class GameDetails {
    public Date date;
    public Time time;
    public String fieldName;
    public String hostName;
    public String guestName;
    public String mainUserName;
    public String side1UserName;
    public String side2UserName;
    public String seasonName;
    public String leagueName;
    static int runningID = 0;
    public int GID;



    public GameDetails(Date date, Time time, String fieldName, String hostName, String guestName, String mainUserName, String side1UserName, String side2UserName, String seasonName, String leagueName) {
        this.GID = ++runningID;
        this.date = date;
        this.time = time;
        this.fieldName = fieldName;
        this.hostName = hostName;
        this.guestName = guestName;
        this.mainUserName = mainUserName;
        this.side1UserName = side1UserName;
        this.side2UserName = side2UserName;
        this.seasonName = seasonName;
        this.leagueName = leagueName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getMainUserName() {
        return mainUserName;
    }

    public void setMainUserName(String mainUserName) {
        this.mainUserName = mainUserName;
    }

    public String getSide1UserName() {
        return side1UserName;
    }

    public void setSide1UserName(String side1UserName) {
        this.side1UserName = side1UserName;
    }

    public String getSide2UserName() {
        return side2UserName;
    }

    public void setSide2UserName(String side2UserName) {
        this.side2UserName = side2UserName;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public int getGID() {
        return GID;
    }

    public void setGID(int GID) {
        this.GID = GID;
    }
}
