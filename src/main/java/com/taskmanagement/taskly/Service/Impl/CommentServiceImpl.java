package com.taskmanagement.taskly.Service.Impl;

import com.taskmanagement.taskly.Entity.Comment;
import com.taskmanagement.taskly.Repository.CommentRepository;
import com.taskmanagement.taskly.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Mono<Comment> create(Comment comment) {
        comment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        comment.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return commentRepository.save(comment);

    }

    @Override
    public Flux<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Mono<Comment> get(long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Mono<Comment> update(Comment comment, long id) {
        return commentRepository.findById(id).flatMap(comment1 -> {
            comment1.setTaskId(comment.getTaskId());
            comment1.setContent(comment.getContent());
            comment1.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            return commentRepository.save(comment1);
        });
    }

    @Override
    public Mono<Void> delete(long id) {
        return commentRepository.findById(id).flatMap(comment -> commentRepository.delete(comment));
    }
}
