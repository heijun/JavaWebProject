package com.jsptest1.servlet;

import com.jsptest1.User;
import com.jsptest1.UserServiceImpl;
import com.jsptest1.Utils.MybatisUtil;
import com.jsptest1.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
@Slf4j
@WebServlet("/login")
public class TestServlet extends HttpServlet {


    UserServiceImpl userService;

    TemplateEngine engine;
    @Override
    public void init() throws ServletException {
        userService= new UserServiceImpl();
        engine = new TemplateEngine();
        ClassLoaderTemplateResolver r = new ClassLoaderTemplateResolver();
        engine.setTemplateResolver(r);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //首先设置一下响应类型
        resp.setContentType("text/html;charset=GBK");

        //获取POST请求携带的表单数据
        Map<String, String[]> map = req.getParameterMap();

        HttpSession session=req.getSession();

        //判断表单是否完整
        if(userService.auth(session,req)) {
            if ((map.get("username").equals("")==false && map.get("password").equals("")) == false) {
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                if (map.containsKey("remember-me")) {   //若勾选了勾选框，那么会此表单信息
                    Cookie cookie_username = new Cookie("username", username);
                    cookie_username.setMaxAge(30);
                    Cookie cookie_password = new Cookie("password", password);
                    cookie_password.setMaxAge(30);
                    resp.addCookie(cookie_username);
                    resp.addCookie(cookie_password);
                }

                resp.sendRedirect("index");

                //权限校验（待完善）
            } else {
                resp.getWriter().write("错误，您的表单数据不完整！");
                resp.sendRedirect("login");
            }
        }else {

            resp.getWriter().write("用户名或密码不正确");
            resp.sendRedirect("login");
        }

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("GBK");
        resp.setContentType("text/html;charset=GBK");
        Context context = new Context();
        context.setVariable("list", Arrays.asList("伞兵一号的故事", "倒一杯卡布奇诺", "玩游戏要啸着玩", "十七张牌前的电脑屏幕"));

        Cookie[] cookies = req.getCookies();
        if(req.getSession().getAttribute("user")!=null) {
            if (cookies != null) {
                String username = null;
                String password = null;
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("username")) username = cookie.getValue();
                    if (cookie.getName().equals("password")) password = cookie.getValue();
                }
                if (username != null && password != null) {

                    resp.sendRedirect("index");
                    return;   //直接返回
                }

            }
        }
        engine.process("test.html", context, resp.getWriter());
    }


}
