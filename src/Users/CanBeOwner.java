package Users;
public class CanBeOwner extends Fan {

    TeamOwner teamOwner;

    public CanBeOwner(String UID, String fName, String lName) {
        super(UID, fName, lName);
        this.teamOwner = null;
    }

    public CanBeOwner(String UID, String fName, String lName,TeamOwner teamOwner) {
        super(UID, fName, lName);
        this.teamOwner = teamOwner;
    }
}
