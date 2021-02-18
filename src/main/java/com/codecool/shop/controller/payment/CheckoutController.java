package com.codecool.shop.controller.payment;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Cart;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        CartDao cartDataStore = CartDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());


        Cart cart = cartDataStore.find(1);

        String cartTotalWithCurrency = cart.getCartSubtotal() + " " + cart.getItems().get(0).getProduct().getDefaultCurrency().toString();
        context.setVariable("cartProducts", cart.getItems());
        context.setVariable("cartTotal", cartTotalWithCurrency);
        context.setVariable("cartItemsCount", cart.getSize());
        context.setVariable("stripeToken", "pk_test_51I6ONaH41PcOPuaUaWENc7iHUH5YWEGbV9Y2u6lQ1flqBzosgXJquEraMQkoT7Qk0Zl7uAfk00fFEdg9iZBACekX00dKizyLUY");

        engine.process("checkout/checkout.html", context, resp.getWriter());
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        doGet(req, resp);

    }

}