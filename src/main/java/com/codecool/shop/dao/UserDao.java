package com.codecool.shop.dao;

import com.codecool.shop.model.User;

public interface UserDao {
    void add(User user) throws Exception;
    User find(String email);
    User find(int id);
    void remove(int id);
}
