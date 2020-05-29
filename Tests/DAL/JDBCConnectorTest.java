package DAL;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JDBCConnectorTest {

    @Test
    void connectDBUploadData() {
        JDBCConnector connector= new JDBCConnector();
        connector.connectDBUploadData();
        connector.connectDBSaveData();
    }
}