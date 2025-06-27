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
@Table("users")
public class User {
    @Id
    private long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column("created_at")
    private Timestamp createdAt;
}
