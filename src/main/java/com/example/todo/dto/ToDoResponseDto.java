package com.example.todo.dto;

import com.example.todo.entity.ToDo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;


@Getter
@AllArgsConstructor
public class ToDoResponseDto {

    private Long id;
    private String name;
    private String contents;
    private LocalDate updateDate;

    public ToDoResponseDto(ToDo toDo) {
        this.id = toDo.getId();
        this.name = toDo.getName();
        this.contents = toDo.getContents();
        this.updateDate = toDo.getUpdateDate().toLocalDate();
    }
}
