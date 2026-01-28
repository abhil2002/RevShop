package dao;

import model.CartItem;
import util.DBConnection;

import java.sql.*;
import java.util.List;

public class OrderDAO {

    public int createOrder(int buyerId, double total, String ship, String bill, String pay) {
        String sql = """
          INSERT INTO orders(buyer_id,total_amount,status,shipping_address,billing_address,payment_method)
          VALUES (?,?, 'PLACED', ?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, buyerId);
            ps.setDouble(2, total);
            ps.setString(3, ship);
            ps.setString(4, bill);
            ps.setString(5, pay);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }

    public void addOrderItems(int orderId, List<CartItem> items) {
        String sql = "INSERT INTO order_items(order_id,product_id,quantity,price) VALUES (?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (CartItem ci : items) {
                ps.setInt(1, orderId);
                ps.setInt(2, ci.getProductId());
                ps.setInt(3, ci.getQuantity());
                ps.setDouble(4, ci.getPrice());
                ps.addBatch();
            }
            ps.executeBatch();

        } catch (Exception e) { e.printStackTrace(); }
    }

    public void viewSellerOrders(int sellerId) {
        String sql = """
          SELECT o.order_id, o.created_at, p.name, oi.quantity, oi.price
          FROM orders o
          JOIN order_items oi ON o.order_id = oi.order_id
          JOIN products p ON oi.product_id = p.product_id
          WHERE p.seller_id=?
          ORDER BY o.created_at DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sellerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Order #" + rs.getInt("order_id") +
                        " | " + rs.getString("name") +
                        " x " + rs.getInt("quantity") +
                        " | ₹" + rs.getDouble("price") +
                        " | " + rs.getTimestamp("created_at"));
            }

        } catch (Exception e) { e.printStackTrace(); }
    }

    public void viewBuyerOrders(int buyerId) {
        String sql = """
          SELECT order_id, total_amount, payment_method, created_at
          FROM orders WHERE buyer_id=? ORDER BY created_at DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, buyerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Order #" + rs.getInt("order_id") +
                        " | ₹" + rs.getDouble("total_amount") +
                        " | " + rs.getString("payment_method") +
                        " | " + rs.getTimestamp("created_at"));
            }

        } catch (Exception e) { e.printStackTrace(); }
    }
}
