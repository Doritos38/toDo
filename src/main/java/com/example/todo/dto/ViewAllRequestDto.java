package com.example.todo.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ViewAllRequestDto {

    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{1,20}$")
    private String name;

    @Pattern(regexp = "^(?!.*\\s)\\d{4}-\\d{2}-\\d{2}$")
    private String date;

    @Min(value = 1)
    @Max(value = 500)
    private Long userId;
}
