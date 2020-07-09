<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/lists.css"/>
    <title>Spring MVC</title>
</head>
<body>
<div align="center">
    <h1>Employee list</h1>

    <h3>
        <a href="/employee/"><button>Show employees without any sorting</button></a>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="samesalary"><button>Show employees with same salary</button></a>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="grouped"><button>Show employees grouped by position and date</button></a>
    </h3>

    <table>
        <tr>
            <th>Name</th>
            <th>Sex</th>
            <th>Department</th>
            <th>Position</th>
            <th>Projects</th>
            <th>Salary</th>
            <th>Date of hire</th>
            <th>Action</th>
        </tr>
        <c:forEach var="employee" items="${employees}">
            <tr>
                <td>${employee.name}</td>
                <td>${employee.sex}</td>
                <td>${employee.department.name}</td>
                <td>${employee.position}</td>
                <td>${employee.projects.size()}
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="projects?name=${employee.name}"><button>Show</button></a>
                </td>
                <td>${employee.salary}</td>
                <td>${employee.dateOfHire}</td>
                <td>
                    <a href="update?id=${employee.id}"><button>Edit</button></a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="delete?id=${employee.id}"><button>Delete</button></a></td>
            </tr>
        </c:forEach>

    </table>

    <h3>
        <a href="create"><button>Add new employee</button></a>
    </h3>
</div>
</body>
</html>