<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.jalasoft.todolist.model.Todo" %>
<%@ page import="java.util.List" %>
<%
    List<Todo> todos = (List<Todo>) request.getAttribute("todos");
%>
<html>
<head>
    <title>Todo App</title>
</head>
<body>
    <h2>List of Todos</h2>
    <table>
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Status</th>
            <th>Target Date</th>
            <th>Action</th>
        </tr>
        <%
            for (Todo todo : todos) {
        %>
            <tr>
                <td><%= todo.getTitle() %></td>
                <td><%= todo.getDescription() %></td>
                <td><%= todo.getStatus() %></td>
                <td><%= todo.getTargetDate() %></td>
            </tr>
        <%
            }
        %>
    </table>

</body>
</html>
