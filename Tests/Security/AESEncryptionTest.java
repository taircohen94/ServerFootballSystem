package Security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AESEncryptionTest {

    private String cipher;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setKey() {
    }

    @Test
    void encrypt() {
        cipher = AESEncryption.encrypt("tair","shhhh!!");
        AESEncryption.encrypt("tair","");
    }

    @Test
    void decrypt() {
        cipher = AESEncryption.encrypt("tair","shhhh!!");
        String message = AESEncryption.decrypt(cipher,"shhhh!!");
        assertEquals("tair",message);
    }
}