package Users;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import AssociationAssets.EEventType;
import AssociationAssets.EScheduleOrPlaceEvent;
import AssociationAssets.Game;
import System.*;

/**
 * A fan of a sports club, national team, team or athlete,
 * is a fan of that club, group or athlete.
 *
 * @ Written by Yuval Ben Eliezer
 */
public class Fan extends Guest implements IFan ,Serializable {

    private String userName;
    private String fName;
    private String lName;
    private EStatus status;
    private HashMap<String,EEventType> pendingGameNotifications;
    private HashMap<String,APageEditor> pendingPageNotifications;
    private HashMap<String, EScheduleOrPlaceEvent> pendingScheduleNotifications;
    private List<String> searchHistory;

    /**
     * @param userName - Unique user name
     * @param fName - First name of the fan
     * @param lName - Last name of the fan
     * When a fan is created his status is ONLINE
     */
    public Fan(String userName, String fName, String lName) {
        this.userName = userName;
        this.fName = fName;
        this.lName = lName;
        this.searchHistory = new ArrayList<>();
        this.pendingGameNotifications = new HashMap<>();
        this.pendingPageNotifications = new HashMap<>();
        this.pendingScheduleNotifications = new HashMap<>();
        this.status = EStatus.ONLINE;
    }

    /**
     * When a fan leaves the system, a fan's status changes to offline
     *
     * # use case 3.1
     */
    public void logout() {
        this.status = EStatus.OFFLINE;
    }

    /**
     *  With this function you can view fan information.
     * @return - His full name, status and search history.
     */
    public String viewProfile() { //useCase 3.6
        return toString();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if(userName!=null) {
            this.userName = userName;
        }
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        if(fName != null){
            this.fName = fName;
        }
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        if(lName != null){
            this.lName = lName;
        }
    }

    public EStatus getStatus() {
        return status;
    }

    /**
     * This function changes the player's status in the system
     * if the player had notifications while offline, show them to him when online.
     * @param status - It could be:
     *      *             ONLINE , OFFLINE
     */
    public void setStatus(EStatus status) {
        boolean wasOffline = false;
        if(this.status == EStatus.OFFLINE) wasOffline = true;
        if(status != null) {
            this.status = status;
            //checking if this fan was offline and now became online
            if (status == EStatus.ONLINE && wasOffline) {
                if (pendingGameNotifications.size() != 0) {
                    for (Map.Entry<String, EEventType> entry : pendingGameNotifications.entrySet()) {
                        // TODO: 29/04/2020 show some pop up message to the user about each notification
                        System.out.println(entry.getKey());
                    }
                }
                if (pendingPageNotifications.size() != 0) {
                    for (Map.Entry<String, APageEditor> entry : pendingPageNotifications.entrySet()) {
                        // TODO: 29/04/2020 same for page updates
                        System.out.println(entry.getKey());
                    }
                }
                //clear notifications after the user got them
                pendingGameNotifications.clear();
                pendingGameNotifications.clear();
            }

        }
    }

    /**
     * With this function you can view fan information.
     * Provides a brief description of the player,
     * His full name, status and search history.
     *
     * # use case 3.6
     */
    @Override
    public String toString() {
        return "My name is" +
                ": " + fName + '\'' +
                " " + lName + '\'' +
                ", my status is" + status +
                ", my search History is " + searchHistory ;
    }

    /**
     *A fan can sign up to track personal pages
     * With this function, a fan can sign up to track player and coach pages
     *
     * # use case 3.2
     *
     * @param pageEditor - The page the fan wants to sign up for
     */
    public void subscribePersonalPage(APageEditor pageEditor){//useCase 3.2
        pageEditor.register(this);
    }

    /**
     * A fan can delete a subscription to track personal pages
     * In this function, a fan can delete tracker of player and coach pages
     *
     * # use case 3.2
     *
     * @param pageEditor - The page the fan wants to stop following
     */
    public void removeRegisterFromPersonalPage(APageEditor pageEditor){//useCase 3.2
        pageEditor.delete(this);
    }

    public void subscribeTeamPersonalPage() {//useCase 3.2
        // TODO: 12/04/2020 Ask Alon&Amit about team page
    }

    public void update(APageEditor pageEditor,String feed){}

