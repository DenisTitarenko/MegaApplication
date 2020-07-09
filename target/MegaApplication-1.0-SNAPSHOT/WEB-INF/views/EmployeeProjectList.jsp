<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/lists.css"/>
    <title>Spring MVC</title>
</head>
<body>
<div align="center">
    <h1>${employeeName}'s projects</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
        </tr>
        <c:forEach var="project" items="${projects}">
            <tr>
                <td>${project.id}</td>
                <td>${project.name}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
