package com.example.todo.service;

import com.example.todo.dto.ToDoRequestDto;
import com.example.todo.dto.ToDoResponseDto;
import com.example.todo.entity.ToDo;
import com.example.todo.repository.ToDoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

        ToDo toDo = new ToDo(dto.getName(), dto.getContents(), dto.getPassword());

        Long id = toDoRepository.saveMemo(toDo);

        if(id == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "save failed");
        }
    }

    @Override
    public List<ToDoResponseDto> viewAll(ToDoRequestDto dto) {

        List<ToDoResponseDto> allView = toDoRepository.findAllView(dto);




        return allView;
    }

    @Override
    public ToDoResponseDto view(ToDoRequestDto dto) {

        Optional<ToDo> optionalToDo = toDoRepository.findview(dto);

        if (optionalToDo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + dto.getId());
        }

        return new ToDoResponseDto(optionalToDo.get());
    }
}
