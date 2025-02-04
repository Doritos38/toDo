package com.example.todo.service;

import com.example.todo.dto.ToDoRequestDto;
import com.example.todo.dto.ToDoResponseDto;

import java.util.List;

public interface ToDoService {
    void saveToDo(ToDoRequestDto dto);

    List<ToDoResponseDto> viewAll(ToDoRequestDto dto);

    ToDoResponseDto viewId(Long id);

    void update(ToDoRequestDto dto);

    void delete(ToDoRequestDto dto);

    void regiUser(ToDoRequestDto dto);
}