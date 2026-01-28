package controller;

import dao.FavoriteDAO;
import dao.NotificationDAO;
import dao.OrderDAO;
import model.CartItem;
import model.Product;
import model.User;
import service.CartService;
import service.OrderService;
import service.ProductService;
import service.ReviewService;

import java.util.List;
import java.util.Scanner;

public class BuyerController {

    private ProductService productService = new ProductService();
    private CartService cartService = new CartService();
    private OrderService orderService = new OrderService();
    private Scanner sc = new Scanner(System.in);

    public void menu(User user) {
        while (true) {
            System.out.println("\n--- BUYER MENU ---");
            System.out.println("1. Browse Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Reviews");
            System.out.println("6. Favorites");
            System.out.println("7. Notifications");
            System.out.println("8. Logout");
            System.out.println("9. Search Product");
            System.out.println("10. Filter by Category");
            System.out.println("11. Remove from Cart");
            System.out.println("12. Order History");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> browse();
                case 2 -> addToCart(user);
                case 3 -> viewCart(user);
                case 4 -> checkout(user);
                case 5 -> reviews(user);
                case 6 -> favorites(user);
                case 7 -> notifications(user);
                case 8 -> {
                    System.out.println("👋 Logged out");
                    return;
                }
                case 9 -> search();
                case 10 -> filter();
                case 11 -> removeFromCart(user);
                case 12 -> new OrderDAO().viewBuyerOrders(user.getUserId());
                default -> System.out.println("❌ Invalid choice");
            }
        }
    }

    // 1. Browse Products
    private void browse() {
        List<Product> products = productService.getAll();
        if (products.isEmpty()) {
            System.out.println("⚠ No products available");
            return;
        }

        products.forEach(p ->
                System.out.println(
                        p.getProductId() + " | " +
                                p.getName() + " | ₹" +
                                p.getDiscountedPrice() +
                                " | Stock: " +
                                p.getStockQuantity()
                )
        );
    }

    // 2. Add to Cart
    private void addToCart(User user) {
        System.out.print("Enter product ID: ");
        int pid = sc.nextInt();
        System.out.print("Quantity: ");
        int qty = sc.nextInt();
        sc.nextLine(); // consume newline

        cartService.add(user.getUserId(), pid, qty);
    }

    // 11. Remove from Cart
    private void removeFromCart(User user) {
        System.out.print("Enter product ID to remove: ");
        int pid = sc.nextInt();
        sc.nextLine(); // consume newline

        cartService.remove(user.getUserId(), pid);
    }

    // 3. View Cart
    private void viewCart(User user) {
        List<CartItem> items = cartService.view(user.getUserId());

        if (items.isEmpty()) {
            System.out.println("🛒 Your cart is empty");
            return;
        }

        double total = 0;

        for (CartItem ci : items) {
            double sub = ci.getPrice() * ci.getQuantity();
            total += sub;

            System.out.println(
                    ci.getProductId() + " | " +
                            ci.getName() + " x " +
                            ci.getQuantity() + " = ₹" + sub
            );
        }

        System.out.println("Total: ₹" + total);
    }

    // 4. Checkout
    private void checkout(User user) {
        System.out.print("Shipping Address: ");
        String ship = sc.nextLine();

        System.out.print("Billing Address: ");
        String bill = sc.nextLine();

        orderService.checkout(user.getUserId(), ship, bill);
    }

    // 5. Reviews
    private void reviews(User user) {
        System.out.print("Enter Product ID: ");
        int pid = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.println("1. View Reviews");
        System.out.println("2. Add Review");
        int c = sc.nextInt();
        sc.nextLine(); // consume newline

        ReviewService rs = new ReviewService();

        if (c == 1) {
            rs.view(pid);
        } else if (c == 2) {
            System.out.print("Rating (1-5): ");
            int rating = sc.nextInt();
            sc.nextLine();

            System.out.print("Comment: ");
            String comment = sc.nextLine();

            rs.add(pid, user.getUserId(), rating, comment);
        } else {
            System.out.println("❌ Invalid option");
        }
    }

    // 6. Favorites
    private void favorites(User user) {
        System.out.println("1. View Favorites");
        System.out.println("2. Add to Favorites");

        int c = sc.nextInt();
        sc.nextLine(); // consume newline

        FavoriteDAO favDAO = new FavoriteDAO();

        if (c == 2) {
            System.out.print("Enter Product ID: ");
            int pid = sc.nextInt();
            sc.nextLine();

            favDAO.add(user.getUserId(), pid);
        } else if (c == 1) {
            favDAO.view(user.getUserId());
        } else {
            System.out.println("❌ Invalid option");
        }
    }

    // 7. Notifications
    private void notifications(User user) {
        new NotificationDAO().view(user.getUserId());
    }

    // 9. Search Product
    private void search() {
        System.out.print("Enter keyword: ");
        String key = sc.nextLine();

        List<Product> results = productService.search(key);

        if (results.isEmpty()) {
            System.out.println("❌ No products found");
            return;
        }

        results.forEach(p ->
                System.out.println(
                        p.getProductId() + " | " +
                                p.getName() + " | ₹" +
                                p.getDiscountedPrice()
                )
        );
    }

    // 10. Filter by Category
    private void filter() {
        System.out.print("Enter category: ");
        String cat = sc.nextLine();

        List<Product> results = productService.filterByCategory(cat);

        if (results.isEmpty()) {
            System.out.println("❌ No products found in this category");
            return;
        }

        results.forEach(p ->
                System.out.println(
                        p.getProductId() + " | " +
                                p.getName() + " | ₹" +
                                p.getDiscountedPrice()
                )
        );
    }
}
