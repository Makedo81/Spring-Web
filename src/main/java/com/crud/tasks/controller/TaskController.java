package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/task")
public class TaskController {

    @Autowired
    public DbService dbService;
    @Autowired
    TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET,value = "getTasks")
    public List<TaskDto> getTasks(){
        return taskMapper.mapToListDtoList(dbService.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET,value = "getTask")
    public TaskDto getTask(@RequestParam Long id){
        return taskMapper.mapToTaskDto(dbService.getTask(id));
    }

    @RequestMapping(method = RequestMethod.POST,value = "createTask",consumes = APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto){
         dbService.saveTask(taskMapper.mapToTask(taskDto));
    }

    @RequestMapping(method = RequestMethod.PUT,value = "updateTask")
    public TaskDto updateTask(@RequestBody TaskDto taskDto){
        return taskMapper.mapToTaskDto(dbService.saveTask(taskMapper.mapToTask(taskDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "deleteTask")
    public void deleteTask(@RequestParam Long id){
        dbService.deleteTask(id);
    }
}