package DAL;

import java.sql.*;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Database Manager is in charge of setting a connection with the DB.
 * Using: MysQL server, over port 3306.
 * Connecting to remote server sitting on 132.72.65.88
 * Author: Amit Shakarchy
 */

public class DatabaseManager {

    protected String connectionString;
    protected String databaseName;
    protected String username;
    protected String password;
    protected Connection conn;

    //<editor-fold desc="Constructors">
    public DatabaseManager() {
        this.connectionString = "jdbc:mysql://132.72.65.88:3306/fsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        this.databaseName = "fsdb";
        this.username = "fab5";
        this.password = "root";
    }

    public DatabaseManager(String connectionString, String databaseName) {
        this.connectionString = connectionString;
        this.databaseName = databaseName;
        this.username = "fab5";
        this.password = "root";
    }

    public DatabaseManager(String connectionString) {
        this.connectionString = connectionString;
        this.databaseName = "";
        this.username = "";
        this.password = "";
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public String getConnectionString() {
        return connectionString;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabaseName() {
        return databaseName;
    }
    //</editor-fold>

    //<editor-fold desc="Connection Methods">

    /**
     * start connection with the given connectionString, username and password
     */
    public void startConnection() {
        try {
            conn = DriverManager.getConnection(connectionString, username, password);
            selectDatabase(this.databaseName);
            conn.setAutoCommit(false);
            System.out.println("started connection successfully");
        } catch (Exception ignored) {
            System.out.println("connection went bad");
        }
    }

    /**
     * Selects database to work on.
     *
     */
    private void selectDatabase(String databaseName) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("use " + databaseName);
            stmt.close();
        } catch (Exception ignored) {

        }
    }

    /**
     * Close the connection
     *
     */
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                conn = null;
            }
        } catch (Exception ignored) {

        }
    }
    //</editor-fold>

    //<editor-fold desc="Execute Queries">

    /**
     * @param query SQL query
     * @return ResultSet holds the query results
     */
    //Execute Queries
    public ResultSet executeQuerySelect(String query) {
        ResultSet resultSet = null;
        if (conn != null) {
            try {
                Statement sqlStatement = conn.createStatement();
                resultSet = sqlStatement.executeQuery(query);
            } catch (SQLException ignored) {
                System.out.println("bad query or connection: "+ignored.getMessage());
            }
        }
        return resultSet;
    }
    //</editor-fold>
}
