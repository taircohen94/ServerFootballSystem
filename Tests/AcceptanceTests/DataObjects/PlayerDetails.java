package AcceptanceTests.DataObjects;
import Users.EPlayerRole;

import java.util.Date;

public class PlayerDetails extends UserDetails{
    public EPlayerRole training;


    public PlayerDetails(String userName, String password, String firstName, String lastName, EPlayerRole training) {
        super(userName, password, firstName, lastName);
        this.training = training;
    }



    public EPlayerRole getTraining() {
        return training;
    }

    public void setTraining(EPlayerRole training) {
        this.training = training;
    }
}
