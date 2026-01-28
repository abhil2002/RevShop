package service;

import dao.*;
import model.CartItem;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;

public class OrderService {
    private OrderDAO orderDAO = new OrderDAO();
    private CartDAO cartDAO = new CartDAO();
    private ProductDAO productDAO = new ProductDAO();
    private NotificationDAO notificationDAO = new NotificationDAO();

    public void checkout(int buyerId, String ship, String bill) {
        int cartId = cartDAO.getOrCreateCart(buyerId);
        List<CartItem> items = cartDAO.viewCart(cartId);

        if (items.isEmpty()) {
            System.out.println("🛒 Cart is empty");
            return;
        }

        System.out.println("Select Payment Method:");
        System.out.println("1. UPI");
        System.out.println("2. Card");
        System.out.println("3. Cash on Delivery");

        int p = new Scanner(System.in).nextInt();
        String method = (p == 1) ? "UPI" : (p == 2) ? "CARD" : "COD";

        double total = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        int orderId = orderDAO.createOrder(buyerId, total, ship, bill, method);
        orderDAO.addOrderItems(orderId, items);

        try (Connection con = DBConnection.getConnection()) {
            for (CartItem ci : items) {
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE products SET stock_quantity = stock_quantity - ? WHERE product_id=?");
                ps.setInt(1, ci.getQuantity());
                ps.setInt(2, ci.getProductId());
                ps.executeUpdate();
            }
        } catch (Exception e) { e.printStackTrace(); }

        notificationDAO.create(buyerId,
                "Your order #" + orderId + " has been placed successfully!");

        String sql = """
          SELECT DISTINCT p.seller_id
          FROM order_items oi
          JOIN products p ON oi.product_id = p.product_id
          WHERE oi.order_id=?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int sellerId = rs.getInt("seller_id");
                notificationDAO.create(sellerId,
                        "New order received! Order ID: " + orderId);
            }

        } catch (Exception e) { e.printStackTrace(); }

        cartDAO.clearCart(cartId);
        System.out.println("🎉 Order placed successfully! Order ID: " + orderId);
    }
}
