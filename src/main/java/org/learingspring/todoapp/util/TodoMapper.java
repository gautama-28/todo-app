package org.learingspring.todoapp.util;

import org.learingspring.todoapp.dto.CreateTodoRequest;
import org.learingspring.todoapp.dto.TodoResponse;
import org.learingspring.todoapp.dto.UpdateTodoRequest;
import org.learingspring.todoapp.entity.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {
    public Todo TodoToEntity(CreateTodoRequest request){
        Todo todo = new Todo();

        todo.setTitle(request.getTitle());
        return todo;
    }
    public Todo TodoToEntity(UpdateTodoRequest request, Integer id){
        Todo todo = new Todo();

        todo.setId(id);
        todo.setTitle(request.getTitle());
        todo.setCompleted(request.getCompleted());

        return  todo;
    }
    public TodoResponse todoResponse(Todo todo){
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getCompleted()
        );
    }
}
