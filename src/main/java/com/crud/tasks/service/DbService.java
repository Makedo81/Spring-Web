package com.crud.tasks.service;

//import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DbService {

    @Autowired
    private TaskRepository repository;

    public List<Task>getAllTasks(){
        List<Task> taskList = repository.findAll();
        System.out.println(taskList.size());
        return taskList;
    }

    public Task getTask(final Long id){
        return repository.findOne(id);
    }

    public Task saveTask(Task task){
        return  repository.save(task);
    }

    public void deleteTask(final Long id){ repository.delete(id);}


}
