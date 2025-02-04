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
@RequestMapping("/asdasd")
public class ToDoListController {

    private final ToDoService toDoService;

    public ToDoListController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @RequestMapping("/add")
    public ResponseEntity<ToDoResponseDto> createToDo(@RequestBody @Valid SaveRequestDto dto) {

        toDoService.saveToDo(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/viewAll")
    public ResponseEntity<List<ToDoResponseDto>> viewAllToDo(@RequestBody @Valid ViewAllRequestDto dto) {

        return new ResponseEntity<>(toDoService.viewAll(dto), HttpStatus.OK);
    }

    @RequestMapping("/allPage")
    public ResponseEntity<PagingResponseDto<ToDoResponseDto>> PaigingAllToDo(@RequestBody @Valid AllPageRequestDto dto) {

        return new ResponseEntity<>(toDoService.pagingAll(dto), HttpStatus.OK);
    }

    @RequestMapping("/view")
    public ResponseEntity<ToDoResponseDto> viewToDo(@RequestBody @Valid ViewRequestDto dto) {

        return new ResponseEntity<>(toDoService.viewId(dto.getId()), HttpStatus.OK);
    }

    @RequestMapping("/update")
    public ResponseEntity<ToDoResponseDto> updateToDo(@RequestBody @Valid UpdateRequestDto dto) {

        toDoService.update(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/delete")
    public ResponseEntity<ToDoResponseDto> deleteToDo(@RequestBody @Valid DeleteRequestDto dto) {

        toDoService.delete(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/regi")
    public ResponseEntity<ToDoResponseDto> regiUser(@RequestBody @Valid RegistRequestDto dto) {

        toDoService.regiUser(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}