<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <title>MegaApp</title>
</head>
<body>
<jsp:include page="fragments/header.jsp" />
<div align="center">
    <h4 style="padding-top: 30px; padding-bottom: 20px">New/Edit project</h4>
    <form:form action="save" method="post" modelAttribute="project">
        <form:hidden path="id"/>
        <div class="form-group" style="width: 50%">
            <input required type="text" class="form-control" placeholder="Enter project name" name="name" value="${project.name}">
        </div>
        <button type="submit" class="btn btn-outline-primary" value="Save">Submit</button>
    </form:form>
</div>
</body>
</html>
