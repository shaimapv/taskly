package com.taskmanagement.taskly.Repository;

import com.taskmanagement.taskly.Entity.Comment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends ReactiveCrudRepository<Comment,Long> {
}
