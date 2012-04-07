<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User friend request page</title>
    </head>
    <body>
        <!--
        <h1>Friend requests sent to you by</h1>
        <p>
        <c:forEach var="request" items="${pendingRequests}">
            username: ${request.username}, name: ${request.name} <br />
        </c:forEach>
        </p>
        -->
        <h1>Send friend requests</h1>
        <c:forEach var="user" items="${unadded}">
            username: ${user.username}, name: ${user.name} <a href=<c:url value = "/user/friendRequests/${user.id}" />>Add as friend</a><br />
        </c:forEach>
    </body>
</html>
