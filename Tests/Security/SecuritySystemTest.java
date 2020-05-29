package Security;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.FailedLoginException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecuritySystemTest {

    private SecuritySystem securitySystem;

    @BeforeEach
    void setUp() {
        this.securitySystem = new SecuritySystem();

    }

    @Test
    void addNewUser() {
        assertEquals(true,securitySystem.addNewUser("Tair","1213"));
        assertEquals(false,securitySystem.addNewUser("Tair","!1"));
        // null arguments
        assertEquals(false,securitySystem.addNewUser(null,"!1"));
        assertEquals(false,securitySystem.addNewUser("Tair",null));
        //empty arguments
        assertEquals(false,securitySystem.addNewUser("","!1"));
        assertEquals(false,securitySystem.addNewUser("Tair",""));
        assertEquals(true,securitySystem.addNewUser("yuval","1213"));
    }

    @Test
    void updatePassword() {
        assertEquals(false,securitySystem.updatePassword("Tair","1213"));
        assertEquals(true,securitySystem.addNewUser("Tair","!1"));
        assertEquals(true,securitySystem.updatePassword("Tair","1213"));
        // null arguments
        assertEquals(false,securitySystem.updatePassword(null,"!1"));
        assertEquals(false,securitySystem.updatePassword("Tair",null));
        //empty arguments
        assertEquals(false,securitySystem.updatePassword("","!1"));
        assertEquals(false,securitySystem.updatePassword("Tair",""));
    }

    @Test
    void checkPasswordForLogIn() throws FailedLoginException {
        assertEquals(true,securitySystem.addNewUser("Tair","1213"));
        assertEquals(false,securitySystem.checkPasswordForLogIn("Tair","1212"));
        assertEquals(false,securitySystem.checkPasswordForLogIn("Tair","1213!"));
        assertEquals(false,securitySystem.checkPasswordForLogIn("Tair",null));
        assertEquals(false,securitySystem.checkPasswordForLogIn("Tair",""));
        assertEquals(true,securitySystem.checkPasswordForLogIn("Tair","1213"));
    }

    @Test
    void removeUser() {
        assertEquals(true,securitySystem.addNewUser("Tair","1213"));
        securitySystem.removeUser("Tair");
        securitySystem.removeUser("");
        securitySystem.removeUser(null);
    }
}