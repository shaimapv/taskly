package com.taskmanagement.taskly.Controller;

import com.taskmanagement.taskly.Entity.Task;
import com.taskmanagement.taskly.Repository.UserRepository;
import com.taskmanagement.taskly.Service.TaskService;
import com.taskmanagement.taskly.dto.TaskRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/insert")
    public Mono<Task> create(@RequestBody TaskRequestDto taskRequest) {
        return userRepository.findByName(taskRequest.getAssigneeName())
                .switchIfEmpty(Mono.error(new RuntimeException("User not found: " + taskRequest.getAssigneeName())))
                .flatMap(user -> {
                    Task task = new Task();
                    task.setTitle(taskRequest.getTitle());
                    task.setDescription(taskRequest.getDescription());
                    task.setAssignee(user.getId());
                    task.setStatus("TO DO");
                    task.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                    task.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                    return taskService.create(task);
                });
    }

    @GetMapping("/findAll")
    public Flux<Task> getAll(){
        return taskService.getAll();
    }
    @GetMapping("/{id}")
    public Mono<Task> get(@PathVariable long id){
        return taskService.get(id);
    }
    @PutMapping("/update/{id}")
    public Mono<Task> update(@RequestBody Task task, @PathVariable long id){
        return taskService.update(task,id);
    }
    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable long id){
        return taskService.delete(id);
    }
}
