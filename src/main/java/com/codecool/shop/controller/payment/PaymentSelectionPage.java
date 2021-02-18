package com.codecool.shop.controller.payment;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/pay"})
public class PaymentSelectionPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        CartDao cartDataStore = CartDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("cartTotal", cartDataStore.find(1).getCartSubtotal() * 100);

        req.getParameter("lastName");
        req.getParameter("lastName");
        req.getParameter("email");
        req.getParameter("address");
        req.getParameter("address2");
        req.getParameter("country");
        req.getParameter("state");
        req.getParameter("zip");
//        if (req.getParameter("paymentMethod").equals("paypal"))
        engine.process("checkout/pay.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }


}