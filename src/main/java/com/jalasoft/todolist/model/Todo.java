package com.jalasoft.todolist.model;

import com.jalasoft.todolist.type.TodoStatusType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
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

    public Todo(String title, String description, TodoStatusType status, LocalDate targetDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.targetDate = targetDate;
    }

    public Todo() {
    }
}
