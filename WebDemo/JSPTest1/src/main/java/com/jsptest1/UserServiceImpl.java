package com.jsptest1;

import com.jsptest1.Utils.MybatisUtil;
import com.jsptest1.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserServiceImpl implements UserService{
    public boolean auth(HttpSession session, HttpServletRequest req) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try (
                SqlSession sqlSession = MybatisUtil.getSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.getUser(username, password);
            if (user == null) return false;
            session.setAttribute("user", user);
            return true;
        }
    }
}
