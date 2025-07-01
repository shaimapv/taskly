package com.taskmanagement.taskly.Controller;

import com.taskmanagement.taskly.Entity.User;
import com.taskmanagement.taskly.Repository.UserRepository;
import com.taskmanagement.taskly.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserService userService;

//    private UserRepository userRepository;

//    @GetMapping("/findAll")
//    public Flux<User> getAll(){
//        return userService.getAll();
//    }
@GetMapping("/users")
public Mono<List<User>> getAllUsers() {
    return userService.getAll().collectList();
}

//    @GetMapping("/users")
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }

    @GetMapping("/{id}")
    public Mono<User> get(@PathVariable int id){
        return userService.get(id);
    }
}
