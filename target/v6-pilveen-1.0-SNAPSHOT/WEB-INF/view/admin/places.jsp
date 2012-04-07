<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Places</title>
    </head>
    <body>
        <h1>Places</h1>
        <c:forEach var="place" items="${places}">
            <p>${place.name}</p>
        </c:forEach>
    </body>
</html>
