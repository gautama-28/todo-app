package org.learingspring.todoapp.controller;

import lombok.AllArgsConstructor;

import org.learingspring.todoapp.dto.CreateTodoRequest;
import org.learingspring.todoapp.dto.TodoResponse;
import org.learingspring.todoapp.dto.UpdateTodoRequest;
import org.learingspring.todoapp.entity.Todo;
import org.learingspring.todoapp.service.TodoService;
import org.learingspring.todoapp.util.TodoMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
@AllArgsConstructor // this. karke wale sare aaenge

public class TodoController {

    private final TodoService todoService;
    private final TodoMapper todoMapper;

    @PostMapping
    public TodoResponse save(@RequestBody CreateTodoRequest request){

        Todo todo = todoMapper.TodoToEntity(request);
        Todo saveTodo = todoService.saveTodo(todo);

        return todoMapper.todoResponse(saveTodo);
    }


    @GetMapping
    public List<TodoResponse> getAllTodos(){
        return  todoService.getAll()
                .stream()
                .map(todoMapper::todoResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TodoResponse GetById(@PathVariable Integer id){
        Todo todo =  todoService.findById(id);

        if(todo==null)
            throw new RuntimeException("Does not exist");

        return todoMapper.todoResponse(todo);
    }

    @PutMapping("/{id}")
    public TodoResponse update(@PathVariable Integer id, @RequestBody UpdateTodoRequest request){
        Todo todo = todoMapper.TodoToEntity(request, id);

        Todo updatedTodo = todoService.updateTodo(todo);

        if(updatedTodo==null) return  null;

        return todoMapper.todoResponse(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        todoService.deleteTodo(id);
    }



}
