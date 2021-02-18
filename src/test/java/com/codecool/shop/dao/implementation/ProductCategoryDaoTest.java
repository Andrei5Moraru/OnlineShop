package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoTest {

    ProductCategoryDaoMem testCategoryDaoMem;

    @BeforeEach
    void setup() {

        ProductCategory tablet = new ProductCategory("tablet", "", "");
        ProductCategory laptop = new ProductCategory("tablet", "", "");
        testCategoryDaoMem = ProductCategoryDaoMem.getInstance();
        testCategoryDaoMem.add(tablet);
        testCategoryDaoMem.add(laptop);
    }

    @AfterEach
    void cleanUp() {
        testCategoryDaoMem.remove(2);
        testCategoryDaoMem.remove(1);
    }

    @Test
    void add_Category_increaseStorageSize() {
        assertEquals(2, testCategoryDaoMem.getAll().size());
    }


    @Test
    void find_searchForNotExistingIndex_returnsNull() {
        assertNull(testCategoryDaoMem.find(-1));
    }


    @Test
    void remove_removeCategory_return() {
        testCategoryDaoMem.remove(2);
        assertNull(testCategoryDaoMem.find(2));
    }

    @Test
    void getAll_return() {
        assertEquals(2, testCategoryDaoMem.getAll().size());
    }

}