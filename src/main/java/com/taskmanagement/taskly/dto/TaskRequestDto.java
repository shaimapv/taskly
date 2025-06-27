package com.taskmanagement.taskly.dto;

import lombok.Data;

@Data
public class TaskRequestDto {

    private String title;
    private String description;
    private String assigneeName;
    private String status;
}
