package service;

import dao.ProductDAO;
import model.Product;
import java.util.List;

public class ProductService {
    private ProductDAO dao = new ProductDAO();

    public List<Product> getAll() { return dao.getAllProducts(); }
    public void add(Product p) { dao.addProduct(p); }
    public void update(Product p) { dao.updateProduct(p); }
    public void delete(int pid, int sid) { dao.deleteProduct(pid, sid); }
    public List<Product> getSellerProducts(int sid) { return dao.getBySeller(sid); }
    public List<Product> search(String k) { return dao.search(k); }
    public List<Product> filterByCategory(String c) { return dao.filterByCategory(c); }
}
