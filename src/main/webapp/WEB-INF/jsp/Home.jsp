<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Spring MVC</title>
    <style>
        body {
            background-color: slategrey;
        }
        table {
            background-color: white;
        }
    </style>
</head>
<body>
<div align="center">
    <h1>Employee List</h1>

    <h3>
        <a href="/MegaApp/employee/"><button>Show employees without any sorting</button></a>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="samesalary"><button>Show employees with same salary</button></a>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="grouped"><button>Show employees grouped by position and date</button></a>
    </h3>

    <table border="1" style="width: 75%">

        <tr>
            <th>Name</th>
            <th>Sex</th>
            <th>Department</th>
            <th>Position</th>
            <th>Salary</th>
            <th>Date of hire</th>
            <th>Action</th>
        </tr>

        <c:forEach var="employee" items="${employees}">
            <c:set var="dep" value="${employee.department}" />
            <tr>
                <td>${employee.name}</td>
                <td>${employee.sex}</td>
                <td>${dep.name}</td>
                <td>${employee.position}</td>
                <td>${employee.salary}</td>
                <td>${employee.dateOfHire}</td>
                <td style="text-align: center">
                    <a href="update?id=${employee.id}"><button>Edit</button></a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="delete?name=${employee.name}"><button>Delete</button></a></td>
            </tr>
        </c:forEach>

    </table>

    <h3>
        <a href="create"><button>Add new employee</button></a>
    </h3>
</div>
</body>
</html>