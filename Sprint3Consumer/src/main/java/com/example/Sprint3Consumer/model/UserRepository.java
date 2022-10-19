package com.example.Sprint3Consumer.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    void deleteById(int id);
    List<User> findById(int id);
}