    /**
     *A fan can sign up to track games
     * With this function, a fan can sign up to games
     *
     * # use case 3.3
     *
     * @param game - The game the fan wants to sign up for
     */
    public void subscribeGames(Game game){//useCase 3.2
        game.register(this);
    }

    /**
     * A fan can delete a subscription to track game
     * In this function, a fan can delete tracker of game
     *
     * # use case 3.2
     * # use case 3.2
     *
     * @param game - The game the fan wants to stop following
     */
    public void removeRegisterFromGames(Game game){//useCase 3.2
        game.delete(this);
    }

    public void updateGame(String description, EEventType eventType) {
        if(this.status == EStatus.ONLINE){
            // TODO: 29/04/2020 pop up message
        }
        else{
            pendingGameNotifications.put(description,eventType);
        }
    }

    /**
     * This method should receive a complain text (from the service layer)
     * and sends the complain to a complaint box .
     * @param complain - Verbal text describing the complain
     */
    public void submitComplain(String complain) {//useCase 3.4
        Complains.getInstance().addComplain(complain,this);
    }

    /**
     *A fan can respond to a complaint he has sent to the administrator
     *
     * @param systemManager - The administrator who responded to the complaint
     * @param Complain - The Complaint
     * @param response - The answer to the complaint
     *
     * # use case 3.4
     */
    public void getResponseForComplain(SystemManager systemManager,String Complain,String response){}

    /**
     * A fan can view his search history
     *
     * # use case 3.5
     * @return search history.
     */
    public List<String> getSearchHistory() { //useCase 3.5
        return this.searchHistory;
    }

    /**
     * A fan can view his search history
     *
     * # use case 3.5
     * @return search history in order to display it on the screen.
     */
    public String viewSearchHistory(){ //useCase 3.5
        StringBuilder res = new StringBuilder();
        for (String aSearchHistory : this.searchHistory) {
            res.append(aSearchHistory);
        }
        return res.toString();
    }

    public void setSearchHistory(List<String> searchHistory) {
        if(searchHistory!= null) {
            this.searchHistory = searchHistory;
        }
    }

    /**
     * A fan can perform system searches
     * A fan search history is saved to the system
     * @param name - The content for which the fan wants information
     * return null - If no result is found
     */
    @Override
    public String searchByName(String name){
        if (name != null) {
            String res = super.searchByName(name);
            this.searchHistory.add(name);
            return res;
        }
        return null;
    }

    /**
     * A fan can perform system searches
     * A fan search history is saved to the system
     * @param categoryName - The category for which the fan wants information
     * return null - If no result is found
     */
    @Override
   public LinkedList<String> searchByCategory(String categoryName){
//        if (categoryName != null) {
//            String res = super.searchByCategory(categoryName);
//            this.searchHistory.add(categoryName);
//            return res;
//        }
        return null;
    }

    public void addTimeChangedNotification(String notification){
        if(notification != null && notification.length() != 0){
            if(this.status == EStatus.ONLINE){
                //throw some exception or other way to let the service layer to pop up message for referee
            }
            //save the notifications for later (show it when referee is online)
            else{
                pendingScheduleNotifications.put(notification, EScheduleOrPlaceEvent.TIME_CHANGED);
            }
        }

    }

    public void addDateChangedNotification(String notification){
        if(notification != null && notification.length() != 0){
            if(this.status == EStatus.ONLINE){
                //throw some exception or other way to let the service layer to pop up message for referee
            }
            //save the notifications for later (show it when referee is online)
            else{
                pendingScheduleNotifications.put(notification, EScheduleOrPlaceEvent.DATE_CHANGED);
            }
        }
    }

    public void addFieldChangedNotification(String notification){
        if(notification != null && notification.length() != 0){
            if(this.status == EStatus.ONLINE){
                //throw some exception or other way to let the service layer to pop up message for referee
            }
            //save the notifications for later (show it when referee is online)
            else{
                pendingScheduleNotifications.put(notification, EScheduleOrPlaceEvent.FIELD_CHANGED);;
            }
        }
    }


    /**
     * A fan can perform system searches
     * A fan search history is saved to the system
     * @param keyWord - The key word for which the fan wants information
     * return null - If no result is found
     */
//    @Override
//    public String searchByKeyWord(String keyWord){
//        if (keyWord != null) {
//            String res = super.searchByKeyWord(keyWord);
//            this.searchHistory.add(keyWord);
//            return res;
//        }
//        return null;
//    }

}