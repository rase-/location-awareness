<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Friend information page</title>
    </head>
    <body>
        <h1>${friend.name}</h1>
        <p>Username: ${friend.username}</p>
        <h2>Friends</h2>
        <p>
        <c:forEach var="friendsFriend" items="${friend.friends}">
            Username: ${friendsFriend.username}, name: ${friendsFriend.name} <br />
        </c:forEach>
        </p>
        <h2>History</h2>
        <p>
        <c:forEach var="occurrence" items="${friend.history}">
            ${occurrence.measureTime}: <a href=<c:url value="/user/places/${occurrence.place.id}" />>${occurrence.place.name}</a> <br />
        </c:forEach>
        </p>
    </body>
</html>
