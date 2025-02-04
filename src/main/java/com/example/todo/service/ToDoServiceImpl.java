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
    @Transactional      //  할 일 저장
    public void saveToDo(SaveRequestDto dto) {

        ToDo toDo = new ToDo(dto.getName(), dto.getContents(), dto.getPassword(), dto.getUserId());

        Long id = toDoRepository.saveToDo(toDo);    // 저장

        if (id == null) {   // 저장 되었는지 여부 체크
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "save failed");
        }
    }

    @Override       // 전체 보기
    public List<ToDoResponseDto> viewAll(ViewAllRequestDto dto) {

        List<ToDoResponseDto> allView = toDoRepository.viewAll(dto);

        return allView;
    }

    @Override       // 하나만 보기
    public ToDoResponseDto viewId(Long id) {


        Optional<ToDo> optionalToDo = toDoRepository.viewId(id);

        if (optionalToDo.isEmpty()) {   // 해당 id의 일정 있는지 체크
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong id");
        } else if (toDoRepository.checkDelete(id)) {    // 삭제 여부 체크    삭제 검증을 SQL문으로 처리해서 받을 때 한번에 처리하는게 좋을지 아님 이렇게 따로 검증로직을 만들어서 db 두번 사용하는게 더 나은지 질문하기
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deleted id");
        }

        return new ToDoResponseDto(optionalToDo.get());
    }

    @Override       // 할 일 수정
    public void update(UpdateRequestDto dto) {

        if (toDoRepository.checkPassword(dto.getId(), dto.getPassword()) == 0) {    // 패스워드 체크     이것도 위랑 같다
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password");
        } else if (toDoRepository.checkDelete(dto.getId())) {   // 삭제 여부 체크
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deleted id");
        }

        int result = toDoRepository.update(dto.getContents(), dto.getName(), LocalDateTime.now(), dto.getId()); // 수정

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong input");   //  수정 성공 여부 체크
        }
    }

    @Override       // 할 일 삭제
    public void delete(DeleteRequestDto dto) {

        if (toDoRepository.checkPassword(dto.getId(), dto.getPassword()) == 0) {    // 패스워드 체크
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password");
        } else if (toDoRepository.checkDelete(dto.getId())) {   // 삭제 여부 체크
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Deleted id");
        }

        int result = toDoRepository.delete(dto.getId());    // 삭제

        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong input");   // 삭제 여부 체크
        }

    }

    @Override       // 유저 등록
    public void regiUser(RegistRequestDto dto) {

        User user = new User(dto.getName(), dto.getEmail());
        Long id = toDoRepository.regi(user);    // 유저 등록

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "regi failed");   // 등록 여부 체크
        }

    }

    @Override       // 페이징 전체 보기
    public PagingResponseDto<ToDoResponseDto> pagingAll(AllPageRequestDto dto) {
        int offSet = (dto.getPage() - 1) * dto.getSize();   // 현재 페이지 수 계산

        int totalSize = toDoRepository.checkSize(); // 전체 데이터 수

        int totalPage = (int) Math.ceil((double) totalSize / dto.getSize());    // 전체 페이지

        return new PagingResponseDto<>(toDoRepository.paging(dto, offSet), dto.getPage(), dto.getSize(), totalSize, totalPage);
    }
}