package com.example.todo.service;

import com.example.todo.dto.*;

import java.util.List;

public interface ToDoService {
    void saveToDo(SaveRequestDto dto);

    List<ToDoResponseDto> viewAll(ViewAllRequestDto dto);

    ToDoResponseDto viewId(Long id);

    void update(UpdateRequestDto dto);

    void delete(DeleteRequestDto dto);

    void regiUser(RegistRequestDto dto);

    PagingResponseDto<ToDoResponseDto> pagingAll(AllPageRequestDto dto);
}