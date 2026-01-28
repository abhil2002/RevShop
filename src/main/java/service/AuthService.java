package service;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;

public class AuthService {
    private UserDAO userDAO = new UserDAO();

    public int register(String name, String email, String password,
                        String role, String q, String a) {
        return userDAO.register(
                name, email, PasswordUtil.hash(password), role, q, a);
    }

    public User login(String email, String password) {
        return userDAO.login(email, PasswordUtil.hash(password));
    }

    public boolean recover(String email, String answer, String newPassword) {
        return userDAO.recover(email, answer, PasswordUtil.hash(newPassword));
    }

    public boolean changePassword(int id, String oldPass, String newPass) {
        return userDAO.changePassword(
                id, PasswordUtil.hash(oldPass), PasswordUtil.hash(newPass));
    }
}
