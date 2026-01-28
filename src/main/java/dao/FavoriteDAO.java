package dao;

import util.DBConnection;
import java.sql.*;

public class FavoriteDAO {

    public void add(int buyerId, int pid) {
        String sql = "INSERT IGNORE INTO favorites(buyer_id,product_id) VALUES (?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, buyerId);
            ps.setInt(2, pid);
            ps.executeUpdate();
            System.out.println("❤️ Added to favorites");

        } catch (Exception e) { e.printStackTrace(); }
    }

    public void view(int buyerId) {
        String sql = """
          SELECT p.product_id, p.name, p.discounted_price
          FROM favorites f JOIN products p ON f.product_id = p.product_id
          WHERE f.buyer_id=?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, buyerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("product_id") + " | " +
                        rs.getString("name") + " | ₹" +
                        rs.getDouble("discounted_price"));
            }

        } catch (Exception e) { e.printStackTrace(); }
    }
}
