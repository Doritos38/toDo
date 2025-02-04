package com.example.todo.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class UpdateRequestDto {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z가-힣0-9\\s!@#$%^&*()_-]{1,200}$")
    private String contents;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{1,20}$")
    private String name;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z가-힣!@#$%^&*(),.?\":{}|<>0-9]{1,20}$")
    private String password;

    @NotNull
    @Min(value = 1)
    @Max(value = 500)
    private Long id;
}
