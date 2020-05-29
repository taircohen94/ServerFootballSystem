package AcceptanceTests.DataObjects;

public class TeamDetails {
    String name;
    String leagueName;
    String seasonYear;
    String fieldName;


    public TeamDetails(String name, String leagueName, String seasonYear, String fieldName) {
        this.name = name;
        this.leagueName = leagueName;
        this.seasonYear = seasonYear;
        this.fieldName = fieldName;
        this.fieldName = "Blomfield";

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(String seasonYear) {
        this.seasonYear = seasonYear;
    }

    public String getFieldName() {
        return fieldName;
    }



    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
