<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.jalasoft.todolist.model.Todo" %>
<%@ page import="java.util.List" %>
<%
    List<Todo> todos = (List<Todo>) request.getAttribute("todos");
%>
<%@ include file="/components/header.jsp" %>
<h2>List of Todos</h2>
<a href="/TodoList?action=new" class="add-button">Add +</a>

<table class="todo-table">
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
        <td><a href="#" class="edit-link">Edit</a> / <a href="#" class="delete-link">Delete</a></td>
    </tr>
    <%
        }
    %>
</table>


<%@ include file="/components/footer.jsp" %>
