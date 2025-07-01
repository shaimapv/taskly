package com.taskmanagement.taskly.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String assigneeName;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // getters and setters
}