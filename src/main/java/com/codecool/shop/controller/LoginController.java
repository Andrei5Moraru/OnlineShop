package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.jdbc.UserDaoJdbc;
import com.codecool.shop.model.User;
import com.codecool.shop.util.Encrypt;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(urlPatterns = {"/login"}, loadOnStartup = 4)
public class LoginController extends HttpServlet {

    UserDao userDao = UserDaoJdbc.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("/login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String uname = req.getParameter("username");
        String pass = req.getParameter("password");

        if (isValidUser(uname) && isValidPass(uname, pass)) {
            User user = userDao.find(uname);
            session.setAttribute("user", user);
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/login?error");
        }
    }

    private boolean isValidUser(String username) {
        return userDao.find(username) != null;
    }

    private boolean isValidPass(String username, String pass) {
        Encrypt encrypt = Encrypt.getInstance();
        try {
            String encryptedPass = encrypt.encrypt(pass);
            return ((UserDaoJdbc) userDao).getPass(username).equals(encryptedPass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}