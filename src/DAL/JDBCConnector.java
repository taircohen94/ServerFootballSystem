package DAL;

import BL.AssociationAssets.*;
import BL.AssociationAssets.Event;
import BL.System.*;
import BL.Users.*;

/**
 * JDBC Connector in in charge of connecting with the DB.
 * Offers uploading and downloading all data do/from the system's datasets.
 */
public class JDBCConnector {

    public DatabaseManager databaseManager;
    public DataUploader uploader;
    public DataSave dataSave;
    public DeleteData deleteData;

    public JDBCConnector() {
        databaseManager = new DatabaseManagerServerMySQL();
        uploader = new DataUploader(databaseManager);
        dataSave = new DataSave(databaseManager);
        deleteData = new DeleteData(databaseManager);
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

    public void deleteEvent(Event event, int gameID) {
        databaseManager.startConnection();
        deleteData.deleteEvent(event, gameID);
        databaseManager.closeConnection();
    }


}
