package com.codecool.shop.controller;


import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Cart;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart"})
public class CartPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        CartDao cartDataStore = CartDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        try {
            Cart cart = cartDataStore.find(1);
            context.setVariable("cartContent", cart);
            String cartTotalWithCurrency = cart.getCartSubtotal() + " " + cart.getItems().get(0).getProduct().getDefaultCurrency().toString(); // Taking the default currency from the first LineItem in cart (for now)
            context.setVariable("cartTotal", cartTotalWithCurrency);
        } catch (IndexOutOfBoundsException | NullPointerException ex) {
            resp.sendRedirect(req.getContextPath() + "/");
        }
        engine.process("/cart.html", context, resp.getWriter());
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
