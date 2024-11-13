<%@ page import="com.jalasoft.todolist.model.Todo" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    Todo todo = (Todo) request.getAttribute("todo");
    boolean isEdit = (todo != null);
    Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");

    // Action URL
    String formActionUrl = isEdit ? "/TodoList/edit" : "/TodoList/new";

    // Form values
    String titleValue = request.getAttribute("title") != null ? (String) request.getAttribute("title") : (isEdit ? todo.getTitle() : "");
    String descriptionValue = request.getAttribute("description") != null ? (String) request.getAttribute("description") : (isEdit ? todo.getDescription() : "");
    String targetDateValue = request.getAttribute("targetDate") != null ? (String) request.getAttribute("targetDate") : (isEdit ? todo.getTargetDate().toString() : "");

    // Errors
    String titleError = errors != null ? errors.get("title") : null;
    String descriptionError = errors != null ? errors.get("description") : null;
    String targetDateError = errors != null ? errors.get("targetDate") : null;

    // Status dropdown values
    String status = isEdit ? todo.getStatus().getValue() : "PENDING";
    String pendingSelected = status.equals("Pending") ? "selected" : "";
    String inProgressSelected = status.equals("In Progress") ? "selected" : "";
    String completeSelected = status.equals("Completed") ? "selected" : "";

    // Status disabled
    String statusDisabled = isEdit ? "" : "disabled";
%>

<%@ include file="/components/header.jsp" %>

<div class="todo-form">
    <h1><%= isEdit ? "Edit TODO" : "New TODO" %>
    </h1>
    <form action="<%= formActionUrl %>" method="post">
        <% if (isEdit) { %>
        <input type="hidden" name="id" value="<%= todo.getId() %>">
        <% } %>

        <label for="title">Todo Title <span class="required">*</span></label>
        <input type="text" id="title" name="title" placeholder="Title" value="<%= titleValue %>">
        <% if (titleError != null) { %>
        <p class="error"><%= titleError %>
        </p>
        <% } %>

        <label for="description">Todo Description <span class="required">*</span></label>
        <textarea id="description" name="description" placeholder="Description"
                  rows="3"><%= descriptionValue %></textarea>
        <% if (descriptionError != null) { %>
        <p class="error"><%= descriptionError %>
        </p>
        <% } %>

        <% if (!isEdit) { %>
        <input type="hidden" name="status" value="PENDING">
        <% } %>

        <label for="status">Todo Status</label>
        <select id="status" name="status" <%= statusDisabled %>>
            <option value="PENDING" <%= pendingSelected %>>Pending</option>
            <option value="IN_PROGRESS" <%= inProgressSelected %>>In Progress</option>
            <option value="COMPLETED" <%= completeSelected %>>Completed</option>
        </select>

        <label for="targetDate">Todo Target Date <span class="required">*</span></label>
        <input type="date" id="targetDate" name="targetDate" placeholder="Target Date" value="<%= targetDateValue %>">
        <% if (targetDateError != null) { %>
        <p class="error"><%= targetDateError %>
        </p>
        <% } %>

        <button type="submit">Save</button>
    </form>
</div>

<%@ include file="/components/footer.jsp" %>