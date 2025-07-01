package com.taskmanagement.taskly.Controller;

import com.taskmanagement.taskly.Entity.Comment;
import com.taskmanagement.taskly.Service.CommentService;
import com.taskmanagement.taskly.dto.CommentRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/insert")
    public Mono<Comment> create(@RequestBody CommentRequestDto dto){
        Comment comment = new Comment();
        comment.setTaskId(dto.getTaskId());
        comment.setContent(dto.getContent());
        comment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        comment.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return commentService.create(comment);
    }

    @GetMapping("/findAll")
    public Flux<Comment> getAll(){
        return commentService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<Comment> get(@PathVariable long id){
        return commentService.get(id);
    }

    @PutMapping("/{id}")
    public Mono<Comment> update(@RequestBody CommentRequestDto dto,@PathVariable long id){
        Comment updated = new Comment();
        updated.setTaskId(dto.getTaskId());
        updated.setContent(dto.getContent());
        updated.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return commentService.update(updated,id);
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable long id){
        return commentService.delete(id);
    }

}
