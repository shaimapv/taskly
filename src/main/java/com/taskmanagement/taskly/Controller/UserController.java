package com.taskmanagement.taskly.Controller;

import com.taskmanagement.taskly.Entity.User;
import com.taskmanagement.taskly.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public Flux<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<User> get(@PathVariable int id){
        return userService.get(id);
    }
}
