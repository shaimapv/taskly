package com.taskmanagement.taskly.Service;

import com.taskmanagement.taskly.Entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {



    public Mono<User> get(long id);

    public Flux<User> getAll();





}
