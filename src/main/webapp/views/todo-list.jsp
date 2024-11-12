<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.jalasoft.todolist.model.Todo" %>
<%@ page import="java.util.List" %>
<%
    List<Todo> todos = (List<Todo>) request.getAttribute("todos");
    int currentPage = (int) request.getAttribute("currentPage");
    int totalPages = (int) request.getAttribute("totalPages");
%>

<%@ include file="/components/header.jsp" %>
<h2>List of Todos</h2>
<a href="<%= request.getContextPath() %>/TodoList?action=new" class="add-button">New Todo</a>

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
        <td><a href="<%= request.getContextPath() %>/TodoList?action=edit&id=<%= todo.getId() %>" class="edit-link">Edit</a>
            /
            <a href="<%= request.getContextPath() %>/TodoList?action=delete&id=<%= todo.getId() %>" class="delete-link">Delete</a>
        </td>
    </tr>
    <%
        }
    %>
</table>

<div class="pagination">
    <a href="<%= request.getContextPath() %>/TodoList?page=<%= currentPage - 1 %>"
       class="previous <%= currentPage > 0 ? "" : "hidden" %>"></a>
    <a href="<%= request.getContextPath() %>/TodoList?page=<%= currentPage + 1 %>"
       class="next <%= currentPage < totalPages - 1 ? "" : "hidden" %>"></a>
</div>


<%@ include file="/components/footer.jsp" %>
