package IntegrationTests;

import Users.EPlayerRole;
import Users.Player;

import java.util.Date;

public class PlayerStub extends Player {
    int selector;
    public PlayerStub(String userName, String fName, String lName, Date bDate, EPlayerRole role,int selector) {
        super(userName, fName, lName, bDate, role);
        this.selector = selector;

    }

}
