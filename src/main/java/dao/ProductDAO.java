package dao;

import model.Product;
import util.DBConnection;

import java.sql.*;
import java.util.*;

public class ProductDAO {

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE stock_quantity > 0";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Product p = map(rs);
                list.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public Product getById(int id) {
        String sql = "SELECT * FROM products WHERE product_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);

        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void addProduct(Product p) {
        String sql = """
          INSERT INTO products
          (seller_id,name,description,category,mrp,discounted_price,stock_quantity,threshold_quantity)
          VALUES (?,?,?,?,?,?,?,?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getSellerId());
            ps.setString(2, p.getName());
            ps.setString(3, p.getDescription());
            ps.setString(4, p.getCategory());
            ps.setDouble(5, p.getMrp());
            ps.setDouble(6, p.getDiscountedPrice());
            ps.setInt(7, p.getStockQuantity());
            ps.setInt(8, p.getThresholdQuantity());
            ps.executeUpdate();
            System.out.println("✅ Product added");

        } catch (Exception e) { e.printStackTrace(); }
    }

    public void updateProduct(Product p) {
        String sql = """
          UPDATE products SET
          name=?, description=?, category=?, mrp=?, discounted_price=?,
          stock_quantity=?, threshold_quantity=?
          WHERE product_id=? AND seller_id=?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setString(3, p.getCategory());
            ps.setDouble(4, p.getMrp());
            ps.setDouble(5, p.getDiscountedPrice());
            ps.setInt(6, p.getStockQuantity());
            ps.setInt(7, p.getThresholdQuantity());
            ps.setInt(8, p.getProductId());
            ps.setInt(9, p.getSellerId());
            ps.executeUpdate();
            System.out.println("✅ Product updated");

        } catch (Exception e) { e.printStackTrace(); }
    }

    public void deleteProduct(int pid, int sid) {
        String sql = "DELETE FROM products WHERE product_id=? AND seller_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pid);
            ps.setInt(2, sid);
            ps.executeUpdate();
            System.out.println("🗑 Product deleted");

        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Product> getBySeller(int sellerId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE seller_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sellerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));

        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<Product> search(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ? OR description LIKE ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));

        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<Product> filterByCategory(String category) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));

        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    private Product map(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setProductId(rs.getInt("product_id"));
        p.setSellerId(rs.getInt("seller_id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setCategory(rs.getString("category"));
        p.setMrp(rs.getDouble("mrp"));
        p.setDiscountedPrice(rs.getDouble("discounted_price"));
        p.setStockQuantity(rs.getInt("stock_quantity"));
        p.setThresholdQuantity(rs.getInt("threshold_quantity"));
        return p;
    }
}
