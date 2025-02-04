package com.example.todo.service;

import com.example.todo.dto.ToDoRequestDto;
import com.example.todo.dto.ToDoResponseDto;
import com.example.todo.entity.ToDo;
import com.example.todo.entity.User;
import com.example.todo.repository.ToDoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoService{

    private final ToDoRepository toDoRepository;

    public ToDoServiceImpl(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Override
    @Transactional
    public void saveToDo(ToDoRequestDto dto) {

        if(dto.getName() == null || dto.getContents() == null || dto.getPassword() == null || dto.getUserId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NUll");
        }

        ToDo toDo = new ToDo(dto.getName(), dto.getContents(), dto.getPassword(), dto.getUserId());

        Long id = toDoRepository.saveToDo(toDo);

        if(id == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "save failed");
        }
    }

    @Override
    public List<ToDoResponseDto> viewAll(ToDoRequestDto dto) {

        List<ToDoResponseDto> allView = toDoRepository.viewAll(dto);

        return allView;
    }

    @Override
    public ToDoResponseDto viewId(Long id) {


        Optional<ToDo> optionalToDo = toDoRepository.viewId(id);

        if (optionalToDo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong id");
        }

        return new ToDoResponseDto(optionalToDo.get());
    }

    @Override
    public void update(ToDoRequestDto dto) {
        if (dto.getContents() == null || dto.getName() == null || dto.getPassword() == null || dto.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NUll");
        }

        if(toDoRepository.checkPassword(dto.getId(), dto.getPassword())==0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password");
        }

        int result = toDoRepository.update(dto.getContents(), dto.getName(), LocalDateTime.now(), dto.getId());

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong input");
        }
    }

    @Override
    public void delete(ToDoRequestDto dto) {
        if (dto.getPassword() == null || dto.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NUll");
        }

        if(toDoRepository.checkPassword(dto.getId(), dto.getPassword())==0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password");
        }

        int result = toDoRepository.delete(dto.getId());

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong input");
        }

    }

    @Override
    public void regiUser(ToDoRequestDto dto) {
        if(dto.getName() == null || dto.getEmail() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NUll");
        }
        User user = new User(dto.getName(), dto.getEmail());
        Long id = toDoRepository.regi(user);

        if(id == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "regi failed");
        }

    }
}