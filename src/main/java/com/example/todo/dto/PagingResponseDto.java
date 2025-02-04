package com.example.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagingResponseDto<T> {
    private List<T> content;
    private int presentPage;
    private int size;
    private int totalSize;
    private int totalPage;

}
