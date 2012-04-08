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
