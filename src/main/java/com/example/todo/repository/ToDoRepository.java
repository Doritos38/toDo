package com.example.todo.repository;

import com.example.todo.dto.ToDoRequestDto;
import com.example.todo.dto.ToDoResponseDto;
import com.example.todo.entity.ToDo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ToDoRepository {
    Long saveMemo(ToDo toDo);

    List<ToDoResponseDto> viewAll(ToDoRequestDto dto);

    Optional<ToDo> viewId(Long id);

    int update(String contents, String name, LocalDateTime now, Long id);

    int checkPassword(Long id, String password);

    int delete(Long id);
}