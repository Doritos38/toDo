package com.example.todo.dto;

import com.example.todo.entity.ToDo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;


@Getter
@AllArgsConstructor
public class ToDoResponseDto {

    private Long id;
    private Long userId;
    private String name;
    private String email;
    private String contents;
    private LocalDate updateDate;

    public ToDoResponseDto(ToDo toDo) {
        this.id = toDo.getId();
        this.userId = toDo.getUserId();
        this.name = toDo.getName();
        this.contents = toDo.getContents();
        this.updateDate = toDo.getUpdateDate().toLocalDate();
    }
}
