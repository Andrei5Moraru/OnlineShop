package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


import static org.junit.jupiter.api.Assertions.*;

class CartDaoMemTest {

    CartDaoMem testCartDaoMem;
    Cart testCart1;
    Cart testCart2;
    User user;

    @BeforeEach
    void setup() {
        user = new User("Test", "test@test.com", "test");
        testCart1 = new Cart(user);
        testCart2 = new Cart(user);
        testCartDaoMem = CartDaoMem.getInstance();
        testCartDaoMem.add(testCart1);
        testCartDaoMem.add(testCart2);
    }

    @AfterEach
    void cleanUp() {
        testCartDaoMem.remove(2);
        testCartDaoMem.remove(1);
    }

    @org.junit.jupiter.api.Test
    void add_Cart_increaseStorageSize() {
        assertEquals(2, testCartDaoMem.getSize());
    }


    @org.junit.jupiter.api.Test
    void find_searchForNotExistingIndex_returnsNull() {
        assertNull(testCartDaoMem.find(-1));
    }

    @org.junit.jupiter.api.Test
    void remove_removeItem_return() {
        testCartDaoMem.remove(2);
        assertNull(testCartDaoMem.find(2));
    }

    @org.junit.jupiter.api.Test
    void getSize() {
        assertEquals(2, testCartDaoMem.getSize());
    }
}