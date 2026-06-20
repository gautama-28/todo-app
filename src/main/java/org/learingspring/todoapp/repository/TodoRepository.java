package org.learingspring.todoapp.repository;

import org.learingspring.todoapp.entity.Todo;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TodoRepository {

    private final List<Todo> todos = new ArrayList<>();

    public Todo save(Todo todo) { // post operation
        todos.add(todo);
        return todo;
    }

    public List<Todo> findAll() { // get operation
        return todos;
    }

    public Optional<Todo> findById(
            Integer id) {
        return todos.stream()
                .filter(todo -> todo.getId().equals(id))
                .findFirst();
    }

    public Todo update(Todo updatedTodo) {
            for (int i = 0 ; i < todos.size() ; i++) {
                if( todos.get(i).getId().equals(updatedTodo.getId())){
                    todos.set(i, updatedTodo);
                    return updatedTodo;
                }
            }
            return null;
    }

    public void deleteById(
            Integer id) {
        todos.removeIf(
                todo -> todo.getId().equals(id)
        );
    }


}
