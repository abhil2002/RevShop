package dao;

import util.DBConnection;
import java.sql.*;

public class ReviewDAO {

    public void add(int pid, int buyerId, int rating, String comment) {
        String sql = "INSERT INTO reviews(product_id,buyer_id,rating,comment) VALUES (?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pid);
            ps.setInt(2, buyerId);
            ps.setInt(3, rating);
            ps.setString(4, comment);
            ps.executeUpdate();
            System.out.println("⭐ Review added");

        } catch (Exception e) { e.printStackTrace(); }
    }

    public void viewByProduct(int pid) {
        String sql = """
          SELECT u.name, r.rating, r.comment
          FROM reviews r JOIN users u ON r.buyer_id = u.user_id
          WHERE r.product_id=?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("name") +
                        " | ⭐ " + rs.getInt("rating") +
                        " | " + rs.getString("comment"));
            }

        } catch (Exception e) { e.printStackTrace(); }
    }

    public void viewBySeller(int sellerId) {
        String sql = """
          SELECT p.name, u.name, r.rating, r.comment
          FROM reviews r
          JOIN products p ON r.product_id=p.product_id
          JOIN users u ON r.buyer_id=u.user_id
          WHERE p.seller_id=?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sellerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1) + " | " +
                        rs.getString(2) + " | ⭐ " +
                        rs.getInt(3) + " | " +
                        rs.getString(4));
            }

        } catch (Exception e) { e.printStackTrace(); }
    }
}
