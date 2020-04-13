<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/lists.css"/>
    <title>Spring MVC</title>
</head>
<body>
<div align="center">
    <h1>Employee list in ${projectName.toLowerCase()} project </h1>
    <table>
        <tr>
            <th>Name</th>
            <th>Sex</th>
            <th>Department</th>
            <th>Position</th>
            <th>Salary</th>
            <th>Date of hire</th>
        </tr>
        <c:forEach var="employee" items="${employees}">
            <tr>
                <td>${employee.name}</td>
                <td>${employee.sex}</td>
                <td>${employee.department.name}</td>
                <td>${employee.position}</td>
                <td>${employee.salary}</td>
                <td>${employee.dateOfHire}</td>
            </tr>
        </c:forEach>

    </table>
</div>
</body>
</html>
