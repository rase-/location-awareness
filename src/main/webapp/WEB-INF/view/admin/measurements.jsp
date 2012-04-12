<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Measurement management page</title>
    </head>
    <body>
        <h1>${place.name} measurements</h1>
        <h2>Add a new measurement</h2>
        <form:form commandName="measurementform" actions="${pageContext.request.contextPath}/admin/places/${place.id}/measurements" method="POST">
            <form:textarea path="measurements" rows = "5" cols = "30"/><form:errors path="measurements" /> <br />
            <input type="submit" />
        </form:form>
        <p>For each row you need to input a valid mac address and an integer (if positive without + sign) separated by a space. Only the first to arguments per row are used.</p>
        <h2>Measurements</h2>
        <c:forEach var="measurement" items="${place.measurements}">
            <p><a href=<c:url value="/admin/places/${place.id}/measurements/${measurement.id}" />>${measurement.measureTime}</a></p>
        </c:forEach>
    </body>
</html>
