package dao;

import model.CartItem;
import util.DBConnection;

import java.sql.*;
import java.util.*;

public class CartDAO {

    public int getOrCreateCart(int buyerId) {
        String find = "SELECT cart_id FROM carts WHERE buyer_id=?";
        String insert = "INSERT INTO carts(buyer_id) VALUES(?)";

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(find);
            ps.setInt(1, buyerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("cart_id");

            ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, buyerId);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }

    public void addToCart(int cartId, int productId, int qty) {
        String sql = """
            INSERT INTO cart_items(cart_id,product_id,quantity)
            VALUES (?,?,?)
            ON DUPLICATE KEY UPDATE quantity = quantity + ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ps.setInt(3, qty);
            ps.setInt(4, qty);
            ps.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }
    }

    public void removeFromCart(int cartId, int productId) {
        String sql = "DELETE FROM cart_items WHERE cart_id=? AND product_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ps.executeUpdate();
            System.out.println("🗑 Removed from cart");

        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<CartItem> viewCart(int cartId) {
        List<CartItem> list = new ArrayList<>();
        String sql = """
          SELECT p.product_id, p.name, c.quantity, p.discounted_price
          FROM cart_items c JOIN products p ON c.product_id = p.product_id
          WHERE c.cart_id=?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItem ci = new CartItem();
                ci.setProductId(rs.getInt("product_id"));
                ci.setName(rs.getString("name"));
                ci.setQuantity(rs.getInt("quantity"));
                ci.setPrice(rs.getDouble("discounted_price"));
                list.add(ci);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public void clearCart(int cartId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM cart_items WHERE cart_id=?")) {
            ps.setInt(1, cartId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
