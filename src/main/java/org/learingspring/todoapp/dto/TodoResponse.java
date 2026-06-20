package org.learingspring.todoapp.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponse {
    private Integer id;
    private String title;
    private Boolean completed;
}
