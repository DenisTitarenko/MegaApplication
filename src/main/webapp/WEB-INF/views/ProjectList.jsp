<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/lists.css"/>
    <title>Spring MVC</title>
</head>
<body>
<div align="center">
    <h1>Project list</h1>
    <table>
        <tr>
            <th>Project</th>
            <th>Employees count</th>
            <th>Action</th>
        </tr>
        <c:forEach var="project" items="${projects}">
            <tr>
                <td>${project.name}</td>
                <td>${project.employees.size()}
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="employees?name=${project.name}"><button>Show</button></a>
                </td>
                <td>
                    <a href="update?name=${project.name}"><button>Edit</button></a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="delete?name=${project.name}"><button>Delete</button></a>

                </td>
            </tr>
        </c:forEach>

    </table>

    <h3>
        <a href="create"><button>Add new project</button></a>
    </h3>
</div>
</body>
</html>
