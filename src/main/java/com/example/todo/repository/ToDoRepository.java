package com.example.todo.repository;

import com.example.todo.dto.ToDoRequestDto;
import com.example.todo.dto.ToDoResponseDto;
import com.example.todo.entity.ToDo;

import java.util.List;
import java.util.Optional;

public interface ToDoRepository {
    Long saveMemo(ToDo toDo);

    List<ToDoResponseDto> findAllView(ToDoRequestDto dto);

    Optional<ToDo> findview(ToDoRequestDto dto);
}
