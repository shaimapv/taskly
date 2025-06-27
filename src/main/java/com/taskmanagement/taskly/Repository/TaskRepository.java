package com.taskmanagement.taskly.Repository;

import com.taskmanagement.taskly.Entity.Task;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends ReactiveCrudRepository<Task,Long> {
}
