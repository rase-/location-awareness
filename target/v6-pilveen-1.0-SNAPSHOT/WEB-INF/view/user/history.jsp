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
            <input name="submit" type="submit" />
        </form:form>
        <p>For each row you need to input a valid mac address and an integer (if positive without + sign) separated by a space. Only the first two arguments per row are used.</p>
        <h2>Last 10 locations</h2>
        <c:forEach var="history" items="${history}">
            <p>${history.measureTime} near <a href=<c:url value="/user/places/${history.place.id}" />>${history.place.name}</a></p>
        </c:forEach>
    </body>
</html>
