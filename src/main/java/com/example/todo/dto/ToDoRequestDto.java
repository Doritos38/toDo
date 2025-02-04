package com.example.todo.dto;

import lombok.Getter;

@Getter
public class ToDoRequestDto {
    private String name;
    private String contents;
    private String password;
    private String date;
    private Long id;
    private Long userId;
    private String email;
    private String regiDate;
    private String userUpdateDate;
    private int size;
    private int page;
}
