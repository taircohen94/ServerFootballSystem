package IntegrationTests;

import BL.Users.Coach;
import BL.Users.ECoachRole;
import BL.Users.ETraining;

public class CoachStub extends Coach {
    int selector;
    public CoachStub(String userName, String fName, String lName, ETraining training, ECoachRole role,int selector) {
        super(userName, fName, lName, training, role);
        this.selector = selector;

    }
}
