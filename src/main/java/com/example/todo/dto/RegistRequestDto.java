package com.example.todo.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class RegistRequestDto {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{1,20}$")
    private String name;

    @Email
    @Size(max = 50)
    @NotBlank
    private String email;
}
