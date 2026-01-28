package controller;

import dao.OrderDAO;
import dao.ReviewDAO;
import model.Product;
import model.User;
import service.ProductService;

import java.util.List;
import java.util.Scanner;

public class SellerController {
    private ProductService productService = new ProductService();
    private OrderDAO orderDAO = new OrderDAO();
    private ReviewDAO reviewDAO = new ReviewDAO();
    private Scanner sc = new Scanner(System.in);

    public void menu(User user) {
        while (true) {
            System.out.println("\n--- SELLER MENU ---");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View My Products");
            System.out.println("5. View Orders");
            System.out.println("6. Stock Alerts");
            System.out.println("7. Logout");
            System.out.println("8. View Product Reviews");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> add(user);
                case 2 -> update(user);
                case 3 -> delete(user);
                case 4 -> list(user);
                case 5 -> orders(user);
                case 6 -> alerts(user);
                case 7 -> { return; }
                case 8 -> reviewDAO.viewBySeller(user.getUserId());
                default -> System.out.println("❌ Invalid choice");
            }
        }
    }

    private void add(User user) {
        Product p = new Product();
        p.setSellerId(user.getUserId());

        System.out.print("Name: ");
        p.setName(sc.nextLine());
        System.out.print("Description: ");
        p.setDescription(sc.nextLine());
        System.out.print("Category: ");
        p.setCategory(sc.nextLine());
        System.out.print("MRP: ");
        p.setMrp(sc.nextDouble());
        System.out.print("Discounted Price: ");
        p.setDiscountedPrice(sc.nextDouble());
        System.out.print("Stock Quantity: ");
        p.setStockQuantity(sc.nextInt());
        System.out.print("Threshold Quantity: ");
        p.setThresholdQuantity(sc.nextInt());
        sc.nextLine();

        productService.add(p);
    }

    private void update(User user) {
        System.out.print("Product ID: ");
        int pid = sc.nextInt();
        sc.nextLine();

        Product p = new Product();
        p.setProductId(pid);
        p.setSellerId(user.getUserId());

        System.out.print("New Name: ");
        p.setName(sc.nextLine());
        System.out.print("New Description: ");
        p.setDescription(sc.nextLine());
        System.out.print("New Category: ");
        p.setCategory(sc.nextLine());
        System.out.print("New MRP: ");
        p.setMrp(sc.nextDouble());
        System.out.print("New Discounted Price: ");
        p.setDiscountedPrice(sc.nextDouble());
        System.out.print("New Stock Quantity: ");
        p.setStockQuantity(sc.nextInt());
        System.out.print("New Threshold Quantity: ");
        p.setThresholdQuantity(sc.nextInt());
        sc.nextLine();

        productService.update(p);
    }

    private void delete(User user) {
        System.out.print("Product ID: ");
        int pid = sc.nextInt();
        productService.delete(pid, user.getUserId());
    }

    private void list(User user) {
        List<Product> list = productService.getSellerProducts(user.getUserId());
        list.forEach(p ->
                System.out.println(p.getProductId() + " | " + p.getName() +
                        " | Stock: " + p.getStockQuantity() +
                        " | Threshold: " + p.getThresholdQuantity()));
    }

    private void orders(User user) {
        orderDAO.viewSellerOrders(user.getUserId());
    }

    private void alerts(User user) {
        List<Product> list = productService.getSellerProducts(user.getUserId());
        list.stream()
                .filter(p -> p.getStockQuantity() <= p.getThresholdQuantity())
                .forEach(p ->
                        System.out.println("⚠ LOW STOCK: " + p.getName() +
                                " (" + p.getStockQuantity() + " left)")
                );
    }
}
