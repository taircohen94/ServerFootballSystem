package Users;
import AssociationAssets.AdditionalInfo;
import java.util.ArrayList;
import java.util.List;
import System.*;
/**
 *A coach is a person who guides an athlete or group of athletes,
 * advises them and manages their actions as they prepare for a sporting
 * competition, and while they are actually participating.
 * The coach is responsible for transferring training before the games.
 *
 * @ Written by Yuval Ben Eliezer
 */
public class Coach extends CanBeOwner {

    private ETraining training;
    private ECoachRole role;
    private CoachPageEditor myPage;
    private List<AdditionalInfo> myAdditionalInfo;

    /**
     * @param userName - Unique user name
     * @param fName - First name of the coach
     * @param lName - Last name of the coach
     * @param training - Coach type of training. It could be:
     *                 CDiploma, UEFAA, UEFAB, UEFAPro
     * @param role - The role of the coach. It could be:
     *             GoalkeeperCoach, HeadCoach, AssistantCoach, YouthCoach
     * When a coach is created, a personal page is created for him.
     */
    public Coach(String userName, String fName, String lName, ETraining training, ECoachRole role) {
        super(userName, fName, lName);
        this.training = training;
        this.role = role;
        this.myPage = new CoachPageEditor(fName,lName,role,training);
        this.myAdditionalInfo = new ArrayList<>();
        Logger.getInstance().addActionToLogger("Coach created, user name: "+ userName);
    }

    /**
     * @param fan - Getting a fan and a builder copying his fields
     * @param training - Coach type of training. It could be:
     *                 CDiploma, UEFAA, UEFAB, UEFAPro
     * @param role - The role of the coach. It could be:
     *             GoalkeeperCoach, HeadCoach, AssistantCoach, YouthCoach
     * When a coach is created, a personal page is created for him.
     */
    public Coach(Fan fan, ETraining training, ECoachRole role) {
        super(fan.getUserName(), fan.getfName(), fan.getlName());
        this.training = training;
        this.role = role;
        this.myPage = new CoachPageEditor(fan.getfName(), fan.getlName(),role,training);
        this.myAdditionalInfo = new ArrayList<>();
        Logger.getInstance().addActionToLogger("Coach created, user name: "+ fan.getUserName());
    }

    /**
     * @param canBeOwner - Getting a CanBeOwner and a builder copying his fields
     * @param training - Coach type of training. It could be:
     *                 CDiploma, UEFAA, UEFAB, UEFAPro
     * @param role - The role of the coach. It could be:
     *             GoalkeeperCoach, HeadCoach, AssistantCoach, YouthCoach
     * When a coach is created, a personal page is created for him.
     */
    public Coach(CanBeOwner canBeOwner, ETraining training, ECoachRole role) {
        super(canBeOwner.getUserName(), canBeOwner.getfName(), canBeOwner.getlName());
        this.training = training;
        this.role = role;
        this.myPage = new CoachPageEditor(canBeOwner.getfName(), canBeOwner.getlName(),role,training);
        this.myAdditionalInfo = new ArrayList<>();
        Logger.getInstance().addActionToLogger("Coach created, user name: "+ canBeOwner.getUserName());

    }

    public List<AdditionalInfo> getMyAdditionalInfo() {
        return myAdditionalInfo;
    }

    /**
     * The position of the coaches varies according to the team and the season
     * @param additionalInfo - This section contains details about the team manager,
     *                      team, team owner, team players, team coach and season.
     */
    public void addAdditionalInfo(AdditionalInfo additionalInfo){
        if(additionalInfo != null){
            this.myAdditionalInfo.add(additionalInfo);
            Logger.getInstance().addActionToLogger("AdditionalInfo added to coach, user name: "+ getUserName());

        }

    }

    /**
     * Coaches can upload content to their personal page.
     * The coach uploads verbal content and that content goes up
     * to his personal page.
     *
     * # use case 5.2
     *
     * @param feed verbal content
     */
    public void addFeedToMyPage(String feed){
        if(feed != null) {
            this.myPage.addFeedToMyPage(feed);
        }
    }

    /**
     * Coaches can remove content from their personal page.
     * # use case 5.2
     * @param feed Verbal content
     */
    public void removeFeedFromMyPage(String feed){
        if(feed != null) {
            this.myPage.removeFeedFromMyPage(feed);
        }
    }

    public APageEditor getMyPage() {
        return myPage;
    }

    public ETraining getTraining() {
        return training;
    }

    /**
     * Coach can change his type of training.
     * The training also update in his personal page.
     * @param training - Coach type of training. It could be:
     *                  CDiploma, UEFAA, UEFAB, UEFAPro
     */
    public void setTraining(ETraining training) {
        if( training != null){
                this.training = training;
                this.myPage.setTraining(training);
            Logger.getInstance().addActionToLogger("Coach training has changed, user name: "+ getUserName()+" training: "+training);

        }
    }

    public ECoachRole getRole() {
        return role;
    }

    /**
     * Update the coach's role.
     * The coach's role is also updated on his personal page.
     * use case #5.1
     * @param role The role of the coach. It could be:
     *             GoalkeeperCoach, HeadCoach, AssistantCoach, YouthCoach
     */
    public void setRole(ECoachRole role) {
        if(role != null) {
            this.role = role;
            this.myPage.setRole(role);
            Logger.getInstance().addActionToLogger("Coach role has changed, user name: "+ getUserName()+" role: "+role);

        }
    }

    /**
     * Coach can change his personal page.
     * # use case 5.1
     * @param myPage - Personal page.
     */
    public void setMyPage(CoachPageEditor myPage) {
        if(myPage != null){
            this.myPage = myPage;
        }
    }

    /**
     * This function changes the first name in both the coach and his personal page
     * @param fName - first name
     */
    @Override
    public void setfName(String fName) {
        if(fName != null) {
            super.setfName(fName);
            this.myPage.setMyFirstName(fName);
        }
    }

    /**
     * This function changes the last name in both the coach and his personal page
     * @param lName - last name
     */
    @Override
    public void setlName(String lName) {
        if(lName!= null) {
            super.setlName(lName);
            this.myPage.setMyLastName(lName);
        }
    }

    /**
     * With this function you can view coach information.
     * Provides a brief description of the actor,
     * his full name, his training and his role.
     * # use case 2.4 - You can view details about the coaches through
     * this function as well as through the personal pages of the coaches.
     */
    @Override
    public String viewProfile() {
        return super.viewProfile() + ", I am a football coach, " +
                ", My training is " + this.training +
                ", My role is " + this.role;
    }
}
