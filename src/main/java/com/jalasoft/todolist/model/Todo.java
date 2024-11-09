package com.jalasoft.todolist.model;

import com.jalasoft.todolist.type.TodoStatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "status")
    private TodoStatusType status;

    @Column(name = "target_date")
    private LocalDate targetDate;
}
