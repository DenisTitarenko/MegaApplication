<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <title>MegaApp</title></head>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="https://github.com/DenisTitarenko/MegaApplication"><h3>MegaApplication</h3></a>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item"><a class="nav-link" href="/">Employee</a></li>
            <li class="nav-item"><a class="nav-link" href="/department/">Department</a></li>
            <li class="nav-item"><a class="nav-link" href="/project/">Project</a></li>
            <li class="nav-item dropdown disabled">
                <a class="nav-link dropdown-toggle disabled" href="" id="navbarDropdownMenuLink" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    Sort
                </a>
            </li>
        </ul>
    </div>
    <div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/logout"/>">
                    [<%out.print(SecurityContextHolder.getContext().getAuthentication().getName());%>] Sign out
                </a>
            </li>
        </ul>
    </div>
</nav>
<div class="container" align="center">
    <div class="starter-template">
        <h1 style="padding-top: 30px; padding-bottom: 20px">Error ${error.status}</h1>
        <h2 style="padding-bottom: 60px">${error.message}</h2>
        <a href="/"><button type="button" class="btn btn-outline-dark btn-lg">Back to main page</button></a>
    </div>
</div>
</body>
</html>
