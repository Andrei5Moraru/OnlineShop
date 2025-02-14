package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Supplier extends BaseModel {

    private List<Product> products = new ArrayList<>();

    public Supplier(String name, String description) {
        super(name);
        this.description = description;

    }

    public Supplier(int id, String name, String description) {
        super(name);
        this.id = id;
        this.description = description;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "description: %3$s",
                this.id,
                this.name,
                this.description
        );
    }
}