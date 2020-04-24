<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <li class="nav-item active"><a class="nav-link" href="">Employee</a></li>
            <li class="nav-item"><a class="nav-link" href="/department/">Department</a></li>
            <li class="nav-item"><a class="nav-link" href="/project/">Project</a></li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Sort
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="/employee/">Show employees without any sorting</a>
                    <a class="dropdown-item" href="samesalary">Show employees with same salary</a>
                    <a class="dropdown-item" href="grouped">Show employees grouped by position and date</a>
                </div>
            </li>
        </ul>
    </div>
</nav>
<div align="center">
    <table class="table table-bordered">
        <thead style="background-color: darkgray">
        <tr>
            <th class="text-center" scope="col">Name</th>
            <th class="text-center" scope="col">Sex</th>
            <th class="text-center" scope="col">Department</th>
            <th class="text-center" scope="col">Position</th>
            <th class="text-center" scope="col">Projects</th>
            <th class="text-center" scope="col">Salary</th>
            <th class="text-center" scope="col">Date of hire</th>
            <th class="text-center" scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="employee" items="${employees}">
            <tr>
                <th class="align-middle">${employee.name}</th>
                <td class="align-middle text-center">${employee.sex}</td>
                <td class="align-middle text-center">${employee.department.name}</td>
                <td class="align-middle text-center">${employee.position}</td>
                <td class="align-middle text-center">${employee.projects.size()}&nbsp;&nbsp;&nbsp;&nbsp;
                    <div class="btn-group dropright">
                        <button type="button" class="btn btn-outline-info btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Show
                        </button>
                        <div class="dropdown-menu" style="width: 200px; visibility: hidden">
                            <ul class="list-group" style="visibility: visible">
                                <c:forEach var="project" items="${employee.projects}">
                                    <li class="list-group-item py-1" style="background-color: lightgrey">${project.name}</li>
                                 </c:forEach>
                            </ul>
                        </div>
                    </div>
                </td>
                <td class="align-middle text-center">${employee.salary}</td>
                <td class="align-middle text-center">${employee.dateOfHire}</td>
                <td class="align-middle text-center">
                    <a href="update?id=${employee.id}"><button type="button" class="btn btn-outline-warning btn-sm">Edit</button></a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="delete?id=${employee.id}"><button type="button" class="btn btn-outline-danger btn-sm">Delete</button></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="create"><button type="button" class="btn btn-outline-primary">Add new employee</button></a>
</div>
</body>
</html>