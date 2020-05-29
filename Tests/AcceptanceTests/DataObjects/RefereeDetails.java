package AcceptanceTests.DataObjects;

import AssociationAssets.Game;
import Users.EReferee;

import java.util.ArrayList;
import java.util.List;

public class RefereeDetails extends UserDetails{
    public List<Game> myGames;
    public EReferee training;

    public RefereeDetails(String userName, String password, String firstName, String lastName, EReferee training) {
        super(userName, password, firstName, lastName);
        this.myGames = new ArrayList<>();
        this.training = training;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Game> getMyGames() {
        return myGames;
    }

    public void setMyGames(List<Game> myGames) {
        this.myGames = myGames;
    }

    public EReferee getTraining() {
        return training;
    }

    public void setTraining(EReferee training) {
        this.training = training;
    }

    public boolean addGame(Game game){
        if(game != null){
            this.myGames.add(game);
            return true;
        }
        return false;
    }


}
