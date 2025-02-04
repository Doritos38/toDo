package com.example.todo.repository;

import com.example.todo.dto.ToDoRequestDto;
import com.example.todo.dto.ToDoResponseDto;
import com.example.todo.entity.ToDo;
import com.example.todo.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ToDoRepository {
    Long saveToDo(ToDo toDo);

    List<ToDoResponseDto> viewAll(ToDoRequestDto dto);

    Optional<ToDo> viewId(Long id);

    int update(String contents, String name, LocalDateTime now, Long id);

    int checkPassword(Long id, String password);

    int delete(Long id);

    Long regi(User user);

    List<ToDoResponseDto> paging (ToDoRequestDto dto);

    int checkSize ();

    boolean checkDelete(Long id);
}