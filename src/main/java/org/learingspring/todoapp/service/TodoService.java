package org.learingspring.todoapp.service;

import lombok.RequiredArgsConstructor;
import org.learingspring.todoapp.entity.Todo;
import org.learingspring.todoapp.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final AtomicInteger idGenerator;

    public List<Todo> getAll(){
        return todoRepository.findAll();
    }

    public Todo findById(Integer id){
        return todoRepository.findById(id).orElse(null);
    }

    public  Todo saveTodo(Todo todo){
        todo.setId(idGenerator.getAndIncrement());
        todo.setCompleted(false);

        if(todo.getTitle() == null || todo.getTitle().isEmpty()){
            throw new RuntimeException("Title cannot be empty");
        }
        return todoRepository.save(todo);
    }

    public  Todo updateTodo(Todo updatedTodo) {
        updatedTodo.setCompleted(false);
        return todoRepository.update(updatedTodo);
    }

    public void deleteTodo(Integer id){
        todoRepository.deleteById(id);
    }


}

