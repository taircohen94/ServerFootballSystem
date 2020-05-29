package Security;

import javax.security.auth.login.FailedLoginException;
import java.util.HashMap;
import java.util.Map;

/**
 * this class responsible for the security of the users.
 * the passwords encrypted and saved for each user with AES algorithm.
 * Aouthors: Tair Cohen
 */
public class SecuritySystem {
    private Map<String, String> usersHashMap = new HashMap<>();
    private AESEncryption AES = new AESEncryption();
    private final String secretKey = "ssshhhhhhhhhhh!!!!";

    /**
     * Getter for usersHashMap.
     * @param code - will return the hashmap only if the given code is correct.
     * @return - users hashMap
     */
    public Map<String, String> getUsersHashMap(String code) {
        if(code.equals("iseFab5")) {
            return usersHashMap;
        }else return null;
    }

    /**
     * Sets users & passwords details
     * @param usersHashMap - uploaded from DB
     * @param code - will return the hashmap only if the given code is correct.
     */
    public void setUsersHashMap(Map<String, String> usersHashMap, String code) {
        if(code.equals("iseFab5")) {
            this.usersHashMap = usersHashMap;
        }
    }

    /**
     * this function add new user to the system.
     * the function is using the AES algorithm to encrypt the password/
     * the function checks if the user name already exist, and if so return false.
     *
     * @param userName
     * @param password
     * @return true - if the user name and password were adding successfully to the hash map,
     * otherwise - false;
     */
    public boolean addNewUser(String userName, String password) {
        if (password == null || userName == null ||
                password.isEmpty() || userName.isEmpty()) {
            System.out.println("password and user name are invalid");
            return false;
        }
        if (usersHashMap.containsKey(userName)) {
            System.out.println("This user name is already exist, please try new one");
            return false;
        }
        password = AES.encrypt(password, secretKey);
        usersHashMap.put(userName, password);
        return true;
    }


    /**
     * this function updates a new password for exiting user.
     *
     * @param userName
     * @param password
     * @return true if the password updated successfully, otherwise - false.
     */
    public boolean updatePassword(String userName, String password) {
        if (!usersHashMap.containsKey(userName)) {
            System.out.println("This user id doesnt exits in the system");
            return false;
        }
        if(userName == null || password == null || userName.isEmpty() || password.isEmpty()){
            System.out.println("password and user name are invalid");
            return false;
        }
        password = AES.encrypt(password, secretKey);
        usersHashMap.put(userName, password);
        System.out.println("password has been update to the user: " +userName);
        return true;
    }

    /**
     * this function checks if the password of a user is correct for login option.
     * the function is using AES decryption because the hash map holding encrypted passwords.
     *
     * @param userName
     * @param password
     * @return true if the password is correct, otherwise - false
     */
    public boolean checkPasswordForLogIn(String userName, String password) throws FailedLoginException {
        if (!usersHashMap.containsKey(userName)) {
            throw new FailedLoginException("User name doesn't exists");
        }
        if (AES.decrypt(usersHashMap.get(userName), secretKey).equals(password)) {
            // the user can log in, the password is correct
            return true;
        }
        throw new FailedLoginException("Incorrect Password");
    }

    /**
     * this function removes existing user from the hash map.
     *
     * @param userName
     */
    public void removeUser(String userName) {
        if (this.usersHashMap.containsKey(userName)) {
            this.usersHashMap.remove(userName);
        }
    }

    public Map<String, String> getEncryptedUsersHashMap(String code) {
        Map<String, String> tmp= new HashMap<>();
        if(code.equals("iseFab5")) {
            for (Map.Entry<String, String> entry:usersHashMap.entrySet()) {
                tmp.put(entry.getKey(),AES.encrypt(entry.getValue(),secretKey));
            }
        }
        return tmp;
    }
}
