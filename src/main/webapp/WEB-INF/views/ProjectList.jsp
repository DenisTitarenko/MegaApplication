<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
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
            <li class="nav-item"><a class="nav-link" href="/">Employee</a></li>
            <li class="nav-item"><a class="nav-link" href="/department/">Department</a></li>
            <li class="nav-item active"><a class="nav-link" href="/project/">Project</a></li>
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
                <a class="nav-link" href="<c:url value="/logout"/>">
                    [<%out.print(SecurityContextHolder.getContext().getAuthentication().getName());%>] Sign out
                </a>
            </li>
        </ul>
    </div>
</nav>
<div align="center">
    <table class="table table-bordered">
        <thead style="background-color: darkgray">
        <tr>
            <th class="text-center" style="width: 33%" scope="col">Project</th>
            <th class="text-center" style="width: 33%" scope="col">Employees</th>
            <th class="text-center" scope="col">Action</th>
        </tr>
        </thead>
        <c:forEach var="project" items="${projects}">
            <tr>
                <th class="align-middle">${project.name}</th>
                <td class="align-middle text-center">${project.employees.size()}&nbsp;&nbsp;&nbsp;&nbsp;
                    <div class="btn-group dropright">
                        <button type="button" class="btn btn-outline-info btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Show
                        </button>
                        <div class="dropdown-menu" style="width: 600px; visibility: hidden">
                            <table class="table table-bordered table-sm" style="visibility: visible">
                                <thead style="background-color: lightgrey">
                                <tr>
                                    <th class="text-center" scope="col">Name</th>
                                    <th class="text-center" scope="col">Sex</th>
                                    <th class="text-center" scope="col">Department</th>
                                    <th class="text-center" scope="col">Position</th>
                                    <th class="text-center" scope="col">Salary</th>
                                    <th class="text-center" scope="col">Date of hire</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="employee" items="${project.employees}">
                                    <tr style="background-color: lightgrey">
                                        <td>${employee.name}</td>
                                        <td>${employee.sex}</td>
                                        <td>${employee.department.name}</td>
                                        <td>${employee.position}</td>
                                        <td>${employee.salary}</td>
                                        <td>${employee.dateOfHire}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </td>
                <td class="align-middle text-center">
                    <a href="update?name=${project.name}"><button type="button" class="btn btn-outline-warning btn-sm">Edit</button></a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="delete?name=${project.name}"><button type="button" class="btn btn-outline-danger btn-sm">Delete</button></a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="create"><button type="button" class="btn btn-outline-primary">Add new project</button></a>
</div>
</body>
</html>
