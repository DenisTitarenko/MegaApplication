<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="https://github.com/DenisTitarenko/MegaApplication"><h3>MegaApplication</h3></a>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active"><a class="nav-link" href="/">Employee</a></li>
            <li class="nav-item"><a class="nav-link" href="/department/">Department</a></li>
            <li class="nav-item"><a class="nav-link" href="/project/">Project</a></li>
            <li class="nav-item dropdown disabled">
                <a class="nav-link dropdown-toggle disabled" href="" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Sort
                </a>
            </li>
        </ul>
    </div>
    <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/logout"/>">Sign out</a>
            </li>
        </ul>
    </div>
</nav>
<div align="center">
    <h4 style="padding-top: 30px; padding-bottom: 20px">New/Edit employee</h4>
    <form:form action="save" method="post" modelAttribute="employee">
            <form:hidden path="id"/>
            <div class="form-group" style="width: 50%">
                <input required type="text" class="form-control" placeholder="Enter name" name="name" value="${employee.name}">
            </div>

            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sex" id="m" value="M" >
                <label class="form-check-label" for="m">Male &nbsp;&nbsp;&nbsp;&nbsp;</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sex" id="f" value="F">
                <label class="form-check-label" for="f">Female &nbsp;&nbsp;&nbsp;&nbsp;</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="sex" id="other" value="OTHER" >
                <label class="form-check-label" for="other">Other</label>
            </div>

            <div class="form-group" style="width: 50%; padding-top: 20px">
                <form:select class="form-control" path="departmentName" items="${departments}"/>
            </div>

            <div class="form-group" style="width: 50%">
                <input required type="text" class="form-control" placeholder="Enter position" name="position" value="${employee.position}">
            </div>

            <div class="form-group" style="width: 50%">
                <form:select multiple="true" class="form-control" path="projects" items="${projects}"/>
            </div>

            <div class="form-group" style="width: 50%">
                <input required type="text" class="form-control" placeholder="Enter salary" name="salary" value="${employee.salary}">
            </div>

            <div class="form-group" style="width: 50%">
                <input required type="text" class="form-control" placeholder="Enter date of hire" name="dateOfHire">
            </div>

            <button type="submit" class="btn btn-outline-primary" value="Save">Submit</button>
    </form:form>
</div>
</body>
</html>