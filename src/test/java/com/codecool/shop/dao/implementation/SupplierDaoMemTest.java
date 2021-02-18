package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoMemTest {

    SupplierDaoMem testSupplierDaoMem;

    @BeforeEach
    void setup() {

        Supplier lenovo = new Supplier("Lenovo", "");
        Supplier apple = new Supplier("Apple", "");
        testSupplierDaoMem = SupplierDaoMem.getInstance();
        testSupplierDaoMem.add(lenovo);
        testSupplierDaoMem.add(apple);
    }

    @AfterEach
    void cleanUp() {
        testSupplierDaoMem.remove(2);
        testSupplierDaoMem.remove(1);
    }

    @Test
    void add_Supplier_increaseStorageSize() {
        assertEquals(2, testSupplierDaoMem.getAll().size());
    }


    @Test
    void find_searchForNotExistingIndex_returnsNull() {
        assertNull(testSupplierDaoMem.find(-1));
    }


    @Test
    void remove_removeSupplier_return() {
        testSupplierDaoMem.remove(2);
        assertNull(testSupplierDaoMem.find(2));
    }

    @Test
    void getAll_return() {
        assertEquals(2, testSupplierDaoMem.getAll().size());
    }

}