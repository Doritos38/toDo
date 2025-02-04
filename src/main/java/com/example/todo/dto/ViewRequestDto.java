package com.example.todo.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
@NotNull
public class ViewRequestDto {
    @Min(value = 1)
    @Max(value = 500)
    private Long id;
}
