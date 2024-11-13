<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.jalasoft.todolist.model.Todo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    List<Todo> todos = (List<Todo>) request.getAttribute("todos");
    int currentPage = (int) request.getAttribute("currentPage");
    int totalPages = (int) request.getAttribute("totalPages");

    // URLs
    String contextPath = request.getContextPath();
    String addNewTodoUrl = contextPath + "/TodoList?action=new";
    String editTodoUrl = contextPath + "/TodoList?action=edit";
    String deleteTodoUrl = contextPath + "/TodoList?action=delete";

    // Pagination URLs
    String previousPageUrl = contextPath + "/TodoList?page=" + (currentPage - 1);
    String nextPageUrl = contextPath + "/TodoList?page=" + (currentPage + 1);

    // Pagination classes
    String previousClass = currentPage > 0 ? "" : "hidden";
    String nextClass = currentPage < totalPages - 1 ? "" : "hidden";

    // Date formatter
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL dd yyyy");
%>

<%@ include file="/components/header.jsp" %>

<h2>List of Todos</h2>
<a href="<%= addNewTodoUrl %>" class="add-button">New Todo</a>

<div class="todo-table-container">
    <table class="todo-table">
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Status</th>
            <th>Target Date</th>
            <th>Action</th>
        </tr>
        <% for (Todo todo : todos) {
            String editUrl = editTodoUrl + "&id=" + todo.getId();
            String deleteUrl = deleteTodoUrl + "&id=" + todo.getId();
        %>
        <tr>
            <td><%= todo.getTitle() %>
            </td>
            <td><%= todo.getDescription() %>
            </td>
            <td><%= todo.getStatus().getValue() %>
            </td>
            <td><%= todo.getTargetDate().format(formatter) %>
            </td>
            <td>
                <a href="<%= editUrl %>" class="edit-link">Edit</a> /
                <a href="<%= deleteUrl %>" class="delete-link">Delete</a>
            </td>
        </tr>
        <% } %>
    </table>
</div>

<div class="pagination">
    <a href="<%= previousPageUrl %>" class="previous <%= previousClass %>"></a>
    <a href="<%= nextPageUrl %>" class="next <%= nextClass %>"></a>
</div>

<%@ include file="/components/footer.jsp" %>
