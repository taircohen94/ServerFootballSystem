package IntegrationTests;

import AssociationAssets.Field;
import AssociationAssets.Season;
import AssociationAssets.Team;
import Budget.TeamBudget;
import Users.TeamOwner;

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
