<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User friends</title>
    </head>
    <body>
        <nav>
            <a href=<c:url value="/home" />>Site Home</a>
            <a href=<c:url value="/user/home" />>User Home</a>
            <a href=<c:url value="/user/friends" />>Friends</a>
            <a href=<c:url value="/user/history" />>History</a>
            <a href=<c:url value="/j_spring_security_logout" />>Logout</a>
        </nav>
        <h1>Friend list</h1>
        <c:forEach var="friend" items="${friends}">
            <p><a href=<c:url value="/user/friends/${friend.id}" />>${friend.username}: ${friend.name}</a></p>
        </c:forEach>
            <a href=<c:url value="/user/friendRequests" />>Friend request page</a>
    </body>
</html>
