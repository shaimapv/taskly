package com.taskmanagement.taskly.Controller;

import com.taskmanagement.taskly.Entity.Task;
import com.taskmanagement.taskly.Entity.User;
import com.taskmanagement.taskly.Repository.UserRepository;
import com.taskmanagement.taskly.Service.TaskService;
import com.taskmanagement.taskly.dto.TaskDto;
import com.taskmanagement.taskly.dto.TaskRequestDto;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.Query;
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

    @GetMapping
    public Flux<TaskDto> getTasks() {
        return taskService.getAll()
                .flatMap(this::mapTaskToDto);
    }



    @GetMapping("/findAll")
    public Flux<TaskDto> getAll(){
        return taskService.getAll().flatMap(this::mapTaskToDto);
    }
    @GetMapping("/{id}")
    public Mono<TaskDto> get(@PathVariable long id){
        return taskService.get(id).flatMap(this::mapTaskToDto);
    }
    @PutMapping("/update/{id}")
    public Mono<Task> update(@RequestBody TaskRequestDto taskRequest, @PathVariable long id) {
        System.out.println("updating task for assignee :"+taskRequest.getStatus());
        return userRepository.findByName(taskRequest.getAssigneeName())
                .switchIfEmpty(Mono.error(new RuntimeException("User not found: " + taskRequest.getAssigneeName())))
                .flatMap(user -> {
                    Task updatedTask = new Task();
                    updatedTask.setTitle(taskRequest.getTitle());
                    updatedTask.setDescription(taskRequest.getDescription());
                    updatedTask.setAssignee(user.getId());
                    updatedTask.setStatus(taskRequest.getStatus());
                    updatedTask.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                    return taskService.update(updatedTask, id)
                            .doOnError(e-> System.err.println("task update faoiled "+e.getMessage()));
                });
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable long id) {
        return taskService.delete(id);
    }

    @GetMapping("/filter")
    public Flux<TaskDto> filterTasks(
            @RequestParam(required = false) String assigneeName,
            @RequestParam(required = false) String status) {

        if (assigneeName != null && status != null) {
            return userRepository.findByNameIgnoreCase(assigneeName)
                    .flatMapMany(user -> taskService.findByStatusAndAssignee(status, user.getId()))
                    .flatMap(this::mapTaskToDto);
        } else if (assigneeName != null) {
            return userRepository.findByNameIgnoreCase(assigneeName)
                    .flatMapMany(user -> taskService.findByAssignee(user.getId()))
                    .flatMap(this::mapTaskToDto);
        } else if (status != null) {
            return taskService.findByStatus(status)
                    .flatMap(this::mapTaskToDto);
        } else {
            return taskService.getAll()
                    .flatMap(this::mapTaskToDto);
        }
    }

    @GetMapping("/filter/date")
    public Flux<TaskDto> filterTasksByDate(
            @RequestParam(required = false) String fromCreated,
            @RequestParam(required = false) String toCreated,
            @RequestParam(required = false) String fromUpdated,
            @RequestParam(required = false) String toUpdated
    ) {
        LocalDateTime fromCreatedAt = fromCreated != null ? LocalDateTime.parse(fromCreated) : null;
        LocalDateTime toCreatedAt = toCreated != null ? LocalDateTime.parse(toCreated) : null;
        LocalDateTime fromUpdatedAt = fromUpdated != null ? LocalDateTime.parse(fromUpdated) : null;
        LocalDateTime toUpdatedAt = toUpdated != null ? LocalDateTime.parse(toUpdated) : null;

        return taskService.filterByDates(fromCreatedAt, toCreatedAt, fromUpdatedAt, toUpdatedAt)
                .flatMap(this::mapTaskToDto);
    }

//    @GetMapping("/search")
//    public Flux<Task> searchTask(@PathVariable String query){
//        return taskService.searchTask(query);
//    }

    private Mono<TaskDto> mapTaskToDto(Task task) {
        return userRepository.findById(task.getAssignee())
                .map(user -> convertToDto(task, user))
                .defaultIfEmpty(convertToDto(task, null));
    }

    private TaskDto convertToDto(Task task, User user) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setAssigneeName(user != null ? user.getName() : "Unknown");
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        return dto;
    }


}
