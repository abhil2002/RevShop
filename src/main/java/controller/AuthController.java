package controller;

import dao.SellerDAO;
import model.User;
import service.AuthService;

import java.util.Scanner;

public class AuthController {

    private AuthService authService = new AuthService();
    private Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n=== REVSHOP ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("4. Forgot Password");
            System.out.println("5. Change Password");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> { System.out.println("👋 Exiting RevShop..."); return; }
                case 4 -> recover();
                case 5 -> changePassword();
                default -> System.out.println("❌ Invalid choice");
            }
        }
    }

    private void register() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.print("Role (BUYER/SELLER): ");
        String role = sc.nextLine().toUpperCase();

        System.out.print("Security Question: ");
        String q = sc.nextLine();
        System.out.print("Security Answer: ");
        String a = sc.nextLine();

        int userId = authService.register(name, email, password, role, q, a);

        if (role.equals("SELLER")) {
            System.out.print("Business Name: ");
            String biz = sc.nextLine();
            System.out.print("GST Number: ");
            String gst = sc.nextLine();
            System.out.print("Address: ");
            String addr = sc.nextLine();
            new SellerDAO().addSeller(userId, biz, gst, addr);
        }

        System.out.println("✅ Registration successful!");
    }

    private void login() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        User user = authService.login(email, password);

        if (user == null) {
            System.out.println("❌ Invalid credentials");
            return;
        }

        if (user.getRole().equals("BUYER")) {
            new BuyerController().menu(user);
        } else {
            new SellerController().menu(user);
        }
    }

    private void recover() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Security Answer: ");
        String answer = sc.nextLine();
        System.out.print("New Password: ");
        String pass = sc.nextLine();

        boolean ok = authService.recover(email, answer, pass);
        System.out.println(ok ? "✅ Password updated" : "❌ Recovery failed");
    }

    private void changePassword() {
        System.out.print("User ID: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Old Password: ");
        String o = sc.nextLine();
        System.out.print("New Password: ");
        String n = sc.nextLine();
        boolean ok = authService.changePassword(id, o, n);
        System.out.println(ok ? "✅ Updated" : "❌ Failed");
    }
}
