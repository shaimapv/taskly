package com.taskmanagement.taskly.Service;

import com.taskmanagement.taskly.Entity.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {

    public Mono<Task> create(Task task);

    public Flux<Task> getAll();

    public Mono<Task> get(long id);

    public Mono<Task> update(Task task , long id);

    public Mono<Void> delete(long id);
}
