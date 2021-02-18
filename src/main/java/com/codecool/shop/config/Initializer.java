package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.User;
import org.apache.log4j.BasicConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        UserDao userDataStore = UserDaoMem.getInstance();

        BasicConfigurator.configure();
        String currency = "USD";

        User user = new User("spaf", "spafiii@.com", "qwerty");
        try {
            userDataStore.add(user);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //setting up a new supplier
        Supplier apple = new Supplier("Apple", "");
        supplierDataStore.add(apple);
        Supplier amazon = new Supplier("Amazon", "");
        supplierDataStore.add(amazon);
        Supplier samsung = new Supplier("Samsung", "");
        supplierDataStore.add(samsung);
        Supplier lenovo = new Supplier("Lenovo", "");
        supplierDataStore.add(lenovo);

        //setting up a new product category
        ProductCategory smartphone = new ProductCategory("Smartphone","IT","");
        productCategoryDataStore.add(smartphone);
        ProductCategory laptop = new ProductCategory("Laptop","IT","");
        productCategoryDataStore.add(laptop);
        ProductCategory tablet = new ProductCategory("Tablet","IT","");
        productCategoryDataStore.add(tablet);
        ProductCategory other = new ProductCategory("Other","IT","");
        productCategoryDataStore.add(other);


        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire",89.5,currency,"10.1\" 1080p full HD display; 32 or 64 GB of internal storage (add up to 512 GB with microSD)",tablet,amazon,4));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700",479.00,currency,"The Ideapad Miix 700 is not just recognizably different, it also gives you new disruptive ways to work and play.",tablet,lenovo,6));
        productDataStore.add(new Product("Amazon Fire HD 8",49.00,currency,"Fire HD 8 is fast and responsive, with a HD display, 2X the storage, 2 GB of RAM, and a new 2.0 GHz quad-core processor.",tablet,amazon,10));
        productDataStore.add(new Product("Huawei Smartwatch",199.00,currency,"Huawei Watch GT 2e SmartWatch (2020), 46 mm, black body, red silicone strap, waterproof, HR sensor.",other,amazon,9));
        productDataStore.add(new Product("Apple Iphone 12",799.05,currency,"Apple iPhone 12, 128GB, 5G, Black with Super Retina XDR display and an Apple A14 Bionic CPU.",smartphone,apple,8));
        productDataStore.add(new Product("Samsung S20",699.50,currency,"Samsung Galaxy S20, Dual SIM, 128GB, 12GB RAM, 5G, Cloud Pink with Dynamic AMOLED display.",smartphone,samsung,7));
        productDataStore.add(new Product("Huawei P40",499.00,currency,"Huawei P40, Dual SIM, 128GB, 8GB RAM, 5G, Black with 32mpx camera and Super AMOLED display.",smartphone,amazon,5));
        productDataStore.add(new Product("Lenovo Idea Pad 3",699.00,currency,"Lenovo IdeaPad 3 15ARH05 with AMD Ryzen 7 4800H CPU, Full HD, 8GB, 512GB SSD,Onyx Black.",laptop,lenovo,2));
        productDataStore.add(new Product("Apple MacBook Air",1199.00,currency,"APPLE MacBook Air 13, Intel Core i3 until 3.2GHz, IPS Retina, 8GB, SSD 256GB, Intel Iris Plus Graphics.",laptop,apple,2));


    }
}