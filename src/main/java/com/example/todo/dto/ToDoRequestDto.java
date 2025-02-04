package com.example.todo.dto;

import lombok.Getter;

@Getter
public class ToDoRequestDto {
    private String name;
    private String contents;
    private String password;
    private String date;
    private String id;
}
