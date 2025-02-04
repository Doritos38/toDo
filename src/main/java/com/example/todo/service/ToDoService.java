package com.example.todo.service;

import com.example.todo.dto.*;

import java.util.List;

public interface ToDoService {
    void saveToDo(SaveRequestDto dto);      //  할 일 저장

    List<ToDoResponseDto> viewAll(ViewAllRequestDto dto);       // 전체 보기

    ToDoResponseDto viewId(Long id);       // 하나만 보기

    void update(UpdateRequestDto dto);       // 할 일 수정

    void delete(DeleteRequestDto dto);       // 할 일 삭제

    void regiUser(RegistRequestDto dto);       // 유저 등록

    PagingResponseDto<ToDoResponseDto> pagingAll(AllPageRequestDto dto);       // 페이징 전체 보기
}