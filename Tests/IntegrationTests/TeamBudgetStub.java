package IntegrationTests;

import BL.AssociationAssets.Season;
import BL.AssociationAssets.Team;
import BL.Budget.TeamBudget;

public class TeamBudgetStub extends TeamBudget {
    int selector;
    /**
     * Constructor
     *
     * @param team
     * @param season
     */
    public TeamBudgetStub(Team team, Season season,int selector) {
        super(team, season);
        this.selector = selector;
    }
}
