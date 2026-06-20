package org.learingspring.todoapp.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateTodoRequest {
    private String title;
    private Boolean completed;
}
