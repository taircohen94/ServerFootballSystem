package IntegrationTests;

import AssociationAssets.ETeamStatus;
import AssociationAssets.Team;
import Users.EStatus;
import Users.Fan;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntegrationFanTest {
    Fan fan;
    TeamOwnerStub teamOwnerStub;
    SeasonStub seasonStub;
    FieldStub field;
    TeamBudgetStub teamBudgetStub;
    PlayerStub playerStub;

    @Before
    public void set_Up() {
        fan = new Fan("fan","name","last");
    }

    @Test
    public void basics(){
        assertEquals(fan.getStatus(), EStatus.ONLINE);


    }

}
