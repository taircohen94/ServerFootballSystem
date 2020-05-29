package IntegrationTests;

import AssociationAssets.AdditionalInfo;
import Users.Player;
import Users.TeamOwner;

public class TeamOwnerStub extends TeamOwner {


    int selector;
    /**
     * The constructor for TeamOwner class.
     *
     * @param userName
     * @param firstName
     * @param lastName
     */
    public TeamOwnerStub(String userName, String firstName, String lastName,int selector) {
        super(userName, firstName, lastName);
    }
    public void setSelector(int selector) {
        this.selector = selector;
    }
    public boolean addPlayer(){
        if(selector ==1){

            return true;

        }
        return true;

    }
}
