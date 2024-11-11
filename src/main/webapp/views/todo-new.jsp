<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/components/header.jsp" %>
<div class="todo-form">
    <h1>New TODO</h1>
    <form action="/TodoList/new" method="post">
        <label for="title">Todo Title</label>
        <input type="text" id="title" name="title" placeholder="Title" required>

        <label for="description">Todo Description</label>
        <input type="text" id="description" name="description" placeholder="Description">

        <label for="status">Todo Status</label>
        <select id="status" name="status">
            <option value="In Progress">In Progress</option>
            <option value="Completed">Completed</option>
        </select>

        <label for="targetDate">Todo Target Date</label>
        <input type="date" id="targetDate" name="targetDate">
        <button type="submit">Save</button>
    </form>
</div>
<%@ include file="/components/footer.jsp" %>
