package Users;

/**
 * This class is an interface with the fan object
 * @ Written by Yuval Ben Eliezer
 */
public interface IFan {


    String viewProfile();
    void logout();
    String getUserName();
    void setUserName(String userName);
    String getfName();
    void setfName(String fName);
    String getlName();
    void setlName(String lName);
    EStatus getStatus();
    void setStatus(EStatus status);

}
