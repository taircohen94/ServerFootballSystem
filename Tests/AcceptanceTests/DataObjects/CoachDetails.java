package AcceptanceTests.DataObjects;

import BL.Users.ECoachRole;
import BL.Users.ETraining;

public class CoachDetails extends UserDetails {
    public ETraining training;
    public ECoachRole role;

    public CoachDetails(String userName, String password, String firstName, String lastName, ETraining training, ECoachRole role) {
        super(userName, password, firstName, lastName);
        this.role = role;
        this.training = training;
    }

    public ETraining getTraining() {
        return training;
    }

    public void setTraining(ETraining training) {
        this.training = training;
    }

    public ECoachRole getRole() {
        return role;
    }

    public void setRole(ECoachRole role) {
        this.role = role;
    }
}

