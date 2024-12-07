package com.example.demo.repositories;

import com.example.demo.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserRepository extends JpaRepository<MyUser, String> {
}
