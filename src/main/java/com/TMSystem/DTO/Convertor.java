package com.TMSystem.DTO;

import com.TMSystem.Entity.Task;
import com.TMSystem.Entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Convertor {


    public static ResponseUser convertToResponseUser(User user){

        ResponseUser responseUser=new ResponseUser();
        responseUser.setId(user.getId());
        responseUser.setUsername(user.getUsername());
        responseUser.setPassword(user.getPassword());

        List<Task> tasks= user.getTasks();
        List<String> taskTitles=new ArrayList<>();
        for(Task task: tasks){
            taskTitles.add(task.getTitle());
        }
        responseUser.setTaskTitles(taskTitles);
        return responseUser;
    }
    public static ResponseTask convertToResponseTask(Task task){

        ResponseTask responseTask=new ResponseTask();
        responseTask.setId(task.getId());
        responseTask.setTitle(task.getTitle());
        responseTask.setDescription(task.getDescription());
        responseTask.setDueDate(task.getDueDate());
        User user= task.getAssignedUser();
        if(user!=null) {
            responseTask.setUsername(user.getUsername());
        } else{
            responseTask.setUsername("");
        }
        return responseTask;
    }
    public static User convertTouser(RequestUser requestUser){
        User user=new User();
        user.setUsername(requestUser.getUsername());
        user.setPassword(requestUser.getPassword());
        return user;
    }
    public static Task convertToTask(RequestTask requestTask){
        Task task=new Task();
        task.setTitle(requestTask.getTitle());
        task.setDescription(requestTask.getDescription());
        task.setDueDate(requestTask.getDueDate());
        return task;
    }
}
