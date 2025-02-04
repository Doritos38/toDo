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
    private Long userId;
    private boolean deleted;

    public ToDo (String name, String contents, String password, Long userId) {
        this.name = name;
        this.contents = contents;
        this.password = password;
        this.date = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
        this.userId = userId;
        this.deleted = false;
    }


    public ToDo(Long id, String name, String contents, String password, LocalDateTime date, LocalDateTime updateDate, Long userId) {
        this.id = id;
        this.name = name;
        this.contents = contents;
        this.password = password;
        this.date = date;
        this.updateDate = updateDate;
        this.userId = userId;
    }
}