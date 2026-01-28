package dao;

import util.DBConnection;
import java.sql.*;

public class NotificationDAO {

    public void create(int userId, String message) {
        String sql = "INSERT INTO notifications(user_id, message) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, message);
            ps.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }
    }

    public void view(int userId) {
        String sql = "SELECT * FROM notifications WHERE user_id=? ORDER BY created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("🔔 " + rs.getString("message") +
                        " | " + rs.getTimestamp("created_at"));
            }

        } catch (Exception e) { e.printStackTrace(); }
    }
}
