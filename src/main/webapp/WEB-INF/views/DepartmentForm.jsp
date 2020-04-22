<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
            <li class="nav-item"><a class="nav-link" href="/employee/">Employee</a></li>
            <li class="nav-item active"><a class="nav-link" href="">Department</a></li>
            <li class="nav-item"><a class="nav-link" href="/project/">Project</a></li>
            <li class="nav-item dropdown disabled">
                <a class="nav-link dropdown-toggle disabled" href="" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Sort
                </a>
            </li>
        </ul>
    </div>
</nav>
<div align="center">
    <h4 style="padding-top: 30px; padding-bottom: 20px">New/Edit department</h4>
    <form:form action="save" method="post" modelAttribute="department">
        <form:hidden path="id"/>
        <div class="form-group" style="width: 50%">
            <input required type="text" class="form-control" placeholder="Enter department name" name="name" value="${department.name}">
        </div>
        <button type="submit" class="btn btn-outline-primary" value="Save">Submit</button>
    </form:form>
</div>
</body>
</html>
