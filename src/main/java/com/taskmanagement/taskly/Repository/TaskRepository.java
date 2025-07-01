package com.taskmanagement.taskly.Repository;

import com.taskmanagement.taskly.Entity.Task;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TaskRepository extends ReactiveCrudRepository<Task,Long> {

    Flux<Task> findByStatus (String status);
    Flux<Task> findByAssignee (Long assignee);
    Flux<Task> findByStatusAndAssignee(String status , Long assignee);

//    @Query("SELECT * FROM tasks WHERE title Like '%' ||:keyword|| '%' OR '%' ||:description|| '%'")
//    Flux<Task> searchByTitleorDescription (String keyword);

}


