package IntegrationTests;

import Users.Fan;

public class FanStub extends Fan {
    int selector;
    /**
     * @param userName - Unique user name
     * @param fName    - First name of the fan
     * @param lName    - Last name of the fan
     */
    public FanStub(String userName, String fName, String lName,int selector) {
        super(userName, fName, lName);
        this.selector = selector;
    }
}
