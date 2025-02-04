package com.example.todo.service;

import com.example.todo.dto.*;
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
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository toDoRepository;

    public ToDoServiceImpl(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Override
    @Transactional
    public void saveToDo(SaveRequestDto dto) {

        ToDo toDo = new ToDo(dto.getName(), dto.getContents(), dto.getPassword(), dto.getUserId());

        Long id = toDoRepository.saveToDo(toDo);

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "save failed");
        }
    }

    @Override
    public List<ToDoResponseDto> viewAll(ViewAllRequestDto dto) {

        List<ToDoResponseDto> allView = toDoRepository.viewAll(dto);

        return allView;
    }

    @Override
    public ToDoResponseDto viewId(Long id) {


        Optional<ToDo> optionalToDo = toDoRepository.viewId(id);

        if (optionalToDo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong id");
        } else if (toDoRepository.checkDelete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deleted id");
        }

        return new ToDoResponseDto(optionalToDo.get());
    }

    @Override
    public void update(UpdateRequestDto dto) {

        if (toDoRepository.checkPassword(dto.getId(), dto.getPassword()) == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password");
        } else if (toDoRepository.checkDelete(dto.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deleted id");
        }

        int result = toDoRepository.update(dto.getContents(), dto.getName(), LocalDateTime.now(), dto.getId());

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong input");
        }
    }

    @Override
    public void delete(DeleteRequestDto dto) {

        if (toDoRepository.checkPassword(dto.getId(), dto.getPassword()) == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password");
        } else if (toDoRepository.checkDelete(dto.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deleted id");
        }

        int result = toDoRepository.delete(dto.getId());

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong input");
        }

    }

    @Override
    public void regiUser(RegistRequestDto dto) {

        User user = new User(dto.getName(), dto.getEmail());
        Long id = toDoRepository.regi(user);

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "regi failed");
        }

    }

    @Override
    public PagingResponseDto<ToDoResponseDto> pagingAll(AllPageRequestDto dto) {
        int offSet = (dto.getPage() - 1) * dto.getSize();

        int totalSize = toDoRepository.checkSize();

        int totalPage = (int) Math.ceil((double) totalSize / dto.getSize());

        return new PagingResponseDto<>(toDoRepository.paging(dto, offSet), dto.getPage(), dto.getSize(), totalSize, totalPage);
    }
}