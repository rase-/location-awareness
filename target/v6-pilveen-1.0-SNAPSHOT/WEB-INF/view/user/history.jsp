<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User history</title>
    </head>
    <body>
        <h1>History</h1>
        <h2>Send measurement</h2>
        <form:form commandName="measurementform" actions="${pageContext.request.contextPath}/admin/places/${place.id}/measurements" method="POST">
            <form:textarea path="measurements" rows = "5" cols = "30"/><form:errors path="measurements" /> <br />
            <input type="submit" />
        </form:form>
        <h2>Last 10 locations</h2>
        <c:forEach var="place" items="${history}">
            <p><a href=<c:url value="/user/places/${place.id}" />>${place.name}</a></p>
        </c:forEach>
    </body>
</html>
