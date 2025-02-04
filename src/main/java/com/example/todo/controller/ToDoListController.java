package com.example.todo.controller;

import com.example.todo.dto.*;
import com.example.todo.service.ToDoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/toDo")
public class ToDoListController {

    private final ToDoService toDoService;

    public ToDoListController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @RequestMapping("/add")     // 할 일 저장
    public ResponseEntity<ToDoResponseDto> createToDo(@RequestBody @Valid SaveRequestDto dto) {

        toDoService.saveToDo(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/viewAll")     // 전체 보기
    public ResponseEntity<List<ToDoResponseDto>> viewAllToDo(@RequestBody @Valid ViewAllRequestDto dto) {

        return new ResponseEntity<>(toDoService.viewAll(dto), HttpStatus.OK);
    }

    @RequestMapping("/allPage")     // 페이징 전체 보기
    public ResponseEntity<PagingResponseDto<ToDoResponseDto>> PaigingAllToDo(@RequestBody @Valid AllPageRequestDto dto) {

        return new ResponseEntity<>(toDoService.pagingAll(dto), HttpStatus.OK);
    }

    @RequestMapping("/view")        // 하나만 보기
    public ResponseEntity<ToDoResponseDto> viewToDo(@RequestBody @Valid ViewRequestDto dto) {

        return new ResponseEntity<>(toDoService.viewId(dto.getId()), HttpStatus.OK);
    }

    @RequestMapping("/update")      // 할 일 수정
    public ResponseEntity<ToDoResponseDto> updateToDo(@RequestBody @Valid UpdateRequestDto dto) {

        toDoService.update(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/delete")      // 할 일 삭제
    public ResponseEntity<ToDoResponseDto> deleteToDo(@RequestBody @Valid DeleteRequestDto dto) {

        toDoService.delete(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/regi")        //  사용자 등록
    public ResponseEntity<ToDoResponseDto> regiUser(@RequestBody @Valid RegistRequestDto dto) {

        toDoService.regiUser(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}