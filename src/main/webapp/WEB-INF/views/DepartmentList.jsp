<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/lists.css"/>
    <title>Spring MVC</title>
</head>
<body>
<div align="center">
    <h1>Department list</h1>
    <table>
        <tr>
            <th>Department</th>
            <th>Employees count</th>
            <th>Action</th>
        </tr>
        <c:forEach var="dep" items="${departments}">
            <tr>
                <td>${dep.name}</td>
                <td>${dep.employees.size()}
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="employees?name=${dep.name}"><button>Show</button></a>
                </td>
                <td>
                    <a href="update?name=${dep.name}"><button>Edit</button></a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="delete?name=${dep.name}"><button>Delete</button></a>

                </td>
            </tr>
        </c:forEach>

    </table>

    <h3>
        <a href="create"><button>Add new department</button></a>
    </h3>
</div>
</body>
</html>
