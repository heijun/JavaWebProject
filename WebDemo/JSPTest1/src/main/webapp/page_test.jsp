<%--
  Created by IntelliJ IDEA.
  User: yst
  Date: 2022/8/11
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>testbean</title>
</head>

<body>
<jsp:useBean id="person" scope="page" class="com.jsptest1.Person"></jsp:useBean>
<%=person.getName()%>
<%person.setName("LL");%>
</body>
</html>
