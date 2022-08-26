package com.books.servlet;

import com.books.service.UserService;
import com.books.service.impl.UserServiceImpl;
import com.books.servlet.utils.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;


import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    UserService userService;
    @Override
    public void init() throws ServletException {
        userService=new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        Context context = new Context();
        if(req.getSession().getAttribute("login-failure") != null){
            context.setVariable("failure",true);
            req.getSession().removeAttribute("login-failure");
        }
        if(req.getSession().getAttribute("user") != null) {
            resp.sendRedirect("index");
            return;
        }
        ThymeleafUtil.process("login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String remember = req.getParameter("remember-me");
        if(userService.auth(username,password,req.getSession())) {
            resp.sendRedirect("index");
        }
            else {
                req.getSession().setAttribute("login-failure", new Object());
                this.doGet(req, resp);
            }


    }
}
