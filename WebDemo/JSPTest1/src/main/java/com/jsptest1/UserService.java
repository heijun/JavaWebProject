package com.jsptest1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface UserService {
    public boolean auth(HttpSession session, HttpServletRequest req);
}
