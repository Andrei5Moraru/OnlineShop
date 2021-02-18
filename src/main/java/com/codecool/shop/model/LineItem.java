package com.codecool.shop.model;

public class LineItem {

    private final Product product;
    private int id;
    private int quantity;
    private double itemTotal;


    public LineItem(Product product) {
        this.id = 1;
        this.product = product;
        this.quantity = 1;
        setItemTotal();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void incrementQuantity() {
        this.quantity++;
        setItemTotal();
    }

    public void decrementQuantity() {
        this.quantity--;
        setItemTotal();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        setItemTotal();
    }

    public void setItemTotal() {
        this.itemTotal = this.product.getDefaultPrice() * this.quantity;
    }

    public double getItemTotal() {
        return itemTotal;
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "product=" + product +
                ", id=" + id +
                ", quantity=" + quantity +
                '}';
    }
}
