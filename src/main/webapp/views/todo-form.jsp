<%@ page import="com.jalasoft.todolist.model.Todo" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    Todo todo = (Todo) request.getAttribute("todo");
    boolean isEdit = (todo != null);
%>

<%@ include file="/components/header.jsp" %>
<div class="todo-form">
    <h1><%= isEdit ? "Edit TODO" : "New TODO" %></h1>
    <form action="/TodoList/<%= isEdit ? "edit" : "new" %>" method="post">
        <% if (isEdit) { %>
        <input type="hidden" name="id" value="<%= todo.getId() %>">
        <% } %>

        <label for="title">Todo Title</label>
        <input type="text" id="title" name="title" placeholder="Title" value="<%= isEdit ? todo.getTitle() : "" %>" required>

        <label for="description">Todo Description</label>
        <input type="text" id="description" name="description" placeholder="Description" value="<%= isEdit ? todo.getDescription() : "" %>">

        <label for="status">Todo Status</label>
        <select id="status" name="status">
            <option value="In Progress" <%= isEdit && "In Progress".equals(todo.getStatus()) ? "selected" : "" %>>In Progress</option>
            <option value="Completed" <%= isEdit && "Completed".equals(todo.getStatus()) ? "selected" : "" %>>Completed</option>
        </select>

        <label for="targetDate">Todo Target Date</label>
        <input type="date" id="targetDate" name="targetDate" value="<%= isEdit ? todo.getTargetDate() : "" %>">
        <button type="submit">Save</button>
    </form>
</div>
<%@ include file="/components/footer.jsp" %>