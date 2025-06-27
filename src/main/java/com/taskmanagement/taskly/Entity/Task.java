package com.taskmanagement.taskly.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table("tasks")
public class Task {

    @Id
    private long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column("assignee")
    private Long assignee;

    @Column
    private String status;

    @Column("created_at")
    private Timestamp createdAt;

    @Column("updated_at")
    private Timestamp updatedAt;
}
