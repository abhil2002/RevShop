package dao;

import util.DBConnection;
import java.sql.*;

public class SellerDAO {

    public void addSeller(int id, String biz, String gst, String addr) {
        String sql = "INSERT INTO sellers VALUES (?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, biz);
            ps.setString(3, gst);
            ps.setString(4, addr);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
