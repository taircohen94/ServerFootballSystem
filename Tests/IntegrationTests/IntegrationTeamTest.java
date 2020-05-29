package IntegrationTests;
import static org.junit.Assert.*;

import AssociationAssets.ETeamStatus;
import AssociationAssets.Field;
import AssociationAssets.Season;
import AssociationAssets.Team;
import Users.ECoachRole;
import Users.EPlayerRole;
import Users.ETraining;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class IntegrationTeamTest {
    Team team;
    TeamOwnerStub teamOwnerStub;
    SeasonStub seasonStub;
    FieldStub field;
    TeamBudgetStub teamBudgetStub;
    PlayerStub playerStub;

    @Before
    public void set_Up() {
        seasonStub = new SeasonStub("2020");
        field = new FieldStub("field","city",100);
        teamOwnerStub = new TeamOwnerStub("teamowner","name","lastName",1);
        team = new Team(1,"teamName",seasonStub,field,null,teamOwnerStub);
    }

    @Test
    public void basics(){
        assertEquals(team.getIsActive(), ETeamStatus.ACTIVE);
        assertEquals(team.getFields().size(),1);
        assertEquals(team.getName(),"teamName");
        assertEquals(team.getTeamOwner(),teamOwnerStub);
    }


    @Test
    public void fieldTest(){
        Field field1 = new Field("teddy","jerus",1000022);
        team.addField(field1);
        assertEquals(team.getFields().size(),2);
        team.setMainField(team.getFields().get("teddy"));
        assertEquals(team.getMainField(),field1);
        assertEquals(team.getMainField().getCapacity(),1000022);
    }
    @Test
    public void findPlayer(){
        playerStub = new PlayerStub("usernamep","fname","lname",null, EPlayerRole.GoalKeeper,1);
        teamOwnerStub.addPlayer(team,seasonStub,"usernamef","11111","fname","lname",null,EPlayerRole.GoalKeeper);



    }


}
