package IntegrationTests;

import BL.Users.TeamManager;

public class TeamManagerStub extends TeamManager {
    int selector;
    public TeamManagerStub(String userName, String fName, String lName,int selector) {
        super(userName, fName, lName);
        this.selector = selector;
    }
}
