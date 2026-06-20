package org.learingspring.todoapp.dto;


import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CreateTodoRequest {
    private String title;
}
