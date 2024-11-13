package com.jalasoft.todolist.model;

import com.jalasoft.todolist.type.TodoStatusType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Entity class that represents a Todo object.
 * Contains fields for title, description, status, and target date.
 *
 * @author Chris Alan Apaza Aguilar
 */

@Data
@Entity
@Table(name = "todo", schema = "public")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TodoStatusType status;

    @Column(name = "target_date")
    private LocalDate targetDate;

    /**
     * Constructor for the Todo class.
     *
     * @param title       the title of the Todo
     * @param description the description of the Todo
     * @param status      the status of the Todo
     * @param targetDate  the target date of the Todo
     */
    public Todo(String title, String description, TodoStatusType status, LocalDate targetDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.targetDate = targetDate;
    }

    public Todo() {
    }
}
