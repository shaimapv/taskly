package com.taskmanagement.taskly.Service;

import com.taskmanagement.taskly.Entity.Comment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentService {

    public Mono<Comment> create(Comment comment);

    public Flux<Comment> getAll();

    public Mono<Comment> get(long id);

    public Mono<Comment> update(Comment comment, long id);

    public Mono<Void> delete(long id);
}
