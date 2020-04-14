<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/forms.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Spring MVC</title>
</head>
<body>
<div align="center">
    <h1>New/Edit employee</h1>
    <form:form action="save" method="post" modelAttribute="employee">
        <table>
            <form:hidden path="id"/>
            <tr>
                <td>Name:</td>
                <td><input required type="text" name="name" value="${employee.name}"></td>
            </tr>
            <tr>
                <td>Sex:</td>
                <td>
                    <input type="radio" name="sex" value="M" required>Male &nbsp;&nbsp;
                    <input type="radio" name="sex" value="F">Female &nbsp;&nbsp;
                    <input type="radio" name="sex" value="OTHER">Other
                </td>
            </tr>
            <tr>
                <td>Department:</td>
                <td><form:select path="departmentName" items="${departments}"/></td>
            </tr>
            <tr>
                <td>Position:</td>
                <td><input required type="text" name="position" value="${employee.position}"></td>
            </tr>
            <tr>
                <td>Projects</td>
                <td><form:select multiple="true" path="projects" items="${projects}"/></td>
            </tr>
            <tr>
                <td>Salary:</td>
                <td><input required type="text" name="salary" value="${employee.salary}"></td>
            </tr>
            <tr>
                <td>Date of hire:</td>
                <td><input required type="text" name="dateOfHire"></td>
            </tr>
            <tr>
                <td colspan="2" align="center"></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="Save"></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>