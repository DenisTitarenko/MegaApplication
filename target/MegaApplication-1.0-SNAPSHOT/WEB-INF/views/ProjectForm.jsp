<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%><html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/forms.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Spring MVC</title>
</head>
<body>
<div align="center">
    <h1>New/Edit project</h1>
    <form:form action="save" method="post" modelAttribute="project">
        <table>
            <form:hidden path="id"/>
            <tr>
                <td>Name:</td>
                <td><input required type="text" name="name" value="${project.name}"></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="Save"></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>
