<%@ page import="com.jalasoft.todolist.model.Todo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Todo todo = (Todo) request.getAttribute("todo");
%>

<%@ include file="/components/header.jsp" %>
<div class="todo-form">
    <h1>New TODO</h1>
    <form action="/TodoList/edit" method="post">
        <input type="hidden" name="id" value="<%= todo.getId() %>">

        <label for="title">Todo Title</label>
        <input type="text" id="title" name="title" placeholder="Title" value="<%= todo.getTitle() %>">

        <label for="description">Todo Description</label>
        <input type="text" id="description" name="description" placeholder="Description" value="<%= todo.getDescription() %>">

        <label for="status">Todo Status</label>
        <select id="status" name="status">
            <option value="In Progress" <%= "In Progress".equals(todo.getStatus()) ? "selected" : "" %>>In Progress</option>
            <option value="Completed" <%= "Completed".equals(todo.getStatus()) ? "selected" : "" %>>Completed</option>
        </select>

        <label for="targetDate">Todo Target Date</label>
        <input type="date" id="targetDate" name="targetDate" value="<%= todo.getTargetDate() %>">
        <button type="submit">Save</button>
    </form>
</div>
<%@ include file="/components/footer.jsp" %>
