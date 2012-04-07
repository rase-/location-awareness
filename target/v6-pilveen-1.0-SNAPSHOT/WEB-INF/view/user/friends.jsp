<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User friends</title>
    </head>
    <body>
        <h1>Friend list</h1>
        <c:forEach var="friend" items="${friends}">
            <p>${friend.username}: ${friend.name}</p>
        </c:forEach>
            <a href=<c:url value="/user/friendRequests" />>Friend request page</a>
    </body>
</html>
