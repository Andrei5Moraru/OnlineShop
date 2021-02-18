package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartDaoMem implements CartDao {

    private static final Logger logger = LoggerFactory.getLogger(CartDaoMem.class);

    private List<Cart> data = new ArrayList<>();
    private static CartDaoMem instance = null;

    private CartDaoMem() {}

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Cart cart) {
        if (cart.getId() == -1) {
            cart.setId(data.size() + 1);
            data.add(cart);
            logger.info("A cart with id {} was added", cart.getId());
        }
    }

    public List<Cart> getAll() {
        return data;
    }

    @Override
    public int getSize() {return data.size();}

    @Override
    public Cart find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

}