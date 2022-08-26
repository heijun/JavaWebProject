package com.example.servlet;

import com.example.mapper.UserMapper;
import com.example.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.Map;

@WebServlet(value = "/login",loadOnStartup = 1)
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //首先设置一下响应类型
        resp.setContentType("text/html;charset=UTF-8");
        //获取POST请求携带的表单数据
        Map<String, String[]> map = req.getParameterMap();
        //判断表单是否完整
        if(map.containsKey("username") && map.containsKey("password")) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            //登陆校验（待完善）
            try (SqlSession sqlSession = factory.openSession(true)){
                UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                User user = mapper.getUser(username, password);
                //判断用户是否登陆成功，若查询到信息则表示存在此用户
                if(user != null){
                    resp.getWriter().write("登陆成功！");
                }else {
                    resp.getWriter().write("登陆失败，请验证您的用户名或密码！");
                }
            }
            //权限校验（待完善）
        }else {
            resp.getWriter().write("错误，您的表单数据不完整！");
        }
    }

    SqlSessionFactory factory;
    @SneakyThrows
    @Override
    public void init() throws ServletException {
            factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
    }
}
