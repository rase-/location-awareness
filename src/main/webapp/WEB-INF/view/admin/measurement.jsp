<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Measurement info</title>
    </head>
    <body>
        <nav>
            <a href=<c:url value="/home" />>Site Home</a>
            <a href=<c:url value="/admin/home" />>Admin Home</a>
            <a href=<c:url value="/admin/places" />>Places</a>
            <a href=<c:url value="/admin/users" />>Users</a>
            <a href=<c:url value="/j_spring_security_logout" />>Logout</a>
        </nav>
        <h1>Measurement on ${measurement.measureTime}</h1>
        <h2>Measurement details</h2>
        <c:forEach var="fingerprint" items="${measurement.fingerprints}">
            ${fingerprint.macAddress} ${fingerprint.signalStrength} <br />
        </c:forEach>
        <form:form commandName="delete" action="${pageContext.request.contextPath}/admin/places/${place.id}/measurements/${measurement.id}" method="DELETE">
            <input type="submit" value="Delete" />
        </form:form>
    </body>
</html>
