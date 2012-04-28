<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User friend request page</title>
    </head>
    <body>
        <nav>
            <a href=<c:url value="/home" />>Site Home</a>
            <a href=<c:url value="/user/home" />>User Home</a>
            <a href=<c:url value="/user/friends" />>Friends</a>
            <a href=<c:url value="/user/history" />>History</a>
            <a href=<c:url value="/j_spring_security_logout" />>Logout</a>
        </nav>
        <h1>Friend requests sent to you by</h1>
        <p>
        <c:forEach var="request" items="${friendshipRequests}">
            username: ${request.sender.username}, name: ${request.sender.name} <a href=<c:url value = "/user/friendRequests/${request.sender.id}" />>Add as friend</a><br />
        </c:forEach>
        </p>
        
        <h1>Send friend requests</h1>
        <c:forEach var="user" items="${unadded}">
            username: ${user.username}, name: ${user.name} <a href=<c:url value = "/user/friendRequests/${user.id}" />>Add as friend</a><br />
        </c:forEach>
    </body>
</html>
