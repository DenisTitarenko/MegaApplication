<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <title>MegaApp</title></head>
</head>
<body>
<jsp:include page="../fragments/header.jsp" />
<div class="container" align="center" >
    <div class="starter-template">
        <h1 style="padding-top: 30px; padding-bottom: 20px">Error ${error.status.value()} - ${error.status.name().toString().replaceAll("_", " ").toLowerCase()}</h1>
        <h3 style="padding-bottom: 60px">Something went wrong with the client-side. Check your request or retry later.</h3>
        <a href="/"><button type="button" class="btn btn-outline-dark btn-lg">Back to main page</button></a>
    </div>
</div>
</body>
</html>
