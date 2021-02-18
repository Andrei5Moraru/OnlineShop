package com.codecool.shop.controller;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.jdbc.*;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Integer.parseInt;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    ProductDao productDataStore;
    ProductCategoryDao productCategoryDataStore;
    CartDao cartDataStore;
    UserDao userDataStore;
    SupplierDao supplierDataStore;


//    String storageType = "MEMORY";
    String storageType = "DATABASE";

    @Override
    public void init() {
        switch (storageType) {
            case "MEMORY":
                productDataStore = ProductDaoMem.getInstance();
                productCategoryDataStore = ProductCategoryDaoMem.getInstance();
                cartDataStore = CartDaoMem.getInstance();
                userDataStore = UserDaoMem.getInstance();
                supplierDataStore = SupplierDaoMem.getInstance();
                break;
            case "DATABASE":
                productDataStore = ProductDaoJdbc.getInstance();
                productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
                cartDataStore = CartDaoMem.getInstance();
                userDataStore = UserDaoJdbc.getInstance();
                supplierDataStore = SupplierDaoJdbc.getInstance();
                break;
        }
//        user = userDataStore.find(1);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        ProductCategory defaultCategory = new ProductCategory("", "", "");
        Supplier defaultSupplier = new Supplier("", "");

        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());
        try {
            context.setVariable("cart", cartDataStore.find(1).getItems());
        } catch (Exception ignored) {
        }

        String selectedCategory = req.getParameter("categoryInput");
        String selectedSupplier = req.getParameter("supplierInput");

        if (selectedCategory == null && selectedSupplier == null) {

            context.setVariable("products", productDataStore.getAll());
            context.setVariable("selectedCategory", defaultCategory);
            context.setVariable("selectedSupplier", defaultSupplier);

        } else if (selectedCategory != null) {

            ProductCategory filteredCategory = productCategoryDataStore.find(parseInt(selectedCategory));
            context.setVariable("selectedCategory", filteredCategory);
            context.setVariable("selectedSupplier", defaultSupplier);
            context.setVariable("products", productDataStore.getBy(filteredCategory));


        } else {
            Supplier filteredSupplier = supplierDataStore.find(parseInt(selectedSupplier));
            context.setVariable("selectedCategory", defaultCategory);
            context.setVariable("selectedSupplier", filteredSupplier);
            context.setVariable("products", productDataStore.getBy(filteredSupplier));
        }

        String searchedProduct = req.getParameter("search");

        if (searchedProduct != null && !searchedProduct.equals("")) {
            context.setVariable("products", productDataStore.getBy(searchedProduct));
        }
        try {
            context.setVariable("productsInCart", cartDataStore.find(1).getSize());
        } catch (NullPointerException e) {
            context.setVariable("productsInCart", "0");
        }


        engine.process("product/index.html", context, resp.getWriter());
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}