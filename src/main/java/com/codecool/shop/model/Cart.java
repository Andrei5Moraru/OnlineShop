package com.codecool.shop.model;


import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int id;
    private List<LineItem> items;
    private User user;


    public Cart(User user) {
        this.id = -1;
        this.items = new ArrayList<>();
        this.user = user;
        user.addCart(this);

    }

    public Cart(int id, User user) {
        this.id = id;
        this.user = user;
        user.addCart(this);

    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public int getSize() {
        return items.size();
    }

    public void emptyCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(LineItem itemToAdd) {

        LineItem targetItem = null;

        for (LineItem item: this.items) {
            if (item.getProduct() == itemToAdd.getProduct()) {
                targetItem = item;
                break;
            }
        }

        if (targetItem != null) {
            targetItem.incrementQuantity();
        } else {
            targetItem = itemToAdd;
            targetItem.setId(items.size() + 1);
            this.items.add(targetItem);
        }
    }


    public void removeItem(int itemId) {

        for (LineItem cartItem : this.items) {
            if (cartItem.getId() == itemId) {
                this.items.remove(cartItem);
                break;

            }
        }
    }

    public double getCartSubtotal() {
        double cartSubtotal = 0;
        for (LineItem item : items) {
            cartSubtotal += item.getProduct().getDefaultPrice() * item.getQuantity();
        }
        return cartSubtotal;
    }

}
