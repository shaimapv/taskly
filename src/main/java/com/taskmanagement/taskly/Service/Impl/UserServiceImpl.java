package com.taskmanagement.taskly.Service.Impl;

import com.taskmanagement.taskly.Entity.User;
import com.taskmanagement.taskly.Repository.UserRepository;
import com.taskmanagement.taskly.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public  class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;



    @Override
    public Mono<User> get(long id) {
        return userRepository.findById(id);

    }

    @Override
    public Flux<User> getAll() {
        return userRepository.findAll();
    }




}
