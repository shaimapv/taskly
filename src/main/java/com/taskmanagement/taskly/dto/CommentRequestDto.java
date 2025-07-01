package com.taskmanagement.taskly.dto;

import lombok.Data;

@Data
public class CommentRequestDto {

    private long taskId;

    private String content;
}
