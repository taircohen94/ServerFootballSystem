package DAL;
/**
 * Database Manager Server MySQL conducts the connection.
 * Using: "com.mysql.cj.jdbc.Driver
 * Author: Amit Shakarchy
 */
public class DatabaseManagerServerMySQL extends DatabaseManager {

    public DatabaseManagerServerMySQL() {
        super();
    }

    @Override
    public void startConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            super.startConnection();
        } catch (Exception ignored) {

        }
    }

}
