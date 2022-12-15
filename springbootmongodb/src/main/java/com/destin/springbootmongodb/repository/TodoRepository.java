package com.destin.springbootmongodb.repository;

import com.destin.springbootmongodb.model.TodoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<TodoDTO,String> {

    TodoDTO removeById(String id);
}
