package org.learingspring.todoapp.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Todo {
    private Integer id;
    private String title;
    private Boolean completed;
}
