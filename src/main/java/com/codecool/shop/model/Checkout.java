package com.codecool.shop.model;

import java.util.Currency;
import java.util.UUID;

public class Checkout {

    private User user;
    private Cart cart;
    private UUID id;

    public Checkout(Cart cart, User user) {
       this.user = user;
       this.cart = cart;
        this.id = UUID.randomUUID();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}