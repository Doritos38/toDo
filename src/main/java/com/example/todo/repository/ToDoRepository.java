package com.example.todo.repository;

import com.example.todo.dto.AllPageRequestDto;
import com.example.todo.dto.ToDoResponseDto;
import com.example.todo.dto.ViewAllRequestDto;
import com.example.todo.entity.ToDo;
import com.example.todo.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ToDoRepository {
    Long saveToDo(ToDo toDo);       // 할 일 저장

    List<ToDoResponseDto> viewAll(ViewAllRequestDto dto);       // 전체 보기

    Optional<ToDo> viewId(Long id);       // 하나만 보기

    int update(String contents, String name, LocalDateTime now, Long id);       // 할 일 수정

    int checkPassword(Long id, String password);       // 비밀번호 확인

    int delete(Long id);       // 할 일 삭제

    Long regi(User user);       // 유저 등록

    List<ToDoResponseDto> paging(AllPageRequestDto dto, int offSet);       // 페이징 전체 보기

    int checkSize();        //  전체 할 일 개수 체크

    boolean checkDelete(Long id);       // 삭제 여부 체크
}