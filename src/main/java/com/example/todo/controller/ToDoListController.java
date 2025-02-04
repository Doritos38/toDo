package com.example.todo.controller;

import com.example.todo.dto.ToDoRequestDto;
import com.example.todo.dto.ToDoResponseDto;
import com.example.todo.service.ToDoService;
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
    public ResponseEntity<ToDoResponseDto> createToDo(@RequestBody ToDoRequestDto dto) {

        toDoService.saveToDo(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/viewAll")
    public ResponseEntity<List<ToDoResponseDto>> viewAllToDo(@RequestBody ToDoRequestDto dto) {

        return new ResponseEntity<>(toDoService.viewAll(dto), HttpStatus.OK);
    }

    @RequestMapping("/view")
    public ResponseEntity<ToDoResponseDto> viewToDo(@RequestBody ToDoRequestDto dto) {

        return new ResponseEntity<>(toDoService.viewId(dto.getId()), HttpStatus.OK);
    }

    @RequestMapping("/update")
    public ResponseEntity<ToDoResponseDto> updateToDo(@RequestBody ToDoRequestDto dto) {

        toDoService.update(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/delete")
    public ResponseEntity<ToDoResponseDto> deleteToDo(@RequestBody ToDoRequestDto dto) {

        toDoService.delete(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}