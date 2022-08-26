package com.books.service.impl;

import com.books.dao.UserMapper;
import com.books.entity.User;
import com.books.service.UserService;
import com.books.servlet.utils.MybatisUtil;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;



public class UserServiceImpl implements UserService {

    @Override
    public boolean auth(String username, String password, HttpSession session) {
        try (SqlSession sqlSession = MybatisUtil.getSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.getUser(username, password);
            if(user == null) return false;
            session.setAttribute("user", user);
            return true;
        }
    }
}
