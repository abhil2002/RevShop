package service;

import dao.CartDAO;
import dao.ProductDAO;
import model.CartItem;
import model.Product;
import java.util.List;

public class CartService {

    private CartDAO cartDAO = new CartDAO();
    private ProductDAO productDAO = new ProductDAO();

    public void add(int buyerId, int productId, int qty) {
        int cartId = cartDAO.getOrCreateCart(buyerId);
        Product p = productDAO.getById(productId);

        if (p == null || p.getStockQuantity() < qty) {
            System.out.println("❌ Product unavailable");
            return;
        }

        cartDAO.addToCart(cartId, productId, qty);
        System.out.println("✅ Added to cart");
    }

    public void remove(int buyerId, int productId) {
        int cartId = cartDAO.getOrCreateCart(buyerId);
        cartDAO.removeFromCart(cartId, productId);
    }

    public List<CartItem> view(int buyerId) {
        int cartId = cartDAO.getOrCreateCart(buyerId);
        return cartDAO.viewCart(cartId);
    }

    public void clear(int buyerId) {
        int cartId = cartDAO.getOrCreateCart(buyerId);
        cartDAO.clearCart(cartId);
    }
}
