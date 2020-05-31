package DAL;
import BL.AssociationAssets.Event;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class DeleteData {

    DatabaseManager databaseManager;

    public DeleteData(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }


    public void deleteEvent(Event event, int gameID){

        // change date formatting
        Date date= event.getDate();
        date.setHours(event.getTime().getHours());
        date.setMinutes(event.getTime().getMinutes());

        java.util.Date dt = event.getDate();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(dt);

        String query =
                "DELETE FROM events " +
                        "WHERE DateTime=? " +
                        "AND gameID=?;";

        PreparedStatement ps = null;
        try {
            ps = databaseManager.conn.prepareStatement(query); //compiling query in the DB
            ps.setString(1, dateStr);
            ps.setInt(2, gameID);

            //System.out.println(ps.toString());
            ps.executeUpdate();
            databaseManager.conn.commit();
        } catch (SQLException e) {
            try {
                databaseManager.conn.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e3) {
                e3.printStackTrace();
            }
        }
    }
}
