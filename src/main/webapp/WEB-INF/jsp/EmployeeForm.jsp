<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>New/Edit Contact</title>

    <style>
        body {
            background-color: slategrey;
        }
    </style>
</head>
<body>
<div align="center">
    <h1>New/Edit Employee</h1>
    <form:form action="save" method="post" modelAttribute="employee">
        <table>
            <form:hidden path="id"/>
            <tr>
                <td>Name:</td>
                <td><form:input path="name" /></td>
            </tr>
            <tr>
                <td>Sex:</td>
                <td><form:input path="sex" /></td>
            </tr>
            <tr>
                <td>Department:</td>
                <td><form:input path="departmentName" /></td>
            </tr>
            <tr>
                <td>Position:</td>
                <td><form:input path="position" /></td>
            </tr>
            <tr>
                <td>Salary:</td>
                <td><form:input path="salary" /></td>
            </tr>
            <tr>
                <td>Date of hire:</td>
                <td><form:input path="dateOfHire" /></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="Save"></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>