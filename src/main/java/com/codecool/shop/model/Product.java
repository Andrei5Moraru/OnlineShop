package com.codecool.shop.model;

import java.math.BigDecimal;
import java.util.Currency;

public class Product extends BaseModel {

    private double defaultPrice;
    transient private Currency defaultCurrency;
    transient private ProductCategory productCategory;
    transient private Supplier supplier;



    public Product(String name, double defaultPrice, String currencyString, String description, ProductCategory productCategory, Supplier supplier,int amount) {
        super(name, description);
        this.setPrice(defaultPrice, currencyString);
        this.setSupplier(supplier);
        this.setProductCategory(productCategory);
        this.setAmount(amount);

    }

    public Product(int id, String name, double defaultPrice, String currencyString, String description, ProductCategory productCategory, Supplier supplier,int amount) {
        super(name, description);
        this.id = id;
        this.setPrice(defaultPrice, currencyString);
        this.setSupplier(supplier);
        this.setProductCategory(productCategory);
        this.setAmount(amount);

    }

    public double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public String getPrice() {
        return this.defaultPrice + " " + this.defaultCurrency.toString();
    }

    public void setPrice(double price, String currency) {
        this.defaultPrice = price;
        this.defaultCurrency = Currency.getInstance(currency);
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        this.productCategory.addProduct(this);
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
        this.supplier.addProduct(this);
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "defaultPrice: %3$f, " +
                        "defaultCurrency: %4$s, " +
                        "productCategory: %5$s, " +
                        "supplier: %6$s",
                this.id,
                this.name,
                this.defaultPrice,
                this.defaultCurrency.toString(),
                this.productCategory.getName(),
                this.supplier.getName());
    }

}