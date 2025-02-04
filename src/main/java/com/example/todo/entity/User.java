package com.example.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime regiDate;
    private LocalDateTime updateDate;

    public User (String name, String email) {
        this.name = name;
        this.email = email;
        this.regiDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();

    }
}
