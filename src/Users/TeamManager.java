package Users;
import AssociationAssets.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import System.*;

/**
 * @ Written by Yuval Ben Eliezer
 */
public class TeamManager extends CanBeOwner {
    private List<AdditionalInfo> additionalInfoList;

    public TeamManager(String userName, String fName, String lName) {
        super(userName, fName, lName);
        this.additionalInfoList = new ArrayList<>();

        Logger.getInstance().addActionToLogger("Team Manager created, user name: "+ userName);

    }

    public List<AdditionalInfo> getAdditionalInfoList() {
        return additionalInfoList;
    }

    public void setAdditionalInfoList(List<AdditionalInfo> additionalInfoList) {
        this.additionalInfoList = additionalInfoList;
    }


    public void addAdditionalInfo(AdditionalInfo additionalInfoToSearch) {
    }
}


