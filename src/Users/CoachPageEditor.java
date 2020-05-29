package Users;
/**
 *Each coach has a personal page that they can upload and edit.
 *
 * @ Written by Yuval Ben Eliezer
 */
public class CoachPageEditor extends APageEditor {
    private ETraining training;

    /**
     * This personal page contains official content about the coach.
     * To this page the coach uploads updates as he chooses.
     * @param myFirstName - his first name.
     * @param myLastName - his last name.
     * @param training - Coach type of training. It could be:
     *                 CDiploma, UEFAA, UEFAB, UEFAPro
     * @param role - The role of the coach. It could be:
     *             GoalkeeperCoach, HeadCoach, AssistantCoach, YouthCoach
     */
    public CoachPageEditor(String myFirstName, String myLastName, Enum role, ETraining training) {
        super(myFirstName, myLastName, role);
        this.training=training;
    }

    public ETraining getTraining() {
        return training;
    }

    /**
     * Coach can change his type of training.
     * @param training - Coach type of training. It could be:
     *                  CDiploma, UEFAA, UEFAB, UEFAPro
     */
    public void setTraining(ETraining training) {
        this.training = training;
    }

    @Override
    public void addToMyPage(String feed) {

    }



    /**
     *This feature return the content about the coach that is on his personal
     * page and the content that the coach uploads to his site.
     * @return String with the content
     */
    @Override
    public String viewMyPersonalPage() {
        return "Welcome to my page! "+
                "My Name is: '" + super.getMyFirstName() + '\'' +
                " " + super.getMyLastName() + '\'' +
                "My Training is " + getTraining() +
                ". My role is " + super.getRole() +
                ", My Feed : " + super.getMyFeed() ;
    }
}
