package AcceptanceTests.Tests;
import AcceptanceTests.DataObjects.UserDetails;
import AcceptanceTests.SystemOperations.ISystemOperationsBridge;
import AcceptanceTests.SystemOperations.RealSystemOperations;
import AcceptanceTests.SystemOperations.SystemOperationProxy;
import org.junit.*;
import static org.junit.jupiter.api.Assertions.*;

public class UC1Tests {
    ISystemOperationsBridge systemOperations;
    UserDetails registeredUser;

    @Before
    public void Setup(){
        systemOperations = new RealSystemOperations();
        registeredUser = systemOperations.getNewRegisteredUserForTest(); //Registered!!!
    }

    @Test
    public void LoginTest_SuccessScenario(){
        assertTrue(systemOperations.login(registeredUser.userName, registeredUser.password));
    }

    @Test
    public void RegisterTest_tryToRegisterNewUserWithExistUsername_SuccessScenario(){

        assertFalse(systemOperations.register(registeredUser.userName, registeredUser.password,registeredUser.firstName,registeredUser.LastName));
    }

    @Test
    public void RegisterTest_SuccessScenario(){
        assertTrue(systemOperations.register("NewRandomUserName", "NewRandompassword","NewRandomfirstName","NewRandomLastName"));
    }

    @Test
    public void RegisterTest_tryToRegisterWithNullUserName_FailureScenario(){
        assertFalse(systemOperations.register(null, registeredUser.password,registeredUser.firstName,registeredUser.LastName));
    }

    @Test
    public void RegisterTest_tryToRegisterWithNullPassword_FailureScenario(){
        assertFalse(systemOperations.register("tryToRegisterWithNullPassword", null,registeredUser.firstName,registeredUser.LastName));
    }

    @Test
    public void RegisterTest_tryToRegisterWithNullFirstName_FailureScenario(){
        assertFalse(systemOperations.register("RegisterTest_tryToRegisterWithNullFirstName_FailureScenario", registeredUser.password,null,registeredUser.LastName));
    }

    @Test
    public void RegisterTest_tryToRegisterWithNullLastName_FailureScenario(){
        assertFalse(systemOperations.register("RegisterTest_tryToRegisterWithNullLastName_FailureScenario", registeredUser.password,registeredUser.firstName,null));
    }

    @Test
    public void LoginTest_notRegisteredUser_FailureScenario(){
        assertFalse(systemOperations.login("randomNOtREGIStered", "password"));
    }

    @Test
    public void LoginTest_badPassword_FailureScenario(){
        assertFalse(systemOperations.login(registeredUser.userName, "THisISabadpasswrod"));
    }

    @Test
    public void LoginTest_nullPassword_FailureScenario(){
        assertFalse(systemOperations.login(registeredUser.userName, null));
    }

    @Test
    public void LoginTest_nullUserName_FailureScenario(){
        assertFalse(systemOperations.login(null, registeredUser.password));
    }

    @Test
    public void LoginTest_nullUserName_nullPassword_FailureScenario(){
        assertFalse(systemOperations.login(null, null));
    }

    /**
     *     The system registers the system administrator when the system is set up when its username is "admin" and its password is "admin"
     */
    @Test
    public void InitializeSystemTest_SuccessScenario(){
         assertTrue(systemOperations.login("admin", "admin"));
    }

}

