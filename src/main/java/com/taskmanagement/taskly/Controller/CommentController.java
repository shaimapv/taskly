package com.taskmanagement.taskly.Controller;

import com.taskmanagement.taskly.Entity.Comment;
import com.taskmanagement.taskly.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/insert")
    public Mono<Comment> create(@RequestBody Comment comment){
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
    public Mono<Comment> update(@RequestBody Comment comment,@PathVariable long id){
        return commentService.update(comment,id);
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable long id){
        return commentService.delete(id);
    }

}
