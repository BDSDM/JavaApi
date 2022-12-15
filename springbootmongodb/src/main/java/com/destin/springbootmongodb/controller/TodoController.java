package com.destin.springbootmongodb.controller;

import com.destin.springbootmongodb.model.TodoDTO;
import com.destin.springbootmongodb.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class TodoController {

    @Autowired
    private TodoRepository todoRepo;
    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos(){
        List<TodoDTO> todos = todoRepo.findAll();
        if(todos.size()>0){
            return new ResponseEntity<List<TodoDTO>>(todos, HttpStatus.OK);

        }else {
            return new ResponseEntity<>("No todos available",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo){
        try {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
            return new ResponseEntity<TodoDTO>(todo,HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

@GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id")String id){
    Optional<TodoDTO> todoOptional = todoRepo.findById(id);
    if(todoOptional.isPresent()){
        return new ResponseEntity<>(todoOptional.get(),HttpStatus.OK);
    }else {
        return new ResponseEntity<>("Todo not found with id "+id,HttpStatus.NOT_FOUND);
    }
}
    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id")String id,@RequestBody TodoDTO todo){
        Optional<TodoDTO> todoOptional = todoRepo.findById(id);
        if(todoOptional.isPresent()){
            TodoDTO todoToSave = todoOptional.get();
            todoToSave.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : todoToSave.getCompleted());
            todoToSave.setTodo(todo.getTodo() != null ? todo.getTodo() : todoToSave.getTodo());
            todoToSave.setDescription((todo.getDescription()!= null ? todo.getDescription():todoToSave.getDescription()));
            todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todoToSave);
            return new ResponseEntity<>(todoToSave,HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Todo not found with id "+id,HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id")String id){
        try {


             TodoDTO a = todoRepo.removeById(id);
             if(a==null){
                 return new ResponseEntity<>("Pas trouvé",HttpStatus.NOT_FOUND);
             }
            return new ResponseEntity<>("Successfully deleted with id "+id, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
