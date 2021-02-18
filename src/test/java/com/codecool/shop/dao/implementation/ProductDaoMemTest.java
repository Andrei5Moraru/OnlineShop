package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoMemTest {

    ProductDaoMem testProductDaoMem;
    Product product1;
    Product product2;
    Supplier nullSupplier;
    Supplier apple = new Supplier("Apple", "");
    ProductCategory tablet = new ProductCategory("Tablet", "IT", "");
    ProductCategory nullCategory;


    @BeforeEach
    void setup() {

        Supplier lenovo = new Supplier("Lenovo", "");
        product1 = new Product("Amazon Fire", 89.5, "USD", "10.1\" 1080p full HD display; 32 or 64 GB of internal storage (add up to 512 GB with microSD)", tablet, apple, 4);
        product2 = new Product("Amazon Fire HD", 811, "USD", "second product description", tablet, lenovo, 1);
        testProductDaoMem = ProductDaoMem.getInstance();
        testProductDaoMem.add(product1);
        testProductDaoMem.add(product2);
    }

    @AfterEach
    void cleanUp() {
        testProductDaoMem.remove(2);
        testProductDaoMem.remove(1);
    }

    @Test
    void add_Product_increaseStorageSize() {
        assertEquals(2, testProductDaoMem.getAll().size());
    }


    @Test
    void find_searchForNotExistingIndex_returnsNull() {
        assertNull(testProductDaoMem.find(-1));
    }

    @Test
    void remove_removeProduct_return() {
        testProductDaoMem.remove(2);
        assertNull(testProductDaoMem.find(2));
    }

    @Test
    void getAll_return() {
        assertEquals(2, testProductDaoMem.getAll().size());
    }

    @Test
    void getBy_NonExistingSupplier_returnsEmptyList() {
        assertTrue(testProductDaoMem.getBy(nullSupplier).isEmpty());
    }

    @Test
    void getBy_NonExistingCategory_returnsEmptyList() {
        assertTrue(testProductDaoMem.getBy(nullCategory).isEmpty());
    }

    @Test
    void getBy_Supplier_return() {
        assertEquals(product1, testProductDaoMem.getBy(apple).get(0));
    }

    @Test
    void getBy_ProductCategory_return() {
        assertEquals(product1, testProductDaoMem.getBy(tablet).get(0));
    }

}