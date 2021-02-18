package com.codecool.shop.controller.payment;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.stripe.Stripe;
import com.stripe.model.Charge;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/checkout-cart"})
public class ChargeController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");// Set response content type
        String title = "Stripe payment";
        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        try {

            Stripe.apiKey = getServletContext().getInitParameter("stripeSecretKey");
            Map<String, String[]> httpParameters = request.getParameterMap();

            //esctract request parameters
            Map<String, Object> userBillingData = new HashMap<>();
            String userEmail = httpParameters.get("stripeEmail")[0];
            userBillingData.put("email", userEmail);
            userBillingData.put("stripeToken", httpParameters.get("stripeToken")[0]);

            Map<String, Object> params = new HashMap<>();
            params.put("amount", "999"); // or get from request
            params.put("currency", "eur");  // or get from request
            params.put("source", userBillingData.get("stripeToken"));// or get from request
            params.put("receipt_email", userEmail);

            Charge charge;

            charge = Charge.create(params);

            String chargeID = charge.getId();


            CartDao cartDataStore = CartDaoMem.getInstance();
            cartDataStore.find(1).emptyCart();
            response.sendRedirect("/");

            out.println(docType
                    + "<html>\n"
                    + "<head><title>" + title + "</title></head>\n"
                    + "<body bgcolor = \"#f0f0f0\">\n"
                    + "<h1 align = \"center\">" + title + "</h1>\n"
                    + "- PAYMENT OK. chargeID " + chargeID + " -"
                    + "</body></html>"
            );


        } catch (Exception ex) {
            out.println(docType
                    + "<html>\n"
                    + "<head><title>" + title + "</title></head>\n"
                    + "<body bgcolor = \"#f0f0f0\">\n"
                    + "<h1 align = \"center\">" + ex + "</h1>\n"
                    + "- ERROR - "
                    + "</body></html>"
            );
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}