package com.example.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ToDo {
    private Long id;
    private String name;
    private String contents;
    private String password;
    private LocalDateTime date;
    private LocalDateTime updateDate;

    public ToDo (String name, String contents, String password) {
        this.name = name;
        this.contents = contents;
        this.password = password;
        this.date = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();

    }

}