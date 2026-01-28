package dao;

import model.User;
import util.DBConnection;

import java.sql.*;

public class UserDAO {

    public int register(String name, String email, String passHash,
                        String role, String question, String answer) {
        String sql = """
            INSERT INTO users(name,email,password_hash,role,security_question,security_answer)
            VALUES (?,?,?,?,?,?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, passHash);
            ps.setString(4, role);
            ps.setString(5, question);
            ps.setString(6, answer);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public User login(String email, String passHash) {
        String sql = "SELECT * FROM users WHERE email=? AND password_hash=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, passHash);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean recover(String email, String answer, String newHash) {
        String sql = "UPDATE users SET password_hash=? WHERE email=? AND security_answer=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newHash);
            ps.setString(2, email);
            ps.setString(3, answer);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changePassword(int id, String oldHash, String newHash) {
        String sql = "UPDATE users SET password_hash=? WHERE user_id=? AND password_hash=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newHash);
            ps.setInt(2, id);
            ps.setString(3, oldHash);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
