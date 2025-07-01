package com.taskmanagement.taskly.Service;

import com.taskmanagement.taskly.Entity.Task;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface TaskService {

    public Mono<Task> create(Task task);

    public Flux<Task> getAll();

    public Mono<Task> get(long id);

    public Mono<Task> update(Task task , long id);

    public Mono<Void> delete(long id);

    Flux<Task> findByStatus(String status);

    Flux<Task> findByAssignee(long assignee);

    Flux<Task> findByStatusAndAssignee(String status, long assignee);

    public Flux<Task> filterByDates(LocalDateTime fromCreated, LocalDateTime toCreated,
                                    LocalDateTime fromUpdated, LocalDateTime toUpdated);

    //public Flux<Task> searchTask(String keyword);

}
