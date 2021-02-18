package com.codecool.shop.controller;


import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Integer.parseInt;

@WebServlet(urlPatterns = {"/add-to-cart"})
public class CartController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());


        CartDao cartDataStore = CartDaoMem.getInstance();
        UserDao userDataStore = UserDaoMem.getInstance();
        ProductDao productDataStore = ProductDaoMem.getInstance();

        Cart cart;

        User user = userDataStore.find(1);
        if (cartDataStore.getSize() == 0) {
            cart = new Cart(user);
        } else {
            cart = cartDataStore.find(1);
        }

        String productId = req.getParameter("addedProduct");
        LineItem item = new LineItem(productDataStore.find(parseInt(productId)));

        cart.addItem(item);

        if (cartDataStore.getSize() == 0) {
            cartDataStore.add(cart);
        }


        resp.sendRedirect(req.getContextPath() + "/");

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
