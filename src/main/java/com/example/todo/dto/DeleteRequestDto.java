package com.example.todo.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class DeleteRequestDto {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z가-힣!@#$%^&*(),.?\":{}|<>0-9]{1,20}$")
    private String password;

    @NotNull
    @Min(value = 1)
    @Max(value = 500)
    private Long id;
}
