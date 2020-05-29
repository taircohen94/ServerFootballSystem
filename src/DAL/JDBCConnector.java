package DAL;

import AssociationAssets.*;
import System.*;
import Users.*;

import java.util.*;

/**
 * JDBC Connector in in charge of connecting with the DB.
 * Offers uploading and downloading all data do/from the system's datasets.
 */
public class JDBCConnector {

    public DatabaseManager databaseManager;
    public DataUploader uploader;
    public DataSave dataSave;

    public JDBCConnector() {
        databaseManager = new DatabaseManagerServerMySQL();
        uploader = new DataUploader(databaseManager);
        dataSave = new DataSave(databaseManager);
    }

    public void connectDBUploadData() {
        databaseManager.startConnection();
        uploader.uploadData();
        databaseManager.closeConnection();
    }

    public void connectDBSaveData() {
        databaseManager.startConnection();
        dataSave.saveAllData();
        databaseManager.closeConnection();
    }


}
