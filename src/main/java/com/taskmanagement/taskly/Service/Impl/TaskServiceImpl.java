package com.taskmanagement.taskly.Service.Impl;

import com.taskmanagement.taskly.Entity.Task;
import com.taskmanagement.taskly.Repository.TaskRepository;
import com.taskmanagement.taskly.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Mono<Task> create(Task task) {
        task.setStatus("TO DO");
        task.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        task.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return taskRepository.save(task);
    }

    @Override
    public Flux<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Mono<Task> get(long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Mono<Task> update(Task task, long id) {
        return taskRepository.findById(id).flatMap(task1 -> {
            task1.setTitle(task.getTitle());
            task1.setDescription(task.getDescription());
            task1.setAssignee(task.getAssignee());
            task1.setStatus(task.getStatus());
            task1.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            return taskRepository.save(task1);
        });
    }

    @Override
    public Mono<Void> delete(long id) {
        return taskRepository.findById(id).flatMap(task -> taskRepository.delete(task));
    }

    @Override
    public Flux<Task> findByStatus(String status) {
        return taskRepository.findByStatus(status);
    }

    @Override
    public Flux<Task> findByAssignee(long assignee) {
        return taskRepository.findByAssignee(assignee);
    }

    @Override
    public Flux<Task> findByStatusAndAssignee(String status, long assignee) {
        return taskRepository.findByStatusAndAssignee(status, assignee);
    }

    @Override
    public Flux<Task> filterByDates(LocalDateTime fromCreated, LocalDateTime toCreated,
                                    LocalDateTime fromUpdated, LocalDateTime toUpdated) {
        return taskRepository.findAll()
                .filter(task -> {
                    boolean match = true;
                    LocalDateTime createdAt = task.getCreatedAt().toLocalDateTime();
                    LocalDateTime updatedAt = task.getUpdatedAt().toLocalDateTime();
                    if (fromCreated != null)
                        match &= createdAt.isAfter(fromCreated) || createdAt.isEqual(fromCreated);
                    if (toCreated != null)
                        match &= createdAt.isBefore(toCreated) || createdAt.isEqual(toCreated);
                    if (fromUpdated != null)
                        match &= updatedAt.isAfter(fromUpdated) || updatedAt.isEqual(fromUpdated);
                    if (toUpdated != null)
                        match &= updatedAt.isBefore(toUpdated) || updatedAt.isEqual(toUpdated);

                    return match;
                });
    }

//    @Override
//    public Flux<Task> searchTask(String keyword) {
//        return taskRepository.searchByTitleorDescription(keyword);
//    }

}
