<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <h1>Welcome!</h1>
        <p>Are you a <a href=<c:url value="/user/home" />>user</a> or an <a href=<c:url value="/admin/home" />>admin</a>?</p>
    </body>
</html>
