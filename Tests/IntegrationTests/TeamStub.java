package IntegrationTests;

import BL.AssociationAssets.Field;
import BL.AssociationAssets.Season;
import BL.AssociationAssets.Team;
import BL.Budget.TeamBudget;
import BL.Users.TeamOwner;

public class TeamStub extends Team {
    public void setSelector(int selector) {
        this.selector = selector;
    }

    int selector;
    public TeamStub(int TID, String name, Season currentSeason, Field mainField, TeamBudget budget, TeamOwner teamOwner,int selector) {
        super(TID, name, currentSeason, mainField, budget, teamOwner);
        this.selector = selector;
    }


}
