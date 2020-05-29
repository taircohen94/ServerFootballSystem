package IntegrationTests;

import AssociationAssets.Season;
import AssociationAssets.Team;
import Budget.TeamBudget;

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
