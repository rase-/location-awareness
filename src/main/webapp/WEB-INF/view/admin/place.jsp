<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Place information</title>
    </head>
    <body>
        <h1>${place.name}</h1>
        <p><a href=<c:url value="/admin/places/${place.id}/measurements" />>Manage measurements</a></p>
        <h1>Change place information</h1>
        <form:form commandName="edit" action="${pageContext.request.contextPath}/admin/places/${place.id}" method="POST">
            <form:input path="name" value="${place.name}" /><form:errors path="name" /> <br />
            <input type="submit" />
        </form:form>
        <form:form commandName="delete" action="${pageContext.request.contextPath}/admin/places/${place.id}" method="DELETE">
            <input type="submit" value="Delete" />
        </form:form>
    </body>
</html>
