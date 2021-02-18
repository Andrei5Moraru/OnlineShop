package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDaoMem implements OrderDao {

    private List<Order> listOfOrders = new ArrayList<>();
    private static OrderDaoMem instance = null;

    private OrderDaoMem() {
    }

    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Order order) {
        listOfOrders.add(order);
    }

    @Override
    public void remove(int id) {
        listOfOrders.remove(find(id));
    }


    @Override
    public Order find(int id) {
        return listOfOrders.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    public List<Order> getListOfOrders() {
        return listOfOrders;
    }
}