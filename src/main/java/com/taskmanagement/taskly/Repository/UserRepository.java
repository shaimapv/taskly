package com.taskmanagement.taskly.Repository;

import com.taskmanagement.taskly.Entity.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User,Long> {
    Mono<User> findByName(String name);

    @Query("SELECT * FROM task_schema.users WHERE LOWER(name) = LOWER(:name)")
    Mono<User> findByNameIgnoreCase(String name);

}
