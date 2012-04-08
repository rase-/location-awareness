<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin home</title>
    </head>
    <body>
        <h1>Welcome ${user.name}</h1>
        <p><a href=<c:url value="/admin/places"/>>Manage places</a></p>
        <p><a href=<c:url value="/j_spring_security_logout"/>>logout</a></p>
    </body>
</html>
