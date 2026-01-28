package model;

public class Product {
    private int productId;
    private int sellerId;
    private String name;
    private String description;
    private String category;
    private double mrp;
    private double discountedPrice;
    private int stockQuantity;
    private int thresholdQuantity;

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getSellerId() { return sellerId; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getMrp() { return mrp; }
    public void setMrp(double mrp) { this.mrp = mrp; }

    public double getDiscountedPrice() { return discountedPrice; }
    public void setDiscountedPrice(double discountedPrice) { this.discountedPrice = discountedPrice; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public int getThresholdQuantity() { return thresholdQuantity; }
    public void setThresholdQuantity(int thresholdQuantity) { this.thresholdQuantity = thresholdQuantity; }
}
