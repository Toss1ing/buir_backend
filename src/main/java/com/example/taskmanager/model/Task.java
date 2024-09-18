package com.example.taskmanager.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "task_tbl")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;

    private String description;

    private boolean complete;

    private Instant createDate;

    private Instant endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Task() {
        this.taskName = "";
        this.description = "";
        this.complete = false;
        this.createDate = Instant.now();
        this.endDate = Instant.now();
        this.user = new User();
    }

    public Task(final String taskName, final String description, final Instant endDate) {
        this.taskName = taskName;
        this.description = description;
        this.complete = false;
        this.createDate = Instant.now();
        this.endDate = endDate;
        this.user = null;
    }

}
