<%-- 
    Document   : project
    Created on : Mar 3, 2012, 12:27:29 PM
    Author     : tonykovanen
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Project view</title>
    </head>
    <body>
        <h1>${project.name}</h1>
        <p>${project.description}</p>
    </body>
</html>
