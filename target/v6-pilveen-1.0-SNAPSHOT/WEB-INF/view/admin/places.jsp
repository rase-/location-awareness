<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Places</title>
    </head>
    <body>
        <h1>Add a new place</h1>
        <form:form commandName="place" action="${pageContext.request.contextPath}/admin/places" method = "POST">
            Name of the place: <form:input path="name" /><form:errors path="name" /> <br />
            Place description: <form:textarea path="description" /><form:errors path="description" /> <br />
            <input type="submit" />
        </form:form>
        <h1>Places</h1>
        <c:forEach var="place" items="${places}">
            <p><a href=<c:url value="/admin/places/${place.id}" />>${place.name}</a></p>
        </c:forEach>
    </body>
</html>
